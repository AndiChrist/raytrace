package de.avwc.util;

import de.avwc.gfx.Renderable;
import de.avwc.main.Scene;
import org.hipparchus.geometry.euclidean.threed.Line;
import org.hipparchus.geometry.euclidean.threed.Vector3D;

import java.awt.*;

public class ColorUtil {
    private static final int MAX_RECURSION_DEPTH = 3;

    public static Color castPrimary(Scene scene, Line line, int depth) {
        Color color = Color.BLACK;

        if (depth > MAX_RECURSION_DEPTH) {
            return color;
        }

        // which object hits by the ray?
        Renderable intersectObject = null;
        double t = Double.MAX_VALUE;

        // find intersection with objects
        for (Renderable object : scene.getObjects()) {
            double intersection = object.intersect(line);
            // new t is between the eye AND object?
            if (intersection > 0 && intersection < t) {
                intersectObject = object;
                t = intersection;
            }
        }

        // get color from intersected object
        if (intersectObject != null) {
            intersectObject.setScene(scene);
            color = intersectObject.getColor(getPosition(line, t), depth);
        }

        return color;
    }

    private static Vector3D getPosition(Line line, double t) {
        return line.getOrigin().add(line.getDirection().scalarMultiply(t));
    }
}

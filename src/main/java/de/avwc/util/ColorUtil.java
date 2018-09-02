package de.avwc.util;

import de.avwc.gfx.Renderable;
import de.avwc.main.RayScene;
import org.apache.commons.math3.geometry.Vector;
import org.apache.commons.math3.geometry.euclidean.threed.Euclidean3D;
import org.apache.commons.math3.geometry.euclidean.threed.Line;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import javafx.scene.paint.Color;

public class ColorUtil {
    private static final int MAX_RECURSION_DEPTH = 3;

    public static Color castPrimary(Line line, int depth) {
        Color color = Color.BLACK;

        if (depth > MAX_RECURSION_DEPTH) {
            return color;
        }

        // which object hits by the ray?
        Renderable intersectObject = null;
        double t = Double.MAX_VALUE;

        // find intersection with objects
        for (Renderable object : RayScene.getInstance().getObjects()) {
            double intersection = object.intersect(line);
            // new t is between the eye AND object?
            if (intersection > 0 && intersection < t) {
                intersectObject = object;
                t = intersection;
            }
        }

        // get color from intersected object
        if (intersectObject != null) {
            color = intersectObject.getColor(getPosition(line, t), depth);
        }

        return color;
    }

    private static Vector3D getPosition(Line line, double t) {
        return line.getOrigin().add(line.getDirection().scalarMultiply(t));
    }

    public static Vector<Euclidean3D> scalarMultiply(Color c, double reflectionIndex) {
        return new Vector3D(c.getRed(), c.getGreen(), c.getBlue()).scalarMultiply(reflectionIndex);
    }

}

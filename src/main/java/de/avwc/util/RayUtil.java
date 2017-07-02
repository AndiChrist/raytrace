package de.avwc.util;

import de.avwc.gfx.Renderable;
import de.avwc.main.Scene;
import org.apache.commons.math3.geometry.euclidean.threed.Line;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.awt.*;

/**
 * Created by andichrist on 23.04.17.
 */
public class RayUtil {
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
        for (Renderable object : Scene.getInstance().getObjects()) {
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

    public static boolean castShadow(Line line) {
        double t = Double.MAX_VALUE;

        // find intersection with objects
        for (Renderable object : Scene.getInstance().getObjects()) {
            double intersection = object.intersect(line);
            // new t is between the eye AND object?
            if (intersection > 0 && intersection < t) {
                return true;
            }
        }

        return false;
    }

}

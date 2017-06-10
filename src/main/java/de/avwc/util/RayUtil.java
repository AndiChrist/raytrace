package de.avwc.util;

import de.avwc.gfx.Renderable;
import de.avwc.Main;
import de.avwc.main.Scene;
import org.apache.commons.math3.geometry.euclidean.threed.Line;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.awt.*;

/**
 * Created by andichrist on 23.04.17.
 */
public class RayUtil {
    private static final int MAX_RECURSION_DEPTH = 1;

    public static int castPrimary(Line line, int depth) {
        if (depth > MAX_RECURSION_DEPTH) {
            return Color.BLACK.getRGB();
        }

        // which object hits by the ray?
        Renderable intersect = null;
        double t = Double.MAX_VALUE;

        // find intersection with objects
        for (Renderable o : Scene.getInstance().getObjects()) {
            double t2 = o.intersect(line);
            // new t is between the eye AND object?
            if (t2 > 0 && t2 < t) {
                intersect = o;
                t = t2;
            }
        }

        if (intersect != null) {
            return intersect.getColor(getPosition(line, t), depth);
        } else {
            return Color.BLACK.getRGB();
        }
    }

    private static Vector3D getPosition(Line line, double t) {
        return line.getOrigin().add(line.getDirection().scalarMultiply(t));
    }

    public static boolean castShadow(Line line) {
        double t = Double.MAX_VALUE;

        // find intersection with objects
        for (Renderable o : Scene.getInstance().getObjects()) {
            double t2 = o.intersect(line);
            // new t is between the eye AND object?
            if (t2 > 0 && t2 < t) {
                return true;
            }
        }

        return false;
    }

}

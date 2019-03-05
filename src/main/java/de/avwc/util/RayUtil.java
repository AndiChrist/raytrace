package de.avwc.util;

import de.avwc.gfx.Renderable;
import de.avwc.main.Scene;
import org.hipparchus.geometry.euclidean.threed.Line;

/**
 * Created by andichrist on 23.04.17.
 */
public class RayUtil {

    public static boolean castShadow(Scene scene, Line line) {
        double t = Double.MAX_VALUE;

        // find intersection with objects
        for (Renderable object : scene.getObjects()) {
            double intersection = object.intersect(line);
            // new t is between the eye AND object?
            if (intersection > 0 && intersection < t) {
                return true;
            }
        }

        return false;
    }

}

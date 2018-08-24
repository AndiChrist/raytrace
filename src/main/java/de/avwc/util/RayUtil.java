package de.avwc.util;

import de.avwc.gfx.Renderable;
import de.avwc.main.RayScene;
import org.apache.commons.math3.geometry.euclidean.threed.Line;

/**
 * Created by andichrist on 23.04.17.
 */
public class RayUtil {

    public static boolean castShadow(Line line) {
        double t = Double.MAX_VALUE;

        // find intersection with objects
        for (Renderable object : RayScene.getInstance().getObjects()) {
            double intersection = object.intersect(line);
            // new t is between the eye AND object?
            if (intersection > 0 && intersection < t) {
                return true;
            }
        }

        return false;
    }

}

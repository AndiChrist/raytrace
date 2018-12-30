package de.avwc.util;

import de.avwc.gfx.Ray;
import de.avwc.gfx.Renderable;
import de.avwc.main.RayScene;

/**
 * Created by andichrist on 23.04.17.
 */
public class RayUtil {

    public static boolean castShadow(Ray ray) {

        // find intersection with objects
        for (Renderable object : RayScene.getInstance().getObjects()) {
            double intersection = object.intersect(ray);
            // new t is between the eye AND object?
            if (intersection > 0 && intersection < Double.MAX_VALUE) {
                return true;
            }
        }

        return false;
    }

}

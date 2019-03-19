package de.avwc.util;

import de.avwc.gfx.Ray;
import de.avwc.gfx.Renderable;
import de.avwc.main.RayScene;
import javafx.scene.paint.Color;
import org.hipparchus.geometry.euclidean.threed.Vector3D;

public class ColorUtil {
    private static final int MAX_RECURSION_DEPTH = 3;

    public static Color castPrimary(Ray ray, int depth) {
        Color color = Color.BLACK;

        if (depth > MAX_RECURSION_DEPTH) {
            return color;
        }

        // which object hits by the ray?
        Renderable intersectObject = null;
        double t = Double.MAX_VALUE;

        // find intersection with objects
        for (Renderable object : RayScene.getInstance().getObjects()) {
            double intersection = object.intersect(ray);
            // new t is between the eye AND object?
            if (intersection > 0 && intersection < t) {
                intersectObject = object;
                t = intersection;
            }
        }

        // get color from intersected object
        if (intersectObject != null) {
           // System.out.println("intersectObject = " + intersectObject.getName());

            color = intersectObject.getColor(getPosition(ray, t), depth);
        }

        return color;
    }

    private static Vector3D getPosition(Ray ray, double t) {
        return ray.getOrigin().add(ray.getDirection().scalarMultiply(t));
    }

    public static Vector3D scalarMultiply(Color c, double reflectionIndex) {
        return new Vector3D(c.getRed(), c.getGreen(), c.getBlue()).scalarMultiply(reflectionIndex);
    }

}

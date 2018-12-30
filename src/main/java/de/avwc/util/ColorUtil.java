package de.avwc.util;

import de.avwc.gfx.Ray;
import de.avwc.gfx.Renderable;
import de.avwc.main.RayScene;
import javafx.scene.paint.Color;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

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
/*
    public static Vector<Euclidean3D> scalarMultiply(Color c, double reflectionIndex) {
        return new Vector3D(c.getRed(), c.getGreen(), c.getBlue()).scalarMultiply(reflectionIndex);
    }
*/
/*
    public static Color scalarMultiply(Color c, double reflectionIndex) {
        return Color.rgb((int) (c.getRed() * reflectionIndex), (int) (c.getGreen() * reflectionIndex), (int) (c.getBlue() * reflectionIndex));
    }
*/
    public static Vector3D scalarMultiply(Color c, double reflectionIndex) {
        return new Vector3D(c.getRed() * reflectionIndex, c.getGreen() * reflectionIndex, c.getBlue() * reflectionIndex);
    }
/*
    public static Color add(Color a, Color b) {
        return Color.rgb((int) (a.getRed() + b.getRed()), (int) (a.getGreen() + b.getGreen()), (int) (a.getBlue() + b.getBlue()));
    }
*/
    public static Vector3D add(Color a, Color b) {
        return new Vector3D(a.getRed() + b.getRed(), a.getGreen() + b.getGreen(), a.getBlue() + b.getBlue());
    }

}

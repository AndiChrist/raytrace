package de.avwc.util;

import de.avwc.geometry.Renderable;
import de.avwc.main.Main;
import de.avwc.main.Scene;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.awt.*;

/**
 * Created by andichrist on 23.04.17.
 */
public class Ray {
    public Vector3D origin;
    public Vector3D direction;

    public Ray(Vector3D origin, Vector3D direction) {
        this.origin = origin;
        this.direction = direction.normalize();
    }

    /*
    public Ray(Vector3D fromPoint, Vector3D toPoint, boolean dummy) {
        this.origin = fromPoint;
        this.direction = toPoint.subtract(fromPoint).normalize();
    }
    */

    // find first Renderable following the ray
    public int castPrimary(int depth) {
        if (depth > Main.MAX_RECURSION_DEPTH) {
            return Color.BLACK.getRGB();
        }

        // which object hits by the ray?
        Renderable intersect = null;
        double t = Double.MAX_VALUE - 1;

        // find intersection with objects
        for (Renderable o : Scene.getInstance().getObjects()) {
            double t2 = o.intersect(this);
            // new t is between the eye AND object?
            if (t2 > 0 && t2 < t) {
                intersect = o;
                t = t2;
            }
        }

        if (intersect != null) {
            return intersect.getColor(this.getPosition(t), depth);
        } else {
            return Color.BLACK.getRGB();
        }
    }

    public boolean castShadow() {
        double t = Double.MAX_VALUE - 1;
        
        // find intersection with objects
        for (Renderable o : Scene.getInstance().getObjects()) {
            double t2 = o.intersect(this);
            // new t is between the eye AND object?
            if (t2 > 0 && t2 < t) {
                return true;
            }
        }

        return false;
    }

    private Vector3D getPosition(double t) {
        return origin.add(direction.scalarMultiply(t));
    }
}

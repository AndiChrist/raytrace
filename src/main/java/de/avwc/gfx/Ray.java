package de.avwc.gfx;

import org.hipparchus.geometry.euclidean.threed.Line;
import org.hipparchus.geometry.euclidean.threed.Vector3D;

public class Ray extends Line {
    public Ray(Vector3D p1, Vector3D p2, double tolerance) {
        super(p1, p2, tolerance);
    }

    public Ray(Ray ray) {
        super(ray);
    }
}

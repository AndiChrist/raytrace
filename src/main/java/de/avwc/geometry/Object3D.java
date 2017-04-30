package de.avwc.geometry;

import de.avwc.util.Ray;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

/**
 * Created by andichrist on 23.04.17.
 */
public interface Object3D {
    double intersect(Ray ray);

    Vector3D getNormal(Vector3D position);

    int getColor(Vector3D position, int depth);

    Vector3D getCentroid();
}

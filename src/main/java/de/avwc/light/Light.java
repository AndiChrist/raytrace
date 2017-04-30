package de.avwc.light;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

/**
 * Created by andichrist on 23.04.17.
 */
public interface Light {
    Vector3D getPosition();
    Vector3D getIntensity(Vector3D fromPosition);
}

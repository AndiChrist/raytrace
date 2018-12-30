package de.avwc.gfx.light;

import de.avwc.util.Debuggable;
import org.hipparchus.geometry.euclidean.threed.Vector3D;

/**
 * Created by andichrist on 23.04.17.
 */
public interface Light extends Debuggable {
    Vector3D getPosition();
    Vector3D getIntensity();
}

package de.avwc.gfx;

import de.avwc.util.Debuggable;
import javafx.scene.paint.Color;
import org.apache.commons.math3.geometry.euclidean.threed.Line;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

/**
 * Created by andichrist on 23.04.17.
 */
public interface Renderable extends Debuggable {
    double intersect(Line ray);

    Vector3D getNormal(Vector3D position);

    Color getColor(Vector3D position, int depth);

    Vector3D getCenter();

    String getName();
}

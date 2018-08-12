package de.avwc.gfx;

import de.avwc.main.RayScene;
import de.avwc.util.Debuggable;
import org.hipparchus.geometry.euclidean.threed.Line;
import org.hipparchus.geometry.euclidean.threed.Vector3D;

import javafx.scene.paint.Color;

/**
 * Created by andichrist on 23.04.17.
 */
public interface Renderable extends Debuggable {
    double intersect(Line ray);

    Vector3D getNormal(Vector3D position);

    Color getColor(Vector3D position, int depth);

    Vector3D getCenter();

    void setScene(RayScene scene);

    String getName();
}

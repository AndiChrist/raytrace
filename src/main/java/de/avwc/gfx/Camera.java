package de.avwc.gfx;

import de.avwc.main.RayScene;
import de.avwc.util.Debuggable;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import static java.lang.Math.PI;
import static java.lang.Math.tan;

/**
 * Created by andichrist on 23.04.17.
 */
public class Camera implements Debuggable {

    // camera position
    private Vector3D position;

    // (0, 1, 0)
    private final Vector3D up = Vector3D.PLUS_J;

    private int left;
    private int right;

    private int top;
    private int bottom;

    private Vector3D W;
    private Vector3D U;
    private Vector3D V;

    private double distance; // radians; π/4 = 90°
    private Vector3D W_d_negated;

    public Camera(Vector3D position, Vector3D direction) {
        RayScene rayScene = RayScene.getInstance();

        this.position = position;

        W = position.subtract(direction).normalize();
        U = up.crossProduct(W).normalize();
        V = W.crossProduct(U).normalize();

        left = -rayScene.getWidth() / 2;
        right = left * -1;

        top = rayScene.getHeight() / 2;
        bottom = top * -1;

        distance = top / tan(PI / 4.0d);
        //distance = top / tan(PI / 4.0d) / 2.0d;
        W_d_negated = W.scalarMultiply(-distance);
    }

    /* getter */
    public Vector3D getPosition() {
        return position;
    }

    public Vector3D getDirection(int i, int j) {
        // u = l + (r − l)(i + 0.5)/nx
        // v = b + (t − b)(j + 0.5)/ny

        // from left to right
        double u = left + i;
        // from top to bottom
        double v = top - j;

        // direction from camera to current pixel
        Vector3D direction = Vector3D.ZERO
                .add(U.scalarMultiply(u))
                .add(V.scalarMultiply(v))
                .add(W_d_negated);

        //return position.add(direction);
        return direction;
    }
}

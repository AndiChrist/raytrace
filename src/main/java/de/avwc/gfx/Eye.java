package de.avwc.gfx;

import de.avwc.main.RayScene;
import de.avwc.util.Debuggable;
import org.hipparchus.geometry.euclidean.threed.Vector3D;

import static java.lang.Math.PI;
import static java.lang.Math.tan;
import static org.apache.commons.math3.geometry.euclidean.threed.Vector3D.PLUS_J;


/**
 * Created by andichrist on 23.04.17.
 */
public class Eye implements Debuggable {

    // eye position
    private Vector3D position;

    private int left;
    private int right;

    private int top;
    private int bottom;

    private Vector3D U;
    private Vector3D V;

    private Vector3D W_d_negated;  // towards center of screen

    // perspective views
    // p(t) = E + t(-dW + uU + vV)
    // orthographic view
    // p(t) = E + uU + vV -tW
    public Eye(Vector3D position, Vector3D direction) {
        System.out.println("direction = " + direction);

        RayScene rayScene = RayScene.getInstance();

        this.position = position;

        Vector3D W = position.subtract(direction).normalize();
        // PLUS_J = {0, 1, 0}
        U = PLUS_J.crossProduct(W).normalize();
        V = W.crossProduct(U).normalize();

        System.out.println("W = " + W);

        left = -rayScene.getWidth() / 2;
        right = left * -1;

        top = rayScene.getHeight() / 2;
        bottom = top * -1;

        // radians; π/4 = 90°
        double distance = top / tan(PI / 4.0d) / 2.0d;
        //double distance = top / tan(PI / 4.0d) / 2.0d;
        //double distance = position.distance(direction);
        System.out.println("distance = " + distance);

        W_d_negated = W.scalarMultiply(-distance);
    }

    /* getter */
    public Vector3D getPosition() {
        return position;
    }

    public Vector3D getDirection(int i, int j) {
        int nx = right - left;
        int ny = top - bottom;
        // u = (l + (r − l)(i + 0.5))/nx
        // v = (b + (t − b)(j + 0.5))/ny

        // from left to right
        //double u = (left + (right - left) * (i + 0.5)) / nx;
        double u = left + i;

        // from top to bottom
        //double v = (bottom + (top - bottom) * (j + 0.5)) / ny;
        double v = top - j;

        // direction from eye to current pixel
        Vector3D direction = Vector3D.ZERO
                .add(U.scalarMultiply(u))
                .add(V.scalarMultiply(v))
                .add(W_d_negated);

        //return position.add(direction);
        return direction;
    }
}

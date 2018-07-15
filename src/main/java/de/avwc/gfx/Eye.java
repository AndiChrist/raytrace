package de.avwc.gfx;

import de.avwc.util.Debuggable;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import static java.lang.Math.PI;
import static java.lang.Math.tan;

/**
 * Created by andichrist on 23.04.17.
 */
public class Eye implements Debuggable {

    // camera position
    private Vector3D position;

    private final Vector3D up;

    private int left;
    private int right;

    private int top;
    private int bottom;

    private Vector3D W;
    private Vector3D U;
    private Vector3D V;

    private double distance; // radians; π/4 = 90°
    private Vector3D W_d_negated;

    public Eye(Vector3D position, Vector3D direction) {
        this.position = position;

        up = Vector3D.PLUS_J; // (0, 1, 0)

        W = position.subtract(direction).normalize();
        U = up.crossProduct(W).normalize();
        V = W.crossProduct(U).normalize();
    }

    public void setDimension(int width, int height) {
        left = -width / 2;
        right = left * -1;

        top = height / 2;
        bottom = top * -1;

        distance = top / tan(PI / 4) / 2;
        W_d_negated = W.scalarMultiply(distance * -1);
    }

    /* getter */

    public int getLeft() {
        return left;
    }

    public int getRight() {
        return right;
    }

    public int getTop() {
        return top;
    }

    public int getBottom() {
        return bottom;
    }

    public Vector3D getPosition() {
        return position;
    }

    public Vector3D getU() {
        return U;
    }

    public Vector3D getV() {
        return V;
    }

    public Vector3D getW_d_negated() {
        return W_d_negated;
    }
}

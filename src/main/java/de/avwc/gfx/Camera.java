package de.avwc.gfx;

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
    // where to look at (direction); here: looking to origin (0, 0, 0)
    private Vector3D direction = Vector3D.ZERO;

    //private final Vector3D right;

    private final Vector3D up;


    private int left; // left
    private int right; // right

    private int top; // top
    private int bottom; // bottom

    private Vector3D W;
    private Vector3D U;
    private Vector3D V;

    private double distance; // radians; π/4 = 90°
    private Vector3D W_d_negated;

    public Camera(Vector3D position, Vector3D direction) {
        //this.eye = new Vector3D(0, 0, -10);
        this.position = position;
        this.direction = direction;

        up = Vector3D.PLUS_J; // (0, 1, 0)

        W = position.subtract(direction).normalize();
        U = up.crossProduct(W).normalize();
        V = W.crossProduct(U).normalize();
    }

    public void setDimension(int width, int height) {
        left = -width / 2;
        //left = -Main.WIDTH / 2;
        right = left * -1;

        top = height / 2;
        //top = Main.HEIGHT / 2;
        bottom = top * -1;

        distance = top / tan(PI / 4) / 2;
        W_d_negated = W.scalarMultiply(distance * -1);
    }

    /* getter and setter */

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public int getBottom() {
        return bottom;
    }

    public void setBottom(int bottom) {
        this.bottom = bottom;
    }

    public Vector3D getPosition() {
        return position;
    }

    public void setPosition(Vector3D position) {
        this.position = position;
    }

    public Vector3D getW() {
        return W;
    }

    public void setW(Vector3D w) {
        W = w;
    }

    public Vector3D getU() {
        return U;
    }

    public void setU(Vector3D u) {
        U = u;
    }

    public Vector3D getV() {
        return V;
    }

    public void setV(Vector3D v) {
        V = v;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public Vector3D getW_d_negated() {
        return W_d_negated;
    }

    public void setW_d_negated(Vector3D w_d_negated) {
        W_d_negated = w_d_negated;
    }

}

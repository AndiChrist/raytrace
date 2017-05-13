package de.avwc.geometry;

import de.avwc.main.Main;
import de.avwc.util.Debuggable;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import static java.lang.Math.PI;
import static java.lang.Math.tan;

/**
 * Created by andichrist on 23.04.17.
 */
public class Camera {

    private int left = -Main.WIDTH / 2; // left
    private int right = left * -1; // right

    private int top = Main.HEIGHT / 2; // top
    private int bottom = top * -1; // bottom

    private Vector3D UP = Vector3D.PLUS_J; // (0, 1, 0)
    private Vector3D eye = new Vector3D(0, 0, -10);
    private Vector3D Z = Vector3D.ZERO;

    private Vector3D W = eye.normalize();//eye.subtract(Z).normalize(); ???
    private Vector3D U = UP.crossProduct(W).normalize();
    private Vector3D V = W.crossProduct(U).normalize();

    private double d = top / tan(PI / 4) / 2; // PI/4 = 90°
    private Vector3D W_d_negated = W.scalarMultiply(d * -1);

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

    public Vector3D getUP() {
        return UP;
    }

    public void setUP(Vector3D UP) {
        this.UP = UP;
    }

    public Vector3D getEye() {
        return eye;
    }

    public void setEye(Vector3D eye) {
        this.eye = eye;
    }

    public Vector3D getZ() {
        return Z;
    }

    public void setZ(Vector3D z) {
        Z = z;
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

    public double getD() {
        return d;
    }

    public void setD(double d) {
        this.d = d;
    }

    public Vector3D getW_d_negated() {
        return W_d_negated;
    }

    public void setW_d_negated(Vector3D w_d_negated) {
        W_d_negated = w_d_negated;
    }

}

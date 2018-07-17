package de.avwc.gfx;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import de.avwc.main.Scene;
import de.avwc.util.Vector3DUtil;
import org.apache.commons.math3.geometry.euclidean.threed.Line;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.awt.*;
import java.lang.reflect.Field;
import java.util.List;

import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * AABB (axis-aligned bounding box)
 * Created by andichrist on 24.04.17.
 */
public class Cube implements Renderable {

    private Vector3D min;
    private Vector3D max;
    private Vector3D rotate;
    private String name;

    private Pigment pigment = new Pigment(Color.YELLOW, this); // orange
    private Scene scene;

    public Cube(Vector3D min, Vector3D max, Vector3D rotate, String name) {
        this.min = min;
        this.max = max;
        this.rotate = rotate;
        this.name = name;
    }

    public Cube(Vector3D min, Vector3D max, Vector3D rotate, String name, Pigment pigment) {
        this.min = min;
        this.max = max;
        this.rotate = rotate;
        this.name = name;
        this.pigment = pigment;
    }

    public Cube(Vector3D min, Vector3D max, Vector3D rotate, String name, Color color) {
        this.min = min;
        this.max = max;
        this.rotate = rotate;
        this.name = name;
        this.pigment = new Pigment(color, this);
    }

    public Cube() {
    }

    @Override
    public double intersect(Line ray) {
        Vector3D direction = ray.getDirection();
        Vector3D origin = ray.getOrigin();

        Vector3D inverseDirection = Vector3DUtil.inverse(direction);

        int[] sign = { 0, 0, 0 };

        sign[0] = inverseDirection.getX() < 0 ? 1 : 0;
        sign[1] = inverseDirection.getY() < 0 ? 1 : 0;
        sign[2] = inverseDirection.getZ() < 0 ? 1 : 0;

        Vector3D[] bounds = { min, max };

        double txmin = (bounds[sign[0]].getX() - origin.getX()) * inverseDirection.getX();
        double txmax = (bounds[1 - sign[0]].getX() - origin.getX()) * inverseDirection.getX();
        double tymin = (bounds[sign[1]].getY() - origin.getY()) * inverseDirection.getY();
        double tymax = (bounds[1 - sign[1]].getY() - origin.getY()) * inverseDirection.getY();
        double tzmin = (bounds[sign[2]].getZ() - origin.getZ()) * inverseDirection.getZ();
        double tzmax = (bounds[1 - sign[2]].getZ() - origin.getZ()) * inverseDirection.getZ();

        if (txmin > tymax || tymin > txmax)
            return Double.MAX_VALUE;

        txmin = max(tymin, txmin);
        txmax = min(tymax, txmax);

        if (txmin > tzmax || tzmin > txmax)
            return Double.MAX_VALUE;

        txmin = max(tzmin, txmin);
        txmax = min(tzmax, txmax);

        double[] result = { txmin, txmax }; // ??

        return result[0];
    }

    @Override
    public Vector3D getNormal(Vector3D position) {
        return position.subtract(getCenter()).normalize();
    }

    @Override
    public Color getColor(Vector3D position, int depth) {
        return pigment.getColor(position, depth);
    }

    @Override
    public Vector3D getCenter() {
        Vector3D size = getSize();

        return min.add(size.scalarMultiply(0.5));
    }

    @Override
    public void setScene(Scene scene) {
        this.scene = scene;
    }

    private Vector3D getSize() {
        return max.subtract(min);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Vector3D getMin() {
        return min;
    }

    // ArrayList
    public void setMin(List<Double> mins) {
        this.min = new Vector3D(mins.get(0), mins.get(1), mins.get(2));
    }

    public Vector3D getMax() {
        return max;
    }

    // ArrayList
    public void setMax(List<Double> maxs) {
        this.max = new Vector3D(maxs.get(0), maxs.get(1), maxs.get(2));
    }

    public Vector3D getRotate() {
        return rotate;
    }

    // ArrayList
    public void setRotate(List<Double> rotates) {
        this.max = new Vector3D(rotates.get(0), rotates.get(1), rotates.get(2));
    }

    public Pigment getPigment() {
        return pigment;
    }

    public void setPigment(Pigment pigment) {
        this.pigment = pigment;
    }

    @JsonSetter("color")
    @JsonIgnore
    public void setPigment(List<Integer> colors) {
        Color color = new Color (colors.get(0), colors.get(1), colors.get(2));
        this.pigment = new Pigment(color, this);
    }

}

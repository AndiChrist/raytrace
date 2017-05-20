package de.avwc.gfx;

import de.avwc.util.Vector3DUtil;
import org.apache.commons.math3.geometry.euclidean.threed.Line;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.awt.*;
import java.lang.reflect.Field;

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
    private Pigment pigment = new Pigment(new Vector3D(1,1,0), this); // orange

    public Cube(Vector3D min, Vector3D max, Vector3D rotate) {
        this.min = min;
        this.max = max;
        this.rotate = rotate;
    }

    public Cube(Vector3D min, Vector3D max, Vector3D rotate, Pigment pigment) {
        this.min = min;
        this.max = max;
        this.rotate = rotate;
        this.pigment = pigment;
    }

    public Cube(Vector3D min, Vector3D max, Vector3D rotate, Color color) {
        this.min = min;
        this.max = max;
        this.rotate = rotate;
        this.pigment = new Pigment(new Vector3D(color.getRed()/255, color.getGreen()/255, color.getBlue()/255), this);
    }

    public Cube(Vector3D min, Vector3D max, Vector3D rotate, String colorValue) {
        this.min = min;
        this.max = max;
        this.rotate = rotate;
        Color color;
        try {
            Field field = Color.class.getField(colorValue);
            color = (Color)field.get(null);
        } catch (Exception e) {
            color = Color.BLACK; // Not defined
        }
        this.pigment = new Pigment(new Vector3D(color.getRed()/255, color.getGreen()/255, color.getBlue()/255), this);
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
        return position.subtract(getCentroid()).normalize();
    }

    @Override
    public int getColor(Vector3D position, int depth) {
        return pigment.getRGB(position, depth);
    }

    @Override
    public Vector3D getCentroid() {
        Vector3D size = getSize();

        return min.add(size.scalarMultiply(0.5));
    }

    private Vector3D getSize() {
        return max.subtract(min);
    }
}

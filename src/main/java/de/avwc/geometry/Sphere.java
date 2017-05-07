package de.avwc.geometry;

import de.avwc.util.Ray;
import de.avwc.util.Vector3DUtil;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.awt.*;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Set;

import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * Created by andichrist on 23.04.17.
 */
public class Sphere implements Object3D {

    private Vector3D center;
    private double radius;
    private Pigment pigment = new Pigment(new Vector3D(0, 1, 0), this); // green

    public Sphere(Vector3D center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    public Sphere(Vector3D center, double radius, Pigment pigment) {
        this.center = center;
        this.radius = radius;
        this.pigment = pigment;
    }

    public Sphere(Vector3D center, Integer radius, Color color) {
        this.center = center;
        this.radius = radius;
        this.pigment = new Pigment(new Vector3D(color.getRed() / 255, color.getGreen() / 255, color.getBlue() / 255), this);
    }

    public Sphere(Vector3D center, Integer radius, String colorValue) {
        this.center = center;
        this.radius = radius;
        Color color;
        try {
            Field field = Color.class.getField(colorValue);
            color = (Color)field.get(null);
        } catch (Exception e) {
            color = Color.BLACK; // Not defined
        }
        this.pigment = new Pigment(new Vector3D(color.getRed() / 255, color.getGreen() / 255, color.getBlue() / 255), this);
    }

    @Override
    public double intersect(Ray ray) {
        Vector3D rayDirection = ray.direction;
        Vector3D rayOrigin = ray.origin;

        Vector3D origin = rayOrigin.subtract(this.center); // eye center

        double a = rayDirection.dotProduct(rayDirection);
        double b = 2 * rayDirection.dotProduct(origin);
        double c = origin.dotProduct(origin) - this.radius * this.radius;

        Set<Double> results = Vector3DUtil.quadraticFormula(a, b, c);
        Iterator iterator = results.iterator();

        double result;
        if (results.size() == 0) {
            result = Double.MAX_VALUE;
        } else if (results.size() == 1) {
            result = (double) iterator.next(); // t0 == t1
        } else {
            double t0 = (double) iterator.next();
            double t1 = (double) iterator.next();

            double min = min(t0, t1);
            double max = max(t0, t1);

            // if t1 is less than zero, the object is in the ray's negative direction
            // and consequently the ray misses the sphere
            if (max < 0) {
                result = Double.MAX_VALUE;
            } else { // max >= 0
                // if min is less than zero, the intersection point is at max
                // else the intersection point is at min
                result = (min < 0) ? max : min;
            }
        }

        return result;
    }

    @Override
    public Vector3D getNormal(Vector3D position) {
        return position.subtract(center).normalize();
    }

    @Override
    public int getColor(Vector3D position, int depth) {
        return pigment.getRGB(position, depth);
    }

    @Override
    public Vector3D getCentroid() {
        return this.center;
    }

    @Override
    public String toString() {
        return "center: " + center +
                System.lineSeparator() +
                "radius: " + radius +
                System.lineSeparator() +
                "pigment: " + pigment +
                System.lineSeparator();
    }

}

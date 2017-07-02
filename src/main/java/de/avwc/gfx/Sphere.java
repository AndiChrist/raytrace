package de.avwc.gfx;

import de.avwc.util.Vector3DUtil;
import org.apache.commons.math3.geometry.euclidean.threed.Line;
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
public class Sphere implements Renderable {

    private Vector3D center;
    private double radius;
    private String name;

    private Double specular;
    private Double lambert;
    private Double ambient;

    private Pigment pigment = new Pigment(Color.WHITE, this); // green

    public Sphere(Vector3D center, double radius, String name) {
        this.center = center;
        this.radius = radius;
        this.name = name;
    }

    public Sphere(Vector3D center, double radius, String name, Pigment pigment) {
        this.center = center;
        this.radius = radius;
        this.name = name;
        this.pigment = pigment;
    }

    public Sphere(Vector3D center, Integer radius, String name, Color color) {
        this.center = center;
        this.radius = radius;
        this.name = name;
        this.pigment = new Pigment(color, this);
    }

    public Sphere(Vector3D center, Integer radius, String name, String colorValue) {
        this.center = center;
        this.radius = radius;
        this.name = name;
        Color color;
        try {
            Field field = Color.class.getField(colorValue);
            color = (Color)field.get(null);
        } catch (Exception e) {
            color = Color.BLACK; // Not defined
        }
        this.pigment = new Pigment(color, this);
    }

    @Override
    public double intersect(Line ray) {
        Vector3D rayDirection = ray.getDirection();
        Vector3D rayOrigin = ray.getOrigin();

        Vector3D origin = rayOrigin.subtract(this.center); // eye center

        double a = rayDirection.dotProduct(rayDirection);
        double b = 2 * rayDirection.dotProduct(origin);
        double c = origin.dotProduct(origin) - this.radius * this.radius;

        // ƒ(x) = ax² + bx + c
        // x₁, x₂ = (-b ± √(b² - 4ac)) / 2a
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

            // if max is less than zero, the object is in the ray's negative direction
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
    public Color getColor(Vector3D position, int depth) {
        return pigment.getColor(position, depth);
    }

    @Override
    public Vector3D getCentroid() {
        return this.center;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSpecular(Double specular) {
        this.specular = specular;
    }
    public void setLambert(Double lambert) {
        this.lambert = lambert;
    }
    public void setAmbient(Double ambient) {
        this.ambient = ambient;
    }
}

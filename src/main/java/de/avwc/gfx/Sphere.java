package de.avwc.gfx;

import de.avwc.main.Scene;
import de.avwc.util.Vector3DUtil;
import org.hipparchus.geometry.euclidean.threed.Line;
import org.hipparchus.geometry.euclidean.threed.Vector3D;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * Created by andichrist on 23.04.17.
 */
public class Sphere implements Renderable {

    private Vector3D center;
    private double radius;
    List<Double> position = new ArrayList<>();
    List<Double> color = new ArrayList<>();
    private String name;

    private Pigment pigment; // green
    private Scene scene;

    public Sphere() {
    }

    public Sphere(Vector3D center, Integer radius, String name, Color color) {
        this.center = center;
        this.radius = radius;
        this.name = name;
        this.pigment = new Pigment(color, this);
    }

    @Override
    public double intersect(Line ray) {
        Vector3D rayDirection = ray.getDirection();
        Vector3D rayOrigin = ray.getOrigin();

        Vector3D origin = rayOrigin.subtract(center); // eye center

        double a = rayDirection.dotProduct(rayDirection);
        double b = 2 * rayDirection.dotProduct(origin);
        double c = origin.dotProduct(origin) - radius * radius;

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
        pigment.setScene(scene);
        return pigment.getColor(position, depth);
    }

    @Override
    public Vector3D getCenter() {
        return center;
    }

    @Override
    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public List<Double> getColor() {
        return color;
    }

    public void setColor(List<Double> color) {
        this.color = color;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public List<Double> getPosition() {
        return position;
    }

    public void setPosition(List<Double> position) {
        this.position = position;
    }
}

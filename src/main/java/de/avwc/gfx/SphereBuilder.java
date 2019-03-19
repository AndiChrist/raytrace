package de.avwc.gfx;

import javafx.scene.paint.Color;
import org.hipparchus.geometry.euclidean.threed.Vector3D;

import java.util.List;

public class SphereBuilder {
    private Vector3D center;
    private Integer radius;
    private String name;
    private Color color;

    public SphereBuilder setCenter(Vector3D center) {
        this.center = center;
        return this;
    }

    public SphereBuilder setCenter(List<Double> center) {
        this.center = new Vector3D(center.get(0), center.get(1), center.get(2));
        return this;
    }

    public SphereBuilder setRadius(Integer radius) {
        this.radius = radius;
        return this;
    }

    public SphereBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public SphereBuilder setColor(Color color) {
        this.color = color;
        return this;
    }

    public SphereBuilder setColor(List<Integer> color) {
        this.color = Color.rgb(color.get(0), color.get(1), color.get(2), color.get(3));
        return this;
    }

    public Sphere createSphere() {
        return new Sphere(center, radius, name, color);
    }
}
package de.avwc.gfx;

import javafx.scene.paint.Color;
import org.hipparchus.geometry.euclidean.threed.Vector3D;

public class SphereBuilder {
    private Vector3D center;
    private Integer radius;
    private String name;
    private Color color;

    public SphereBuilder setCenter(Vector3D center) {
        this.center = center;
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

    public Sphere createSphere() {
        return new Sphere(center, radius, name, color);
    }
}
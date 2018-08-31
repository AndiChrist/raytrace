package de.avwc.gfx;

import javafx.scene.paint.Color;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class CubeBuilder {
    private Vector3D min;
    private Vector3D max;
    private Vector3D rotate;
    private String name;
    private Color color;

    public CubeBuilder setMin(Vector3D min) {
        this.min = min;
        return this;
    }

    public CubeBuilder setMax(Vector3D max) {
        this.max = max;
        return this;
    }

    public CubeBuilder setRotate(Vector3D rotate) {
        this.rotate = rotate;
        return this;
    }

    public CubeBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public CubeBuilder setColor(Color color) {
        this.color = color;
        return this;
    }

    public Cube createCube() {
        return new Cube(min, max, rotate, name, color);
    }
}
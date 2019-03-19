package de.avwc.gfx;

import javafx.scene.paint.Color;
import org.hipparchus.geometry.euclidean.threed.Vector3D;

import java.util.List;

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

    public CubeBuilder setMin(List<Double> min) {
        this.min = new Vector3D(min.get(0), min.get(1), min.get(2));
        return this;
    }

    public CubeBuilder setMax(Vector3D max) {
        this.max = max;
        return this;
    }

    public CubeBuilder setMax(List<Double> max) {
        this.max = new Vector3D(max.get(0), max.get(1), max.get(2));
        return this;
    }

    public CubeBuilder setRotate(Vector3D rotate) {
        this.rotate = rotate;
        return this;
    }

    public CubeBuilder setRotate(List<Double> rotate) {
        this.rotate = new Vector3D(rotate.get(0), rotate.get(1), rotate.get(2));
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

    public CubeBuilder setColor(List<Integer> color) {
        this.color = Color.rgb(color.get(0), color.get(1), color.get(2), color.get(3));
        return this;
    }

    public Cube createCube() {
        return new Cube(min, max, rotate, name, color);
    }
}
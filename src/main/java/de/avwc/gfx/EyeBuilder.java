package de.avwc.gfx;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class EyeBuilder {
    private Vector3D position;
    private Vector3D direction;

    public EyeBuilder setPosition(Vector3D position) {
        this.position = position;
        return this;
    }

    public EyeBuilder setDirection(Vector3D direction) {
        this.direction = direction;
        return this;
    }

    public Eye createEye() {
        return new Eye(position, direction);
    }
}
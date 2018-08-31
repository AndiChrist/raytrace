package de.avwc.gfx;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class CameraBuilder {
    private Vector3D position;
    private Vector3D direction;

    public CameraBuilder setPosition(Vector3D position) {
        this.position = position;
        return this;
    }

    public CameraBuilder setDirection(Vector3D direction) {
        this.direction = direction;
        return this;
    }

    public Camera createCamera() {
        return new Camera(position, direction);
    }
}
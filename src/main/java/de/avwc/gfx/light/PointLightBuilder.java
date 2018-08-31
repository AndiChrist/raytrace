package de.avwc.gfx.light;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class PointLightBuilder {
    private Vector3D position;
    private Vector3D intensity;

    public PointLightBuilder setPosition(Vector3D position) {
        this.position = position;
        return this;
    }

    public PointLightBuilder setIntensity(Vector3D intensity) {
        this.intensity = intensity;
        return this;
    }

    public PointLight createPointLight() {
        return new PointLight(position, intensity);
    }
}
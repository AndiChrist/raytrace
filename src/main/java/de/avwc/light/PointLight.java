package de.avwc.light;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

/**
 * Created by andichrist on 23.04.17.
 */
public class PointLight implements Light {
    private Vector3D position;
    private Vector3D intensity; // RGB

    public PointLight(Vector3D position, Vector3D intensity) {
        this.position = position;
        this.intensity = intensity;
    }

    @Override
    public Vector3D getPosition() {
        return position;
    }

    @Override
    public Vector3D getIntensity(Vector3D fromPosition) {
        return intensity;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("position: ").append(position);
        sb.append(System.lineSeparator());
        sb.append("intensity: ").append(intensity);
        sb.append(System.lineSeparator());
        return sb.toString();
    }

}

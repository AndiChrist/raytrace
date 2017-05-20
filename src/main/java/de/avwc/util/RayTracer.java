package de.avwc.util;

import de.avwc.gfx.Camera;
import de.avwc.Main;
import de.avwc.gfx.Display;
import de.avwc.main.Scene;
import org.apache.commons.math3.geometry.euclidean.threed.Line;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

/**
 * Created by andichrist on 23.04.17.
 */
public class RayTracer {
    private Camera camera = Scene.getInstance().getCamera();

    public void trace(Display display) {
        // u = l + (r − l)(i + 0.5)/nx
        // v = b + (t − b)(j + 0.5)/ny
        // l = left, r = right, b = bottom, t = top
        for (int i = 0; i < Main.WIDTH; i++) {
            for (int j = 0; j < Main.HEIGHT; j++) {
                // from left to right
                double u = camera.getLeft() + (camera.getRight() - camera.getLeft()) * (i + 0.5) / Main.WIDTH;
                // from top to bottom
                double v = camera.getTop() + (camera.getBottom() - camera.getTop()) * (j + 0.5) / Main.HEIGHT;

                // direction from camera to current pixel
                Vector3D s = Vector3DUtil.add(camera.getU().scalarMultiply(u), camera.getV().scalarMultiply(v), camera.getW_d_negated());
                Vector3D direction = s.normalize();

                Line line = new Line(camera.getPosition(), camera.getPosition().add(direction), Main.EPSILON);

                int resultColor = Ray.castPrimary(line, 0);
                display.setPixel(i, j, resultColor);
            }
        }
    }
}

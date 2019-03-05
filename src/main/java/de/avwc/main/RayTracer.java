package de.avwc.main;

import de.avwc.gfx.Camera;
import de.avwc.Main;
import de.avwc.util.ColorUtil;
import org.hipparchus.geometry.euclidean.threed.Line;
import org.hipparchus.geometry.euclidean.threed.Vector3D;

import java.awt.*;

/**
 * Created by andichrist on 23.04.17.
 */
public class RayTracer {

    public static void trace(Scene scene, Display display) {
        Camera camera = scene.getCamera();

        // u = l + (r − l)(i + 0.5)/nx
        // v = b + (t − b)(j + 0.5)/ny
        // l = left, r = right, b = bottom, t = top
        for (int i = 0; i < scene.getWidth(); i++) {
            for (int j = 0; j < scene.getHeight(); j++) {
                // from left to right
                double u = camera.getLeft() + (camera.getRight() - camera.getLeft()) * (i + 0.5) / scene.getWidth();
                // from top to bottom
                double v = camera.getTop() + (camera.getBottom() - camera.getTop()) * (j + 0.5) / scene.getHeight();

                // direction from camera to current pixel
                Vector3D s = new Vector3D(0,0,0)
                        .add(camera.getU().scalarMultiply(u))
                        .add(camera.getV().scalarMultiply(v))
                        .add(camera.getW_d_negated());

                Line line = new Line(camera.getPosition(), camera.getPosition().add(s), Main.EPSILON);

                Color color = ColorUtil.castPrimary(scene, line, 0);

                display.setPixel(i, j, color);
            }
        }
    }

}

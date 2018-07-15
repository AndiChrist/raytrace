package de.avwc.main;

import de.avwc.gfx.Eye;
import de.avwc.Main;
import de.avwc.util.ColorUtil;
import org.apache.commons.math3.geometry.euclidean.threed.Line;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.awt.*;

/**
 * Created by andichrist on 23.04.17.
 */
public class RayTracer {

    public static void trace(Scene scene, Display display) {
        Eye eye = scene.getEye();

        // u = l + (r − l)(i + 0.5)/nx
        // v = b + (t − b)(j + 0.5)/ny
        // l = left, r = right, b = bottom, t = top
        for (int i = 0; i < scene.getWidth(); i++) {
            for (int j = 0; j < scene.getHeight(); j++) {
                // from left to right
                double u = eye.getLeft() + (eye.getRight() - eye.getLeft()) * (i + 0.5) / scene.getWidth();
                // from top to bottom
                double v = eye.getTop() + (eye.getBottom() - eye.getTop()) * (j + 0.5) / scene.getHeight();

                // direction from camera to current pixel
                Vector3D s = new Vector3D(0,0,0)
                        .add(eye.getU().scalarMultiply(u))
                        .add(eye.getV().scalarMultiply(v))
                        .add(eye.getW_d_negated());

                Line line = new Line(eye.getPosition(), eye.getPosition().add(s), Main.EPSILON);

                Color color = ColorUtil.castPrimary(scene, line, 0);

                display.setPixel(i, j, color);
            }
        }
    }

}

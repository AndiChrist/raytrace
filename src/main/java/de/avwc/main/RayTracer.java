package de.avwc.main;

import de.avwc.gfx.Camera;
import de.avwc.gfx.Pixel;
import de.avwc.util.ColorUtil;
import javafx.scene.paint.Color;
import org.apache.commons.math3.geometry.euclidean.threed.Line;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.util.function.Consumer;

import static de.avwc.RayTracingMain.ε;

/**
 * Created by andichrist on 23.04.17.
 */
public class RayTracer {

    public static final int DEPTH = 0;

    public static void trace(Consumer<Pixel> pixelPainter) {
        RayScene rayScene = RayScene.getInstance();
        Camera camera = rayScene.getCamera();
        camera.setDimension(rayScene.getWidth(), rayScene.getHeight());

        // u = l + (r − l)(i + 0.5)/nx
        // v = b + (t − b)(j + 0.5)/ny
        // l = left, r = right, b = bottom, t = top
        for (int i = 0; i < rayScene.getWidth(); i++) {
            for (int j = 0; j < rayScene.getHeight(); j++) {
                // from left to right
                double u = camera.getLeft() + (camera.getRight() - camera.getLeft()) * (i + 0.5) / rayScene.getWidth();
                // from top to bottom
                double v = camera.getTop() + (camera.getBottom() - camera.getTop()) * (j + 0.5) / rayScene.getHeight();

                // direction from camera to current pixel
                Vector3D s = Vector3D.ZERO
                        .add(camera.getU().scalarMultiply(u))
                        .add(camera.getV().scalarMultiply(v))
                        .add(camera.getW_d_negated());

                Line line = new Line(camera.getPosition(), camera.getPosition().add(s), ε);

                Color color = ColorUtil.castPrimary(line, DEPTH);

                colorPixel(pixelPainter, i, j, color);
            }
        }
    }

    private static void colorPixel(Consumer<Pixel> colorPixel, int x, int y, Color color) {
        Pixel pixel = new Pixel(x, y, color);
        colorPixel.accept(pixel);
    }
}

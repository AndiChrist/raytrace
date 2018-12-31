package de.avwc.main;

import de.avwc.gfx.Camera;
import de.avwc.gfx.Pixel;
import de.avwc.gfx.Ray;
import de.avwc.util.ColorUtil;
import javafx.scene.paint.Color;

import java.util.function.Consumer;

import static de.avwc.RayTracingMain.ε;

/**
 * Created by andichrist on 23.04.17.
 */
public class RayTracer {

    public static final int DEPTH = 3;

    public static void trace(Consumer<Pixel> pixelPainter) {
        RayScene rayScene = RayScene.getInstance();

        // camera aka. "eye"
        Camera camera = rayScene.getCamera();

        for (int i = 0; i < rayScene.getWidth(); i++) {
            for (int j = 0; j < rayScene.getHeight(); j++) {

                Ray ray = new Ray(camera.getPosition(), camera.getDirection(i, j), ε);

                Color color = ColorUtil.castPrimary(ray, DEPTH);

                colorPixel(pixelPainter, i, j, color);
            }
        }
    }

    private static void colorPixel(Consumer<Pixel> colorPixel, int x, int y, Color color) {
        Pixel pixel = new Pixel(x, y, color);
        colorPixel.accept(pixel);
    }
}

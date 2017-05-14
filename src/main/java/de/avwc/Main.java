package de.avwc;

import de.avwc.gfx.Display;
import de.avwc.util.RayTracer;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * Created by andichrist on 23.04.17.
 */
public final class Main {

    public static final int WIDTH = 1600;
    public static final int HEIGHT = 900;

    public static final double EPSILON = 0.00004;
    public static final int MAX_RECURSION_DEPTH = 3;

    private static Display display;

    private void saveImage() {
        File image = new File("Image.png");

        try {
            ImageIO.write(display.getCanvas(), "PNG", image);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        display = new Display(WIDTH, HEIGHT);

        new RayTracer().trace(display);
        Main panel = new Main();

        panel.saveImage();

        display.openJFrame();
    }



}


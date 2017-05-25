package de.avwc;

import de.avwc.gfx.Display;
import de.avwc.main.Scene;
import de.avwc.util.RayTracer;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * Created by andichrist on 23.04.17.
 */
public final class Main {

    public static final double EPSILON = 0.00004;

    public static void main(String[] args) {
        Display display = new Display();
        RayTracer.trace(display);

        Main panel = new Main();
        panel.saveImage(display);

        display.openJFrame();
    }

    private void saveImage(Display display) {
        File image = new File("Image.png");

        try {
            ImageIO.write(display.getCanvas(), "PNG", image);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


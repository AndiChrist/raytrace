package de.avwc;

import de.avwc.panel.Display;
import de.avwc.util.RayTracer;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * Created by andichrist on 23.04.17.
 */
public final class Main {

    public static final double EPSILON = 10e-12;

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


package de.avwc;

import de.avwc.main.Scene;
import de.avwc.main.Display;
import de.avwc.main.RayTracer;
import de.avwc.util.SceneJSONReader;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * Created by andichrist on 23.04.17.
 */
public final class Main {

    public static final double EPSILON = 10e-12;

    public static void main(String[] args) {
        Scene scene = null;
        try {
            scene = SceneJSONReader.readSceneJSON();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Display display = new Display(scene);

        RayTracer.trace(scene, display);

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


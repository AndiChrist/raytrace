package de.avwc;

import de.avwc.gfx.Pixel;
import de.avwc.main.RayScene;
import de.avwc.main.RayTracer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

/**
 * Created by andichrist on 23.04.17.
 */
public final class Main extends Application {

    public static final double EPSILON = 10e-12;

    private PixelWriter pixelWriter;
    private Scene scene;

    public Main() {
        WritableImage image = new WritableImage(RayScene.getWidth(), RayScene.getHeight());
        pixelWriter = image.getPixelWriter();

        ImageView imageView = new ImageView();
        imageView.setImage(image);

        StackPane root = new StackPane();
        root.getChildren().add(imageView);

        scene = new Scene(root, RayScene.getWidth(), RayScene.getHeight());
    }

    public static void main(String[] args) {
        new Main().launch();
    }

/*
    public static void main(String[] args) {
        RayScene rayScene = null;
        try {
            rayScene = SceneJSONReader.readSceneJSON();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Display display = new Display(rayScene);

        RayTracer.trace(rayScene, display);

        BufferedImage image = display.getCanvas();

        saveImage(image);

        display.openJFrame();
    }
*/
    private static void saveImage(BufferedImage image) {
        File outputFile = new File("Image.png");

        try {
            ImageIO.write(image, "PNG", outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        paintRayTraceSet();
        primaryStage.setScene(scene);
        primaryStage.setTitle("Mandelbrot set");
        primaryStage.show();
    }

    private void paintRayTraceSet() {
        Consumer<Pixel> pixelPainter = (pixel) -> pixelWriter.setColor(pixel.getX(),pixel.getY(),pixel.getColor());

        RayTracer.trace(pixelPainter);
    }

}


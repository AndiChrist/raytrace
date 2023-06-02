package de.avwc;

import de.avwc.gfx.Pixel;
import de.avwc.main.RayScene;
import de.avwc.main.RayTracer;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.image.Image;
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
public final class RayTracingMain extends Application {

    public static void main(String[] args) {
        launch();
    }

    public static final double ε = 10e-10;

    private PixelWriter pixelWriter;

    @Override
    public void start(Stage stage) {
        RayScene rayScene = RayScene.getInstance();

        WritableImage image = new WritableImage(rayScene.getWidth(), rayScene.getHeight());
        pixelWriter = image.getPixelWriter();

        ImageView imageView = new ImageView();
        imageView.setImage(image);

        StackPane root = new StackPane();
        root.getChildren().add(imageView);

        Scene scene = new Scene(root, rayScene.getWidth(), rayScene.getHeight());

        paintRayTraceSet();

        stage.setScene(scene);
        stage.setTitle("Raytrace Image");
        stage.show();

        saveToFile(image);
    }

    private void paintRayTraceSet() {
        Consumer<Pixel> pixelPainter = (pixel) -> pixelWriter.setColor(pixel.getX(), pixel.getY(), pixel.getColor());

        RayTracer.trace(pixelPainter);
    }


    private void saveToFile(Image image) {
        File outputFile = new File("Image.png");
        BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
        try {
            ImageIO.write(bImage, "png", outputFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}


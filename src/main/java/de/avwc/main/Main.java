package de.avwc.main;

import de.avwc.util.RayTracer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by andichrist on 23.04.17.
 */
public final class Main extends JPanel {

    public static final int WIDTH = 1600;
    public static final int HEIGHT = 900;

    public static final double EPSILON = 0.00004;
    public static final int MAX_RECURSION_DEPTH = 3;

    private BufferedImage canvas;
    private static Main instance = null;

    private Main(int width, int height) {
        canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        // make blue canvas
        defaultCanvas(Color.BLUE);

        this.setPreferredSize(new Dimension(width, height));
    }

    private void defaultCanvas(Color color) {
        for (int i = 0; i < Main.WIDTH; i++) {
            for (int j = 0; j < Main.HEIGHT; j++) {
                canvas.setRGB(i, j, color.getRGB());
            }
        }
    }

    private void saveImage() {
        File image = new File("Image.png");

        try {
            ImageIO.write(canvas, "PNG", image);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(canvas, null, null);

    }

    private static Main getInstance() {
        if (Main.instance == null)
            Main.instance = new Main(WIDTH, HEIGHT);

        return Main.instance;
    }

    public static void setPixel(int x, int y, int rgb) {
        Main instance = getInstance();

        if (x > WIDTH - 1 || y > HEIGHT - 1 || x < 0 || y < 0) {
            return;
        }

        instance.canvas.setRGB(x, y, rgb);
        instance.repaint();
    }

    public static void main(String[] args) {
        new RayTracer().trace();
        Main panel = Main.getInstance();

        panel.saveImage();

        openJFrame(panel);
    }

    private static void openJFrame(Main panel) {
        JFrame frame = new JFrame("Raytracer");
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }


}


package de.avwc.gfx;

import de.avwc.main.Scene;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by andichrist on 14.05.17.
 */
public class Display extends JPanel {

    private BufferedImage canvas;
    private int width;
    private int height;

    public Display(int width, int height) {
        this.width = width;
        this.height = height;

        canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        // make blue canvas
        defaultCanvas(Color.BLUE);

        this.setPreferredSize(new Dimension(width, height));
    }

    public Display(Scene scene) {
        this(Scene.getInstance().getWidth(), Scene.getInstance().getHeight());
    }

    public Display() {
        this(Scene.getInstance());
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(canvas, null, null);

    }

    private void defaultCanvas(Color color) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                canvas.setRGB(i, j, color.getRGB());
            }
        }
    }

    public void setPixel(int x, int y, int rgb) {
        if (x > width - 1 || y > height - 1 || x < 0 || y < 0) {
            return;
        }

        canvas.setRGB(x, y, rgb);
        repaint();
    }

    public void openJFrame() {
        JFrame frame = new JFrame("Raytracer");
        frame.add(this);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public BufferedImage getCanvas() {
        return canvas;
    }

}

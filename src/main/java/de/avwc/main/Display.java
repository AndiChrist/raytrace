package de.avwc.main;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by andichrist on 14.05.17.
 */
@Deprecated
public class Display extends JPanel {

    private BufferedImage canvas;
    private int width;
    private int height;

    public Display(RayScene scene) {
        this.width = scene.getWidth();
        this.height = scene.getHeight();

        canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        // make blue canvas
        defaultCanvas(Color.BLUE);

        this.setPreferredSize(new Dimension(width, height));
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

    public void setPixel(int x, int y, Color color) {
        if (x > width - 1 || y > height - 1 || x < 0 || y < 0) {
            return;
        }

        canvas.setRGB(x, y, color.getRGB());
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

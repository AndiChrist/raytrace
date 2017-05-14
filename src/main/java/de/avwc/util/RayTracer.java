package de.avwc.util;

import de.avwc.gfx.Camera;
import de.avwc.Main;
import de.avwc.gfx.Display;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

/**
 * Created by andichrist on 23.04.17.
 */
public class RayTracer implements Runnable {
    public static Camera camera = new Camera();

    public void trace(Display display) {
        // u = l + (r − l)(i + 0.5)/nx
        // v = b + (t − b)(j + 0.5)/ny
        // l = left, r = right, b = bottom, t = top
        for (int i = 0; i < Main.WIDTH; i++) {
            for (int j = 0; j < Main.HEIGHT; j++) {
                double u = camera.getLeft() + (camera.getRight() - camera.getLeft()) * (i + 0.5) / Main.WIDTH;
                double v = camera.getTop() + (camera.getBottom() - camera.getTop()) * (j + 0.5) / Main.HEIGHT;

                Vector3D s = Vector3DUtil.add(camera.getU().scalarMultiply(u), camera.getV().scalarMultiply(v), camera.getW_d_negated());
                Vector3D direction = s.normalize();

                Ray ray = new Ray(camera.getEye(), direction);
                //Line line = new Line(camera.getEye(), direction, Main.EPSILON);

                int resultColor = ray.castPrimary(0);
                display.setPixel(i, j, resultColor);
            }
        }
    }

    @Override
    public void run() {

    }

/*
    public void traceXX() {
        for (int i = 0; i < Output.WIDTH; i++) {
            for (int j = 0; j < Output.HEIGHT; j++) {

                // antializing, 8x8 grid
                int resultColor = 0;
                Vector3D color = new Vector3D(); // black

                for (int row = 0; row < 8; row++) {
                    for (int column = 0; column < 8; column++) {
                        double u = camera.getLeft() + (camera.getRight() - camera.getLeft()) * (i + (column+.5)/8) / Output.WIDTH;
                        double v = camera.getTop() + (camera.getBottom() - camera.getTop()) * (j + (row+.5)/8) / Output.HEIGHT;

                        Vector3D s = Vector3D.add(camera.getU().scalarMultiplication(u), camera.getV().scalarMultiplication(v), camera.getW_d_negated());
                        Vector3D direction = s.normalize();

                        Ray ray = new Ray(camera.getEye(), direction);

                        resultColor = ray.castPrimary(0);
                        Color tmp = new Color(resultColor);

                        //color.add(new Vector3D(tmp.getRed(), tmp.getGreen(), tmp.getBlue()));
                        color = Vector3D.add(color, new Vector3D(tmp.getRed(), tmp.getGreen(), tmp.getBlue()));
                    }
                }

                color.divide(64);

                Output.setPixel(i, j, color.toInteger());
            }
        }
    }
*/
}

package de.avwc.gfx;

import de.avwc.gfx.light.Light;
import de.avwc.Main;
import de.avwc.main.Scene;
import de.avwc.util.Ray;
import de.avwc.util.Vector3DUtil;
import org.apache.commons.math3.geometry.euclidean.threed.Line;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.awt.*;

/**
 * Created by andichrist on 23.04.17.
 */
public class Pigment {
    private Renderable renderable;

    private Vector3D ambient;
    //private Color diffuse = Color.LIGHT_GRAY; // light gray
    private Vector3D diffuse = new Vector3D(0.7, 0.7, 0.7); // light gray
    //private Color specular = Color.DARK_GRAY; // dark gray
    private Vector3D specular = new Vector3D(0.3, 0.3, 0.3); // dark gray

    private double phongExponent = 5;
    private double reflectionIndex = 0.5;

    Pigment(Vector3D color) {
        this.ambient = color;
    }

    Pigment(Vector3D color, Renderable renderable) {
        this(color);
        this.renderable = renderable;
    }

    Pigment(Vector3D color, Renderable renderable, Vector3D specular) {
        this(color);
        this.renderable = renderable;
        this.specular = specular;
    }

    int getRGB(Vector3D position, int depth) {
        if (renderable == null)
            return 0;

        // start with zero vector
        Vector3D sum = Vector3D.ZERO;

        // for each LIGHT
        for (Light light : Scene.getInstance().getLights()) {
            // vector pointing to LIGHT
            Vector3D positionToLight = light.getPosition().subtract(position).normalize();

            //Ray shadow = new Ray(Vector3DUtil.move(Main.EPSILON, position, positionToLight), positionToLight);
            Line shadow = new Line(Vector3DUtil.move(Main.EPSILON, position, positionToLight), Vector3DUtil.move(Main.EPSILON, position, positionToLight).add(positionToLight), Main.EPSILON);

            boolean shadowed = Ray.castShadow(shadow);

            Vector3D retValue = Vector3D.ZERO;
            retValue = retValue.add(Vector3DUtil.multiply(ambient, light.getIntensity(position)));

            if (!shadowed) {
                // diffuse factor
                Vector3D normal = this.renderable.getNormal(position);
                double NL = Math.max(normal.dotProduct(positionToLight), 0); // angle
                retValue = retValue.add(Vector3DUtil.multiply(diffuse, light.getIntensity(position)).scalarMultiply(NL));

                // specular factor
                Vector3D reflection = normal.scalarMultiply(NL*2).subtract(positionToLight).normalize();
                Vector3D view = Scene.getInstance().getCamera().getPosition().subtract(position).normalize();
                double RV = Math.max(reflection.dotProduct(view), 0); // angle

                retValue = retValue.add(Vector3DUtil.multiply(specular, light.getIntensity(position)).scalarMultiply(Math.pow(RV, phongExponent)));

            }

            double distance = light.getPosition().subtract(position).getNorm(); // length
            sum = sum.add(retValue.scalarMultiply(1/(distance*distance)).scalarMultiply(255));

        }

        if (this.reflectionIndex > 0) {
            Vector3D normal = this.renderable.getNormal(position);
            Vector3D view = Scene.getInstance().getCamera().getPosition().subtract(position).normalize();
            double NV = Math.max(normal.dotProduct(view), 0); // angle

            Vector3D reflectionRay = normal.scalarMultiply(NV*2).subtract(view).normalize();
            //Ray reflection = new Ray(Vector3DUtil.move(Main.EPSILON, position, reflectionRay), reflectionRay);
            Line reflection = new Line(Vector3DUtil.move(Main.EPSILON, position, reflectionRay), Vector3DUtil.move(Main.EPSILON, position, reflectionRay).add(reflectionRay), Main.EPSILON);

            int res = Ray.castPrimary(reflection, depth + 1);
            Color c = new Color(res);

            Vector3D colorVector = new Vector3D(c.getRed(), c.getGreen(), c.getBlue());
            colorVector.scalarMultiply(reflectionIndex);
            sum = sum.add(colorVector);

        }

        sum = new Vector3D(Math.min(255, sum.getX()), Math.min(255, sum.getY()), Math.min(255, sum.getZ()));
        sum = new Vector3D(Math.max(0, sum.getX()), Math.max(0, sum.getY()), Math.max(0, sum.getZ()));

        Color c = new Color((int)Math.round(sum.getX()), (int)Math.round(sum.getY()), (int)Math.round(sum.getZ()));
        return c.getRGB();
    }

    @Override
    public String toString() {
        return "ambient: " + ambient;
    }

}

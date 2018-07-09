package de.avwc.gfx;

import de.avwc.gfx.light.Light;
import de.avwc.Main;
import de.avwc.main.Scene;
import de.avwc.util.ColorUtil;
import de.avwc.util.RayUtil;
import de.avwc.util.Vector3DUtil;
import org.apache.commons.math3.geometry.euclidean.threed.Line;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.awt.*;

/**
 * Created by andichrist on 23.04.17.
 */
public class Pigment {

    private Renderable renderable;
    private Color ambient;

    private static final Color DIFFUSE = Color.LIGHT_GRAY;
    private static final Color SPECULAR = Color.DARK_GRAY;

    private static final double PHONG_EXPONENT = 5;
    private static final double REFLECTION_INDEX = 0.5;

    Pigment(Color ambient) {
        this.ambient = ambient;
    }

    Pigment(Color ambient, Renderable renderable) {
        this(ambient);
        this.renderable = renderable;
    }

    // depth: recursion depth
    Color getColor(Vector3D position, int depth) {
        if (renderable == null)
            return Color.BLACK;

        // start with zero vector
        Vector3D sum = Vector3D.ZERO;

        // for each LIGHT
        for (Light light : Scene.getInstance().getLights()) {
            // vector pointing to LIGHT
            Vector3D positionToLight = light.getPosition().subtract(position).normalize();

            Vector3D newPosition = Vector3DUtil.move(position, positionToLight, Main.EPSILON);
            Line shadow = new Line(newPosition, newPosition.add(positionToLight), Main.EPSILON);

            Vector3D retValue = Vector3D.ZERO;
            retValue = retValue.add(Vector3DUtil.multiply(ambient, light.getIntensity(position)));

            boolean shadowed = RayUtil.castShadow(shadow);
            if (!shadowed) {
                // diffuse factor
                Vector3D normal = this.renderable.getNormal(position);
                double NL = Math.max(normal.dotProduct(positionToLight), 0); // angle
                retValue = retValue.add(Vector3DUtil.multiply(DIFFUSE, light.getIntensity(position)).scalarMultiply(NL));

                // specular factor
                Vector3D reflection = normal.scalarMultiply(NL*2).subtract(positionToLight).normalize();
                Vector3D view = Scene.getInstance().getCamera().getPosition().subtract(position).normalize();
                double RV = Math.max(reflection.dotProduct(view), 0); // angle

                retValue = retValue.add(Vector3DUtil.multiply(SPECULAR, light.getIntensity(position)).scalarMultiply(Math.pow(RV, PHONG_EXPONENT)));
            }

            double distance = light.getPosition().subtract(position).getNorm(); // length
            sum = sum.add(retValue.scalarMultiply(1/(distance*distance)).scalarMultiply(255));
        }

        sum = sum.add(getReflection(position, depth));

        sum = new Vector3D(Math.min(255, sum.getX()), Math.min(255, sum.getY()), Math.min(255, sum.getZ()));
        sum = new Vector3D(Math.max(0, sum.getX()), Math.max(0, sum.getY()), Math.max(0, sum.getZ()));

        return new Color((int)Math.round(sum.getX()), (int)Math.round(sum.getY()), (int)Math.round(sum.getZ()));
    }

    // error?
    private Vector3D getReflection(Vector3D position, int depth) {
        Vector3D vector = Vector3D.ZERO;
        if (REFLECTION_INDEX > 0) {
            Vector3D normal = this.renderable.getNormal(position);
            Vector3D view = Scene.getInstance().getCamera().getPosition().subtract(position).normalize();
            double NV = Math.max(normal.dotProduct(view), 0); // angle

            Vector3D reflectionRay = normal.scalarMultiply(NV*2).subtract(view).normalize();
            Vector3D newPosition = Vector3DUtil.move(position, reflectionRay, Main.EPSILON);
            Line reflection = new Line(newPosition, newPosition.add(reflectionRay), Main.EPSILON);

            Color c = ColorUtil.castPrimary(reflection, depth + 1);

            Vector3D colorVector = new Vector3D(c.getRed(), c.getGreen(), c.getBlue());
            vector = vector.add(colorVector.scalarMultiply(REFLECTION_INDEX));
        }

        return vector;
    }

    @Override
    public String toString() {
        return "ambient: " + ambient;
    }

}

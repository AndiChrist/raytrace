package de.avwc.gfx;

import de.avwc.gfx.light.Light;
import de.avwc.main.RayScene;
import de.avwc.util.ColorUtil;
import de.avwc.util.RayUtil;
import de.avwc.util.Vector3DUtil;
import javafx.scene.paint.Color;
import org.apache.commons.math3.geometry.euclidean.threed.Line;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import static de.avwc.RayTracingMain.ε;

/**
 * Created by andichrist on 23.04.17.
 */
public class Pigment {

    private Renderable renderable;
    private Color color;

    private static final Color DIFFUSE = Color.LIGHTGRAY;
    private static final Color SPECULAR = Color.DARKGRAY;

    private static final double PHONG_EXPONENT = 5;
    private static final double REFLECTION_INDEX = 0.5;

    Pigment(Color color, Renderable renderable) {
        this.color = color;
        this.renderable = renderable;
    }

    // depth: recursion depth
    Color getColor(Vector3D position, int depth) {
        if (renderable == null)
            return Color.BLACK;

        // start with zero vector
        Vector3D sum = Vector3D.ZERO;

        RayScene rayScene = RayScene.getInstance();

        // for each LIGHT
        for (Light light : rayScene.getLights()) {
            // vector pointing to LIGHT
            Vector3D positionToLight = light.getPosition().subtract(position).normalize();

            Vector3D newPosition = Vector3DUtil.move(position, positionToLight, ε);
            Line shadow = new Line(newPosition, newPosition.add(positionToLight), ε);

            Vector3D retValue = Vector3D.ZERO;
            retValue = retValue.add(Vector3DUtil.multiply(color, light.getIntensity(position)));

            boolean shadowed = RayUtil.castShadow(shadow);
            if (!shadowed) {
                // diffuse factor
                Vector3D normal = renderable.getNormal(position);
                double NL = Math.max(normal.dotProduct(positionToLight), 0); // angle
                retValue = retValue.add(Vector3DUtil.multiply(DIFFUSE, light.getIntensity(position)).scalarMultiply(NL));

                // specular factor
                Vector3D reflection = normal.scalarMultiply(NL*2).subtract(positionToLight).normalize();
                Vector3D view = rayScene.getCamera().getPosition().subtract(position).normalize();
                double RV = Math.max(reflection.dotProduct(view), 0); // angle

                retValue = retValue.add(Vector3DUtil.multiply(SPECULAR, light.getIntensity(position)).scalarMultiply(Math.pow(RV, PHONG_EXPONENT)));
            }

            double distance = light.getPosition().subtract(position).getNorm(); // length
            sum = sum.add(retValue.scalarMultiply(1/(distance*distance)).scalarMultiply(255));
        }

        sum = sum.add(getReflection(position, depth));

        sum = new Vector3D(Math.min(255, sum.getX()), Math.min(255, sum.getY()), Math.min(255, sum.getZ()));
        sum = new Vector3D(Math.max(0, sum.getX()), Math.max(0, sum.getY()), Math.max(0, sum.getZ()));

        return Color.rgb((int)Math.round(sum.getX()), (int)Math.round(sum.getY()), (int)Math.round(sum.getZ()));
    }

    // error?
    private Vector3D getReflection(Vector3D position, int depth) {

        Vector3D normal = renderable.getNormal(position);
        Vector3D view = RayScene.getInstance().getCamera().getPosition().subtract(position).normalize();
        double NV = Math.max(normal.dotProduct(view), 0); // angle

        Vector3D reflectionRay = normal.scalarMultiply(NV*2).subtract(view).normalize();
        Vector3D newPosition = Vector3DUtil.move(position, reflectionRay, ε);
        Line reflection = new Line(newPosition, newPosition.add(reflectionRay), ε);

        Color c = ColorUtil.castPrimary(reflection, depth + 1);

        Vector3D colorVector = new Vector3D(c.getRed(), c.getGreen(), c.getBlue());
        Vector3D vector = Vector3D.ZERO.add(colorVector.scalarMultiply(REFLECTION_INDEX));

        return vector;
    }

    @Override
    public String toString() {
        return color.toString();
    }

}

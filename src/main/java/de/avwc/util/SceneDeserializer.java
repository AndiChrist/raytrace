package de.avwc.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import de.avwc.gfx.Camera;
import de.avwc.gfx.Cube;
import de.avwc.gfx.Sphere;
import de.avwc.gfx.light.PointLight;
import de.avwc.main.RayScene;
import javafx.scene.paint.Color;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by andichrist on 07.05.17.
 */
public class SceneDeserializer extends StdDeserializer<RayScene> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SceneDeserializer.class);

    public final static int X = 0;
    public final static int Y = 1;
    public final static int Z = 2;


    // default (no arg) constructor
    public SceneDeserializer() {
        this(null);
    }

    public SceneDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public RayScene deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        RayScene rayScene = RayScene.getInstance();
        JsonNode node = jp.getCodec().readTree(jp);

        Integer width = node.get("width").asInt();
        Integer height = node.get("height").asInt();

        rayScene.setWidth(width);
        rayScene.setHeight(height);

        JsonNode objects = node.get("objects");
        JsonNode lights = node.get("lights");

        // Retrieve data from JsonObject and create Scene bean
        if (objects != null) {
            for (JsonNode object3D : objects) {
                if (object3D.get("spheres") != null) {
                    for (JsonNode sphere : object3D.get("spheres")) {
                        rayScene.addObject(readSphere(sphere));
                    }
                }

                if (object3D.get("cubes") != null) {
                    for (JsonNode cube : object3D.get("cubes")) {
                        rayScene.addObject(readCube(cube));
                    }
                }
            }
        }

        if (lights != null) {
            for (JsonNode light : lights) {
                for (JsonNode pointlight : light.get("pointlights")) {
                    rayScene.addLight(readPointLight(pointlight));
                }
            }
        }

        JsonNode cameraNode = node.get("camera");
        Camera camera = readCamera(cameraNode);
        rayScene.setCamera(camera);

        LOGGER.info(rayScene.toString());

        return rayScene;
    }

    private static PointLight readPointLight(JsonNode pointLight) {
        Vector3D position = getVector(pointLight.get("position"));
        Vector3D intensity = getVector(pointLight.get("intensity"));

        return new PointLight(position, intensity);
    }

    private static Cube readCube(JsonNode cube) {
        Vector3D min = getVector(cube.get("min"));
        Vector3D max = getVector(cube.get("max"));
        Vector3D rotate = getVector(cube.get("rotate"));
        String name = cube.get("name").asText();
        Color color = getColor(cube.get("color"));

        return new Cube(min, max, rotate, name, color);
    }

    private static Sphere readSphere(JsonNode sphere) {
        Vector3D center = getVector(sphere.get("position"));
        String name = sphere.get("name").asText();
        Integer radius = sphere.get("radius").asInt();
        Color color = getColor(sphere.get("color"));

        return new Sphere(center, radius, name, color);
    }

    private static Camera readCamera(JsonNode camera) {
        Vector3D center = getVector(camera.get("position"));
        Vector3D direction = getVector(camera.get("direction"));

        return new Camera(center, direction);
    }

    private static Vector3D getVector(JsonNode jsonNode) {
        int x = 0;
        int y = 0;
        int z = 0;

        if (jsonNode.isArray() && jsonNode.size() == 3) {
            x = jsonNode.get(X).intValue();
            y = jsonNode.get(Y).intValue();
            z = jsonNode.get(Z).intValue();
        }

        return new Vector3D(x, y, z);
    }

    private static Color getColor(JsonNode jsonNode) {
        return Color.rgb(jsonNode.get(X).intValue(), jsonNode.get(Y).intValue(), jsonNode.get(Z).intValue());
    }

}

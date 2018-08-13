package de.avwc.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;
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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by andichrist on 07.05.17.
 */
public class SceneDeserializer extends StdDeserializer<RayScene> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SceneDeserializer.class);

    // default (no arg) constructor
    public SceneDeserializer() {
        this(null);
    }

    public SceneDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public RayScene deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);

        Integer width = node.get("width").asInt();
        Integer height = node.get("height").asInt();

        RayScene.setWidth(width);
        RayScene.setHeight(height);

        JsonNode objects = node.get("objects");
        JsonNode lights = node.get("lights");

        // Retrieve data from JsonObject and create Scene bean
        if (objects != null) {
            for (JsonNode object3D : objects) {
                if (object3D.get("spheres") != null) {
                    for (JsonNode sphere : object3D.get("spheres")) {
                        RayScene.addObject(readSphere(sphere));
                    }
                }

                if (object3D.get("cubes") != null) {
                    for (JsonNode cube : object3D.get("cubes")) {
                        RayScene.addObject(readCube(cube));
                    }
                }
            }
        }

        if (lights != null) {
            for (JsonNode light : lights) {
                for (JsonNode pointlight : light.get("pointlights")) {
                    RayScene.addLight(readPointLight(pointlight));
                }
            }
        }

        JsonNode cameraNode = node.get("camera");
        Camera camera = readCamera(cameraNode);
        RayScene.setCamera(camera);

        RayScene rayScene = RayScene.getInstance();
        LOGGER.info(rayScene.toString());
        return rayScene;
    }

    private static PointLight readPointLight(JsonNode pointLight) {
        ArrayNode positionNode = (ArrayNode) pointLight.get("position");
        Vector3D position = getVector(positionNode);

        ArrayNode intensityNode = (ArrayNode) pointLight.get("intensity");
        Vector3D intensity = getVector(intensityNode);

        return new PointLight(position, intensity);
    }

    private static Cube readCube(JsonNode cube) {
        Cube object;

        String name = cube.get("name").asText();

        ArrayNode minNode = (ArrayNode) cube.get("min");
        Vector3D min = getVector(minNode);

        ArrayNode maxNode = (ArrayNode) cube.get("max");
        Vector3D max = getVector(maxNode);

        ArrayNode rotateNode = (ArrayNode) cube.get("rotate");
        Vector3D rotate = getVector(rotateNode);

        ArrayNode pigmentNode = (ArrayNode) cube.get("color");
        Color color = getColor(pigmentNode);
        object = new Cube(min, max, rotate, name, color);

        return object;
    }

    private static Sphere readSphere(JsonNode sphere) {
        Sphere object;

        ArrayNode positionNode = (ArrayNode) sphere.get("position");
        Vector3D center = getVector(positionNode);
        String name = sphere.get("name").asText();

        Integer radius = sphere.get("radius").asInt();

        ArrayNode pigmentNode = (ArrayNode) sphere.get("color");
        Color color = getColor(pigmentNode);
        object = new Sphere(center, radius, name, color);

        return object;
    }

    private static Camera readCamera(JsonNode camera) {
        ArrayNode positionNode = (ArrayNode) camera.get("position");
        ArrayNode directionNode = (ArrayNode) camera.get("direction");

        Vector3D center = getVector(positionNode);
        Vector3D direction = getVector(directionNode);

        return new Camera(center, direction);
    }

    private static Vector3D getVector(ArrayNode arrayNode) {
        List<Double> list = new ArrayList<>();
        if (arrayNode.isArray() && arrayNode.size() == 3) {
            for (final JsonNode node : arrayNode) {
                list.add(node.asDouble());
            }
        }

        Double[] array = list.toArray(new Double[0]);
        double[] result = Stream.of(array).mapToDouble(Double::doubleValue).toArray();

        return new Vector3D(result);
    }

    private static Color getColor(ArrayNode arrayNode) {
        Vector3D colorVector = getVector(arrayNode);
        return Color.rgb((int)colorVector.getX(), (int)colorVector.getY(), (int)colorVector.getZ());
    }

}

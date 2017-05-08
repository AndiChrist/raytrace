package de.avwc.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import de.avwc.geometry.Cube;
import de.avwc.geometry.Sphere;
import de.avwc.light.PointLight;
import de.avwc.main.Scene;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by andichrist on 07.05.17.
 */
public class SceneDeserializer extends JsonDeserializer<Scene> {

    @Override
    public Scene deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);
        Scene scene = Scene.getInstance();

        JsonNode objects = node.get("objects");
        JsonNode lights = node.get("lights");

        // Retrieve data from JsonObject and create Scene bean
        for(JsonNode object3D : objects) {
            for (JsonNode sphere : object3D.get("spheres")) {
                scene.getObjects().add(readSphere(sphere));
            }

            for (JsonNode cube : object3D.get("cubes")) {
                scene.getObjects().add(readCube(cube));
            }
        }

        for(JsonNode light : lights) {
            for (JsonNode pointlight : light.get("pointlights")) {
                scene.getLights().add(readPointLight(pointlight));
            }
        }

        return scene;
    }


    private static PointLight readPointLight(JsonNode pointLight) {
        JsonNode positionNode = pointLight.get("position");
        List<Double> positionVector = getVector(positionNode);
        Vector3D position = new Vector3D(positionVector.get(0), positionVector.get(1), positionVector.get(2));

        JsonNode intensityNode = pointLight.get("intensity");
        List<Double> intensityVector = getVector(intensityNode);
        Vector3D intensity = new Vector3D(intensityVector.get(0), intensityVector.get(1), intensityVector.get(2));

        return new PointLight(position, intensity);
    }

    private static Cube readCube(JsonNode cube) {
        JsonNode minNode = cube.get("min");
        List<Double> minVector = getVector(minNode);
        Vector3D min = new Vector3D(minVector.get(0), minVector.get(1), minVector.get(2));

        JsonNode maxNode = cube.get("max");
        List<Double> maxVector = getVector(maxNode);
        Vector3D max = new Vector3D(maxVector.get(0), maxVector.get(1), maxVector.get(2));

        JsonNode rotateNode = cube.get("rotate");
        List<Double> rotateVector = getVector(rotateNode);
        Vector3D rotate = new Vector3D(rotateVector.get(0), rotateVector.get(1), rotateVector.get(2));

        String color = cube.get("color").textValue();

        return new Cube(min, max, rotate, color);
    }

    private static Sphere readSphere(JsonNode sphere) {
        JsonNode positionNode = sphere.get("position");
        Integer radius = sphere.get("radius").asInt();
        String pigment = sphere.get("color").asText();

        List<Double> vector = getVector(positionNode);

        Vector3D center = new Vector3D(vector.get(0), vector.get(1), vector.get(2));

        return new Sphere(center, radius, pigment);
    }

    private static List<Double> getVector(JsonNode arrayNode) {
        List<Double> result = new ArrayList<>();
        if (arrayNode.isArray()) {
            for (final JsonNode n : arrayNode) {
                result.add(n.asDouble());
            }
        }

        return result;
    }

}

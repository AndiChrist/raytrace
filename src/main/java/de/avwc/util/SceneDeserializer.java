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
        Vector3D position = getVector(positionNode);

        JsonNode intensityNode = pointLight.get("intensity");
        Vector3D intensity = getVector(intensityNode);

        return new PointLight(position, intensity);
    }

    private static Cube readCube(JsonNode cube) {
        JsonNode minNode = cube.get("min");
        Vector3D min = getVector(minNode);

        JsonNode maxNode = cube.get("max");
        Vector3D max = getVector(maxNode);

        JsonNode rotateNode = cube.get("rotate");
        Vector3D rotate = getVector(rotateNode);

        String color = cube.get("color").textValue();

        return new Cube(min, max, rotate, color);
    }

    private static Sphere readSphere(JsonNode sphere) {
        JsonNode positionNode = sphere.get("position");
        Integer radius = sphere.get("radius").asInt();
        String pigment = sphere.get("color").asText();

        Vector3D center = getVector(positionNode);
        
        return new Sphere(center, radius, pigment);
    }

    private static Vector3D getVector(JsonNode arrayNode) {
        List<Double> result = new ArrayList<>();
        if (arrayNode.isArray()) {
            for (final JsonNode n : arrayNode) {
                result.add(n.asDouble());
            }
        }

        return new Vector3D(result.get(0), result.get(1), result.get(2));
    }

}

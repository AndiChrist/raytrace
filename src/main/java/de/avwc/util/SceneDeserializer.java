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
        Double posx = positionNode.get("x").asDouble();
        Double posy = positionNode.get("y").asDouble();
        Double posz = positionNode.get("z").asDouble();

        Vector3D position = new Vector3D(posx, posy, posz);

        JsonNode intensityNode = pointLight.get("intensity");
        Double intx = intensityNode.get("x").asDouble();
        Double inty = intensityNode.get("y").asDouble();
        Double intz = intensityNode.get("z").asDouble();

        Vector3D intensity = new Vector3D(intx, inty, intz);

        return new PointLight(position, intensity);
    }

    private static Cube readCube(JsonNode cube) {
        JsonNode minNode = cube.get("min");
        Double minx = minNode.get("x").asDouble();
        Double miny = minNode.get("y").asDouble();
        Double minz = minNode.get("z").asDouble();

        Vector3D min = new Vector3D(minx, miny, minz);

        JsonNode maxNode = cube.get("max");
        Double maxx = maxNode.get("x").asDouble();
        Double maxy = maxNode.get("y").asDouble();
        Double maxz = maxNode.get("z").asDouble();

        Vector3D max = new Vector3D(maxx, maxy, maxz);

        JsonNode rotateNode = cube.get("rotate");
        Double rotx = rotateNode.get("x").asDouble();
        Double roty = rotateNode.get("y").asDouble();
        Double rotz = rotateNode.get("z").asDouble();

        Vector3D rotate = new Vector3D(rotx, roty, rotz);

        String color = cube.get("color").textValue();

        return new Cube(min, max, rotate, color);
    }

    private static Sphere readSphere(JsonNode sphere) {
        JsonNode positionNode = sphere.get("position");
        Integer radius = sphere.get("radius").asInt();
        String pigment = sphere.get("color").asText();

        Double x = positionNode.get("x").asDouble();
        Double y = positionNode.get("y").asDouble();
        Double z = positionNode.get("z").asDouble();

        Vector3D center = new Vector3D(x, y, z);

        return new Sphere(center, radius, pigment);
    }

}

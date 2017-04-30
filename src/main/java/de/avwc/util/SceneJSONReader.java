package de.avwc.util;

import de.avwc.geometry.Cube;
import de.avwc.geometry.Sphere;
import de.avwc.light.PointLight;
import de.avwc.main.Scene;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import javax.json.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by andichrist on 30.04.17.
 */
public class SceneJSONReader {

    public static final String JSON_FILE = "scene.json";

    public SceneJSONReader(Scene scene) throws IOException {
        InputStream fis = new FileInputStream(JSON_FILE);

        //create JsonReader object
        JsonReader jsonReader = Json.createReader(fis);

        /**
         * We can create JsonReader from Factory also
         JsonReaderFactory factory = Json.createReaderFactory(null);
         jsonReader = factory.createReader(fis);
         */

        //get JsonObject from JsonReader
        JsonObject jsonObject = jsonReader.readObject();

        //we can close IO resource and JsonReader now
        jsonReader.close();
        fis.close();

        // Retrieve data from JsonObject and create Scene bean
        JsonArray objectsArray = jsonObject.getJsonArray("objects");
        for(JsonValue objectValue : objectsArray) {
            JsonObject object3D = (JsonObject) objectValue;

            JsonValue sphereTest = object3D.get("sphere");
            if (sphereTest != null) {
                scene.getObjects().add(readSphere((JsonObject) sphereTest));
            }

            JsonValue cubeTest = object3D.get("cube");
            if (cubeTest != null) {
                scene.getObjects().add(readCube((JsonObject) cubeTest));
            }
        }

        JsonArray lightsArray = jsonObject.getJsonArray("lights");
        for(JsonValue lightValue : lightsArray) {
            JsonObject light = (JsonObject) lightValue;

            JsonValue pointLightTest = light.get("pointlight");
            if (pointLightTest != null) {
                scene.getLights().add(readPointLight((JsonObject) pointLightTest));
            }
        }
    }

    private PointLight readPointLight(JsonObject pointLightTest) {
        JsonObject pointLight = pointLightTest;

        JsonValue pos = pointLight.get("position");
        JsonObject coords = (JsonObject) pos;
        Integer posx = coords.getInt("x");
        Integer posy = coords.getInt("y");
        Integer posz = coords.getInt("z");

        Vector3D position = new Vector3D(posx, posy, posz);

        JsonValue intense = pointLight.get("intensity");
        JsonObject coordsInt = (JsonObject) intense;
        Integer intx = coordsInt.getInt("x");
        Integer inty = coordsInt.getInt("y");
        Integer intz = coordsInt.getInt("z");

        Vector3D intensity = new Vector3D(intx, inty, intz);

        return new PointLight(position, intensity);
    }

    private Cube readCube(JsonObject cubeTest) {
        JsonObject cube = cubeTest;

        JsonValue minpos = cube.get("min");
        JsonObject coordsMin = (JsonObject) minpos;
        Integer minx = coordsMin.getInt("x");
        Integer miny = coordsMin.getInt("y");
        Integer minz = coordsMin.getInt("z");

        Vector3D min = new Vector3D(minx, miny, minz);

        JsonValue maxpos = cube.get("min");
        JsonObject coordsMax = (JsonObject) maxpos;
        Integer maxx = coordsMax.getInt("x");
        Integer maxy = coordsMax.getInt("y");
        Integer maxz = coordsMax.getInt("z");

        Vector3D max = new Vector3D(maxx, maxy, maxz);

        JsonValue rotpos = cube.get("rotate");
        JsonObject coordsRot = (JsonObject) rotpos;
        Integer rotx = coordsRot.getInt("x");
        Integer roty = coordsRot.getInt("y");
        Integer rotz = coordsRot.getInt("z");

        Vector3D rotate = new Vector3D(rotx, roty, rotz);

        String color = cube.getString("color");

        return new Cube(min, max, rotate, color);
    }

    private Sphere readSphere(JsonObject sphereTest) {
        JsonObject sphere = sphereTest;

        JsonValue pos = sphere.get("position");
        JsonObject coords = (JsonObject) pos;
        Integer x = coords.getInt("x");
        Integer y = coords.getInt("y");
        Integer z = coords.getInt("z");

        Vector3D position = new Vector3D(x, y, z);
        Integer radius = sphere.getInt("radius");
        String color = sphere.getString("color");

        return new Sphere(position, radius, color);
    }


}

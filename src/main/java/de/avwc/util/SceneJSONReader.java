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

    private static final String JSON_FILE = "scene.json";

    public SceneJSONReader(Scene scene) throws IOException {
        InputStream fis = new FileInputStream(JSON_FILE);

        //create JsonReader object
        JsonReader jsonReader = Json.createReader(fis);

        /*
         * We can create JsonReader from Factory also:
         * JsonReaderFactory factory = Json.createReaderFactory(null);
         * jsonReader = factory.createReader(fis);
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

    private PointLight readPointLight(JsonObject pointLight) {
        JsonValue pos = pointLight.get("position");
        JsonObject coords = (JsonObject) pos;
        Double posx = coords.getJsonNumber("x").doubleValue();
        Double posy = coords.getJsonNumber("y").doubleValue();
        Double posz = coords.getJsonNumber("z").doubleValue();

        Vector3D position = new Vector3D(posx, posy, posz);

        JsonValue intense = pointLight.get("intensity");
        JsonObject coordsInt = (JsonObject) intense;
        Double intx = coordsInt.getJsonNumber("x").doubleValue();
        Double inty = coordsInt.getJsonNumber("y").doubleValue();
        Double intz = coordsInt.getJsonNumber("z").doubleValue();

        Vector3D intensity = new Vector3D(intx, inty, intz);

        return new PointLight(position, intensity);
    }

    private Cube readCube(JsonObject cube) {
        JsonValue minpos = cube.get("min");
        JsonObject coordsMin = (JsonObject) minpos;
        Double minx = coordsMin.getJsonNumber("x").doubleValue();
        Double miny = coordsMin.getJsonNumber("y").doubleValue();
        Double minz = coordsMin.getJsonNumber("z").doubleValue();

        Vector3D min = new Vector3D(minx, miny, minz);

        JsonValue maxpos = cube.get("max");
        JsonObject coordsMax = (JsonObject) maxpos;
        Double maxx = coordsMax.getJsonNumber("x").doubleValue();
        Double maxy = coordsMax.getJsonNumber("y").doubleValue();
        Double maxz = coordsMax.getJsonNumber("z").doubleValue();

        Vector3D max = new Vector3D(maxx, maxy, maxz);

        JsonValue rotpos = cube.get("rotate");
        JsonObject coordsRot = (JsonObject) rotpos;
        Double rotx = coordsRot.getJsonNumber("x").doubleValue();
        Double roty = coordsRot.getJsonNumber("y").doubleValue();
        Double rotz = coordsRot.getJsonNumber("z").doubleValue();

        Vector3D rotate = new Vector3D(rotx, roty, rotz);

        String color = cube.getString("color");

        return new Cube(min, max, rotate, color);
    }

    private Sphere readSphere(JsonObject sphere) {
        JsonValue pos = sphere.get("position");
        JsonObject coords = (JsonObject) pos;
        Double x = coords.getJsonNumber("x").doubleValue();
        Double y = coords.getJsonNumber("y").doubleValue();
        Double z = coords.getJsonNumber("z").doubleValue();

        Vector3D position = new Vector3D(x, y, z);
        Integer radius = sphere.getInt("radius");
        String color = sphere.getString("color");

        return new Sphere(position, radius, color);
    }


}

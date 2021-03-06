package de.avwc.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.avwc.main.RayScene;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by andichrist on 30.04.17.
 */
public class SceneJSONReader {

    private static final String JSON_FILE = "scene.json";

    public static RayScene readSceneJSON() throws IOException {
        InputStream fis = new FileInputStream(JSON_FILE);

        //create JsonReader object
        RayScene scene = new ObjectMapper().readValue(fis, RayScene.class);

        // close file after reading
        fis.close();

        return scene;
    }

}

package de.avwc.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.avwc.main.Scene;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by andichrist on 30.04.17.
 */
public class SceneJSONReader {

    private static final String JSON_FILE = "scene.json";

    public static void readSceneJSON() throws IOException {
        InputStream fis = new FileInputStream(JSON_FILE);

        //create JsonReader object
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.readValue(fis, Scene.class);

        // close file after reading
        fis.close();
    }

}

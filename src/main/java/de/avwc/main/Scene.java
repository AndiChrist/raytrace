package de.avwc.main;

import com.oracle.javafx.jmx.json.JSONReader;
import de.avwc.geometry.Cube;
import de.avwc.geometry.Sphere;
import de.avwc.light.Light;
import de.avwc.geometry.Object3D;
import de.avwc.light.PointLight;
import de.avwc.util.SceneJSONReader;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Created by andichrist on 23.04.17.
 */
public class Scene {
    private static Scene scene;

    private List<Object3D> objects = new ArrayList<>();
    private List<Light> lights = new ArrayList<>();

    public List<Object3D> getObjects() {
        return objects;
    }

    public void setObjects(List<Object3D> objects) {
        this.objects = objects;
    }

    public List<Light> getLights() {
        return lights;
    }

    public void setLights(List<Light> lights) {
        this.lights = lights;
    }

    public Scene() {
        try {
            new SceneJSONReader(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

//        objects.add(new Sphere(new Vector3D(0,0,0), 5, Color.GREEN));
//        objects.add(new Sphere(new Vector3D(15,0, 0), 3, Color.RED));
//        objects.add(new Sphere(new Vector3D(10,0, 0), 20, Color.BLUE));
//        objects.add(new Cube(new Vector3D(3.5,3.5, 3.5), new Vector3D(15,15, 15), Vector3D.ZERO, Color.YELLOW));

//        Object3D s = new Sphere(new Vector3D(0.0, 0.0, 0.0), 1.0);
//        getObjects().add(s);

//        getLights().add(new PointLight(new Vector3D(0, 10, 10), new Vector3D(10, 10, 10)));
//        getLights().add(new PointLight(new Vector3D(-10, -10, 0), new Vector3D(10, 10, 10)));
//        getLights().add(new PointLight(new Vector3D(0, 10, 0), new Vector3D(10, 10, 10)));
//        getLights().add(new PointLight(new Vector3D(10, 10, 0), new Vector3D(10, 10, 10)));

    }

    public static Scene getScene() {
        if (scene == null) {
            scene = new Scene();
        }
        return scene;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("***** Scene Details *****\n");

        sb.append("\n*****************************");

        return sb.toString();

    }
}

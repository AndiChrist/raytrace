package de.avwc.main;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.avwc.gfx.Camera;
import de.avwc.gfx.Renderable;
import de.avwc.gfx.light.Light;
import de.avwc.util.SceneDeserializer;
import de.avwc.util.SceneJSONReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by andichrist on 07.05.17.
 */
@JsonDeserialize(using = SceneDeserializer.class)
public class RayScene {

    private static List<Renderable> objects = new ArrayList<>();
    private static List<Light> lights = new ArrayList<>();
    private static Camera camera;
    private static int width;
    private static int height;

    static {
        try {
            SceneJSONReader.readSceneJSON();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static RayScene rayScene;

    private RayScene() {
    }

    public static RayScene getInstance() {
        if (rayScene == null) {
            rayScene = new RayScene();
        }

        return rayScene;
    }

    public static List<Renderable> getObjects() {
        return objects;
    }

    public static void addObject(Renderable renderable) {
        objects.add(renderable);
    }

    public static List<Light> getLights() {
        return lights;
    }

    public static void addLight(Light light) {
        lights.add(light);
    }

    public static void setCamera(Camera camera) {
        RayScene.camera = camera;
        RayScene.camera.setDimension(width, height);
    }

    public static Camera getCamera() {
        return camera;
    }

    public static int getWidth() {
        return width;
    }

    public static void setWidth(Integer width) {
        RayScene.width = width;
    }

    public static int getHeight() {
        return height;
    }

    public static void setHeight(Integer height) {
        RayScene.height = height;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Renderable o : objects) {
            sb.append(o.debug());
        }
        for(Light l : lights) {
            sb.append(l.debug());
        }

        sb.append(camera.debug());

        return sb.toString();
    }

}

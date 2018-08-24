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

    private List<Renderable> objects = new ArrayList<>();
    private List<Light> lights = new ArrayList<>();
    private Camera camera;
    private int width;
    private int height;

    static {
        try {
            SceneJSONReader.readSceneJSON();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static volatile RayScene instance = null;

    private RayScene() {
    }

    public static RayScene getInstance() {

        if (instance == null) {
            synchronized(RayScene.class) {
                if (instance == null) {
                    instance = new RayScene();
                }
            }
        }

        return instance;
    }

    public List<Renderable> getObjects() {
        return objects;
    }

    public void addObject(Renderable renderable) {
        objects.add(renderable);
    }

    public List<Light> getLights() {
        return lights;
    }

    public void addLight(Light light) {
        lights.add(light);
    }

    public void setCamera(Camera camera) {
        instance.camera = camera;
        instance.camera.setDimension(width, height);
    }

    public Camera getCamera() {
        return camera;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        instance.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        instance.height = height;
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

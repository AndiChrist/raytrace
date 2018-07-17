package de.avwc.main;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.avwc.gfx.Camera;
import de.avwc.gfx.Renderable;
import de.avwc.gfx.light.Light;
import de.avwc.util.SceneDeserializer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andichrist on 07.05.17.
 */
@JsonDeserialize(using = SceneDeserializer.class)
public class Scene {

    private List<Renderable> objects = new ArrayList<>();
    private List<Light> lights = new ArrayList<>();
    private Camera camera;
    private int width;
    private int height;

    public List<Renderable> getObjects() {
        return objects;
    }

    public void setObjects(List<Renderable> objects) {
        this.objects = objects;
    }

    public List<Light> getLights() {
        return lights;
    }

    public void setLights(List<Light> lights) {
        this.lights = lights;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
        this.camera.setDimension(width, height);
    }

    public Camera getCamera() {
        return camera;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
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

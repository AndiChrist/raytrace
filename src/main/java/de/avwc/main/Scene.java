package de.avwc.main;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.avwc.geometry.Object3D;
import de.avwc.light.Light;
import de.avwc.util.SceneDeserializer;
import de.avwc.util.SceneJSONReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by andichrist on 07.05.17.
 */
@JsonDeserialize(using = SceneDeserializer.class)
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

    private Scene() {}

    public static Scene getInstance() {
        if (scene == null) {
            scene = new Scene();
            try {
                new SceneJSONReader(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return scene;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Scene Details:\n");
        for(Object3D o : Scene.getInstance().getObjects()) {
            sb.append(o.getClass().getName()).append(": ");
            sb.append(System.lineSeparator());
            sb.append(o);
            sb.append(System.lineSeparator());

        }
        for(Light l : Scene.getInstance().getLights()) {
            sb.append(l.getClass().getName()).append(": ");
            sb.append(System.lineSeparator());
            sb.append(l);
            sb.append(System.lineSeparator());
        }

        return sb.toString();

    }
}

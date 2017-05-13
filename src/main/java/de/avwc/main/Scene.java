package de.avwc.main;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.avwc.geometry.Renderable;
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

    private List<Renderable> objects = new ArrayList<>();
    private List<Light> lights = new ArrayList<>();

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

    private Scene() {}

    public static Scene getInstance() {
        if (scene == null) {
            scene = new Scene();
            try {
                SceneJSONReader.readSceneJSON(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return scene;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Renderable o : Scene.getInstance().getObjects()) {
            sb.append(o.debug());
        }
        for(Light l : Scene.getInstance().getLights()) {
            sb.append(l.debug());
        }

        return sb.toString();

    }
}

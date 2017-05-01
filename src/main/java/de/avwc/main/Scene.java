package de.avwc.main;

import de.avwc.geometry.Object3D;
import de.avwc.light.Light;
import de.avwc.util.SceneJSONReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    private Scene() {
        try {
            new SceneJSONReader(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
/*
        objects.add(new Sphere(new Vector3D(0,0,0), 5, Color.GREEN));
        objects.add(new Sphere(new Vector3D(15,0, 0), 3, Color.RED));
        objects.add(new Sphere(new Vector3D(10,0, 0), 20, Color.BLUE));
        objects.add(new Cube(new Vector3D(3.5,3.5, 3.5), new Vector3D(15,15, 15), Vector3D.ZERO, Color.YELLOW));

//        Object3D s = new Sphere(new Vector3D(0.0, 0.0, 0.0), 1.0);
//        getObjects().add(s);

        getLights().add(new PointLight(new Vector3D(0, 10, 10), new Vector3D(10, 10, 10)));
        getLights().add(new PointLight(new Vector3D(-10, -10, 0), new Vector3D(10, 10, 10)));
        getLights().add(new PointLight(new Vector3D(0, 10, 0), new Vector3D(10, 10, 10)));
        getLights().add(new PointLight(new Vector3D(10, 10, 0), new Vector3D(10, 10, 10)));
*/
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
        sb.append("Scene Details:\n");
        for(Object3D o : this.getObjects()) {
            sb.append(o.getClass().getName()).append(": ");
            sb.append(System.lineSeparator());
            sb.append(o);
            sb.append(System.lineSeparator());

        }
        for(Light l : this.getLights()) {
            sb.append(l.getClass().getName()).append(": ");
            sb.append(System.lineSeparator());
            sb.append(l);
            sb.append(System.lineSeparator());
        }

        return sb.toString();

    }
}

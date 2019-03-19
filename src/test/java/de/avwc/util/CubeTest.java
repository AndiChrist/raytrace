package de.avwc.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.avwc.gfx.Cube;
import javafx.scene.paint.Color;
import org.hipparchus.geometry.euclidean.threed.Vector3D;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

public class CubeTest {

    @Test
    public void deserializeUsingJsonSetter() throws IOException {
        // arrange
        Vector3D min = new Vector3D(3.5, 3.5, 3.5);
        Vector3D max = new Vector3D(15, 15, 15);
        Vector3D rotate = Vector3D.ZERO;
        String name = "yellow cube";
        Color color = new Color(1.0, 1.0, 0.0, 1.0);

        // act
        Cube cube = new Cube(min, max, rotate, name, color);

        // assert
        assertThat(cube.getName(), containsString("yellow cube"));
    }

}

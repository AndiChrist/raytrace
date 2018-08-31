package de.avwc.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.avwc.gfx.Sphere;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

public class JsonSphereTest {

    @Test
    public void deserializeUsingJsonSetter() throws IOException {
        // arrange
        String json = "{\n" +
                "          \"name\": \"red sphere\",\n" +
                "          \"center\": [0, 0, 0],\n" +
                "          \"radius\": 5,\n" +
                "          \"color\": [255, 0, 0]\n" +
                "        }";
        System.out.println("json = " + json);
        // act
        Sphere sphere = new ObjectMapper().readValue(json, Sphere.class);

        // assert
        assertThat(sphere.getName(), containsString("red sphere"));
    }

}

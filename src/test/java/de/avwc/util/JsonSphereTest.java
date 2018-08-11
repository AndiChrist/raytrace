package de.avwc.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.avwc.gfx.Cube;
import de.avwc.gfx.Sphere;
import org.junit.Ignore;
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
                "          \"position\": [0, 0, 0],\n" +
                "          \"radius\": 5,\n" +
                "          \"color\": [255, 0, 0]\n" +
                "        }";

        // act
        Sphere sphere = new ObjectMapper().readerFor(Sphere.class).readValue(json);

        // assert
        assertThat(sphere.getName(), containsString("red sphere"));
    }

}

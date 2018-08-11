package de.avwc.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.avwc.gfx.Cube;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

public class JsonCubeTest {

    @Test
    public void deserializeUsingJsonSetter2() throws IOException {
        // arrange
        String json = "{\n" +
                "          \"name\": \"yellow cube\",\n" +
                "          \"min\": [3.5, 3.5, 3.5],\n" +
                "          \"max\": [15, 15, 15],\n" +
                "          \"rotate\": [0, 0, 0],\n" +
                "          \"color\": [0, 255, 0]\n" +
                "        }";

        // act
        Cube cube = new ObjectMapper().readerFor(Cube.class).readValue(json);

        // assert
        assertThat(cube.getName(), containsString("yellow cube"));
    }

}

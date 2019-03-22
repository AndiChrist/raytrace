package de.avwc.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.avwc.gfx.Cube;
import de.avwc.gfx.CubeBuilder;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class JsonCubeTest {

    @Test
    public void deserializeUsingJsonSetter() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        // arrange
        String json = "{\n" +
                "          \"name\": \"yellow cube\",\n" +
                "          \"min\": [3.5, 3.5, 3.5],\n" +
                "          \"max\": [15.0, 15.0, 15.0],\n" +
                "          \"rotate\": [0.0, 0.0, 0.0],\n" +
                "          \"color\": [255, 255, 0, 1]\n" +
                "        }";

        JsonNode jsonNode = mapper.readTree(json);

        // act
        List<Double> min = mapper.convertValue(jsonNode.get("min"), ArrayList.class);
        List<Double> max = mapper.convertValue(jsonNode.get("max"), ArrayList.class);
        List<Double> rotate = mapper.convertValue(jsonNode.get("rotate"), ArrayList.class);
        List<Integer> color = mapper.convertValue(jsonNode.get("color"), ArrayList.class);
        String name = jsonNode.get("name").textValue();

        Cube cube = new CubeBuilder().setMin(min).setMax(max).setRotate(rotate).setColor(color).setName(name).createCube();

        // assert
        assertEquals(cube.getName(), "yellow cube");
    }

}

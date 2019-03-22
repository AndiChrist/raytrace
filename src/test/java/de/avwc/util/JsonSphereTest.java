package de.avwc.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.avwc.gfx.Sphere;
import de.avwc.gfx.SphereBuilder;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class JsonSphereTest {

    @Test
    public void deserializeUsingJsonSetter() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        // arrange
        String json = "{\n" +
                "          \"name\": \"red sphere\",\n" +
                "          \"center\": [10.0, 0.0, 0.0],\n" +
                "          \"radius\": 3,\n" +
                "          \"color\": [255, 0, 0, 1]\n" +
                "        }";

        JsonNode jsonNode = mapper.readTree(json);

        List<Double> center = mapper.convertValue(jsonNode.get("center"), ArrayList.class);
        List<Integer> color = mapper.convertValue(jsonNode.get("color"), ArrayList.class);
        String name = jsonNode.get("name").textValue();
        Integer radius = jsonNode.get("radius").asInt();

        // act
        Sphere sphere = new SphereBuilder().setCenter(center).setRadius(radius).setName(name).setColor(color).createSphere();

        // assert
        assertEquals(sphere.getName(), "red sphere");
    }

}

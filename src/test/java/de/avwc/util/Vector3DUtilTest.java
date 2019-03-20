package de.avwc.util;

import javafx.scene.paint.Color;
import org.hipparchus.geometry.euclidean.threed.Vector3D;
import org.junit.Test;

import static org.junit.Assert.*;

public class Vector3DUtilTest {

    @Test
    public void add() {
        // arrange
        Vector3D vector1 = new Vector3D(1.0, 2.0, 3.0);
        Vector3D vector2 = new Vector3D(4.0, 5.0, 6.0);
        Vector3D vector3 = new Vector3D(7.0, 8.0, 9.0);
        Vector3D vector4 = new Vector3D(10.0, 11.0, 12.0);

        Vector3D expected = new Vector3D(22.0, 26.0, 30.0);

        // act
        Vector3D actual = Vector3DUtil.add(vector1, vector2, vector3, vector4);

        // assert
        assertEquals(expected, actual);
    }

    @Test
    public void move() {
        // arrange
        Vector3D vector = new Vector3D(1.0, 2.0, 3.0);
        Vector3D positionToLight = new Vector3D(1.0, 2.0, 3.0);
        double ε = 2.5;

        Vector3D expected = new Vector3D(3.5, 7.0, 10.5);

        // act: v = v + p⊗ε
        Vector3D actual = Vector3DUtil.move(vector, positionToLight, ε);

        // assert
        assertEquals(expected, actual);
    }

    @Test
    public void multiply() {
        // arrange
        Color color = Color.YELLOW;
        Vector3D vector = new Vector3D(1.0, 2.0, 3.0);

        Vector3D expected = new Vector3D(1.0, 2.0, 0.0);

        // act
        Vector3D actual = Vector3DUtil.multiply(color, vector);

        // assert
        assertEquals(expected, actual);
    }

    @Test
    public void divide() {
        // arrange
        double dividend = 6.0;
        Vector3D vector = new Vector3D(1.0, 2.0, 3.0);

        Vector3D expected = new Vector3D(6.0, 3.0, 2.0);

        // act
        Vector3D actual = Vector3DUtil.divide(dividend, vector);

        // assert
        assertEquals(expected, actual);
    }

    @Test
    public void divideDecimal() {
        // arrange
        double dividend = 5.0;
        Vector3D vector = new Vector3D(1.0, 2.0, 3.0);

        Vector3D expected = new Vector3D(5.0, 2.5, 5.0/3.0);

        // act
        Vector3D actual = Vector3DUtil.divide(dividend, vector);

        // assert
        assertEquals(expected, actual);
    }


    @Test
    public void inverse() {
        // arrange
        Vector3D vector = new Vector3D(1.0, 2.0, 3.0);

        Vector3D expected = new Vector3D(1.0, 0.5, 1.0/3.0);

        // act
        Vector3D actual = Vector3DUtil.inverse(vector);

        // assert
        assertEquals(expected, actual);
    }
}
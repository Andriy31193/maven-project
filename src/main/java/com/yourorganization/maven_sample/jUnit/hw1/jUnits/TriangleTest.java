package com.yourorganization.maven_sample.jUnit.hw1.jUnits;

import com.yourorganization.maven_sample.jUnit.hw1.models.Triangle;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TriangleTest {

    @Test
    public void testTriangleArea() {
        Triangle triangle = new Triangle();
        double area = triangle.calculateArea(5, 4);
        assertEquals(10.0, area, 0.01); // The expected area is 10.0
    }

    @Test
    public void testTriangleAreaWithZeroBase() {
        Triangle triangle = new Triangle();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> triangle.calculateArea(0, 4));
        assertEquals("Base and height must be positive numbers.", exception.getMessage());
    }

    @Test
    public void testTriangleAreaWithNegativeHeight() {
        Triangle triangle = new Triangle();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> triangle.calculateArea(5, -4));
        assertEquals("Base and height must be positive numbers.", exception.getMessage());
    }
}

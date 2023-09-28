package com.yourorganization.maven_sample.jUnit.hw1.jUnits;

import com.yourorganization.maven_sample.jUnit.hw1.models.Rectangle;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RectangleTest {

    @Test
    public void testRectangleArea() {
        Rectangle rectangle = new Rectangle();
        double area = rectangle.calculateArea(5, 4);
        assertEquals(20.0, area, 0.01); // The expected area is 20.0
    }

    @Test
    public void testRectangleAreaWithZeroLength() {
        Rectangle rectangle = new Rectangle();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> rectangle.calculateArea(0, 4));
        assertEquals("Length and width must be positive numbers.", exception.getMessage());
    }

    @Test
    public void testRectangleAreaWithNegativeWidth() {
        Rectangle rectangle = new Rectangle();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> rectangle.calculateArea(5, -4));
        assertEquals("Length and width must be positive numbers.", exception.getMessage());
    }
}


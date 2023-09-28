package com.yourorganization.maven_sample.jUnit.hw1.jUnits;

import com.yourorganization.maven_sample.jUnit.hw1.models.Rhombus;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RhombusTest {

    @Test
    public void testRhombusArea() {
        Rhombus rhombus = new Rhombus();
        double area = rhombus.calculateArea(6, 8);
        assertEquals(24.0, area, 0.01); // The expected area is 24.0
    }

    @Test
    public void testRhombusAreaWithZeroDiagonal() {
        Rhombus rhombus = new Rhombus();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> rhombus.calculateArea(0, 8));
        assertEquals("Diagonals must be positive numbers.", exception.getMessage());
    }

    @Test
    public void testRhombusAreaWithNegativeDiagonal() {
        Rhombus rhombus = new Rhombus();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> rhombus.calculateArea(-6, 8));
        assertEquals("Diagonals must be positive numbers.", exception.getMessage());
    }
}


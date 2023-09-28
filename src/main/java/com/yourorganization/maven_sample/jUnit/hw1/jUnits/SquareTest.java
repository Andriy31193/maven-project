package com.yourorganization.maven_sample.jUnit.hw1.jUnits;

import com.yourorganization.maven_sample.jUnit.hw1.models.Square;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SquareTest {

    @Test
    public void testSquareArea() {
        Square square = new Square();
        double area = square.calculateArea(5);
        assertEquals(25.0, area, 0.01); // The expected area is 25.0
    }

    @Test
    public void testSquareAreaWithZeroSideLength() {
        Square square = new Square();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> square.calculateArea(0));
        assertEquals("Side length must be a positive number.", exception.getMessage());
    }

    @Test
    public void testSquareAreaWithNegativeSideLength() {
        Square square = new Square();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> square.calculateArea(-5));
        assertEquals("Side length must be a positive number.", exception.getMessage());
    }
}


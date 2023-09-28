package com.yourorganization.maven_sample.jUnit.hw1.task4.jUnitsTests;

import com.yourorganization.maven_sample.jUnit.hw1.task4.models.LengthConverter;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LengthConverterTest {

    @Test
    public void testMillimetersToCentimeters() {
        LengthConverter converter = new LengthConverter();
        double centimeters = converter.millimetersToCentimeters(100.0);
        assertEquals(10.0, centimeters, 0.01);
    }

    @Test
    public void testCentimetersToMillimeters() {
        LengthConverter converter = new LengthConverter();
        double millimeters = converter.centimetersToMillimeters(10.0);
        assertEquals(100.0, millimeters, 0.01);
    }

    @Test
    public void testCentimetersToDecimeters() {
        LengthConverter converter = new LengthConverter();
        double decimeters = converter.centimetersToDecimeters(20.0);
        assertEquals(2.0, decimeters, 0.01);
    }

    @Test
    public void testDecimetersToCentimeters() {
        LengthConverter converter = new LengthConverter();
        double centimeters = converter.decimetersToCentimeters(5.0);
        assertEquals(50.0, centimeters, 0.01);
    }

    @Test
    public void testMetersToCentimeters() {
        LengthConverter converter = new LengthConverter();
        double centimeters = converter.metersToCentimeters(2.0);
        assertEquals(200.0, centimeters, 0.01);
    }

    @Test
    public void testCentimetersToMeters() {
        LengthConverter converter = new LengthConverter();
        double meters = converter.centimetersToMeters(300.0);
        assertEquals(3.0, meters, 0.01);
    }

    @Test
    public void testMetersToKilometers() {
        LengthConverter converter = new LengthConverter();
        double kilometers = converter.metersToKilometers(5000.0);
        assertEquals(5.0, kilometers, 0.01);
    }

    @Test
    public void testKilometersToMeters() {
        LengthConverter converter = new LengthConverter();
        double meters = converter.kilometersToMeters(3.5);
        assertEquals(3500.0, meters, 0.01);
    }
}


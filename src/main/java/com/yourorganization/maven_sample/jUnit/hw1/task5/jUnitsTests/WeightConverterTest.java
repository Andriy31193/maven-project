package com.yourorganization.maven_sample.jUnit.hw1.task5.jUnitsTests;

import com.yourorganization.maven_sample.jUnit.hw1.task5.models.WeightConverter;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class WeightConverterTest {

    @Test
    public void testMilligramsToGrams() {
        WeightConverter converter = new WeightConverter();
        double grams = converter.milligramsToGrams(1000.0);
        assertEquals(1.0, grams, 0.01);
    }

    @Test
    public void testGramsToMilligrams() {
        WeightConverter converter = new WeightConverter();
        double milligrams = converter.gramsToMilligrams(1.0);
        assertEquals(1000.0, milligrams, 0.01);
    }

    @Test
    public void testGramsToKilograms() {
        WeightConverter converter = new WeightConverter();
        double kilograms = converter.gramsToKilograms(1000.0);
        assertEquals(1.0, kilograms, 0.01);
    }

    @Test
    public void testKilogramsToGrams() {
        WeightConverter converter = new WeightConverter();
        double grams = converter.kilogramsToGrams(1.0);
        assertEquals(1000.0, grams, 0.01);
    }

    @Test
    public void testKilogramsToCentners() {
        WeightConverter converter = new WeightConverter();
        double centners = converter.kilogramsToCentners(100.0);
        assertEquals(1.0, centners, 0.01);
    }

    @Test
    public void testCentnersToKilograms() {
        WeightConverter converter = new WeightConverter();
        double kilograms = converter.centnersToKilograms(1.0);
        assertEquals(100.0, kilograms, 0.01);
    }

    @Test
    public void testKilogramsToTons() {
        WeightConverter converter = new WeightConverter();
        double tons = converter.kilogramsToTons(1000.0);
        assertEquals(1.0, tons, 0.01);
    }

    @Test
    public void testTonsToKilograms() {
        WeightConverter converter = new WeightConverter();
        double kilograms = converter.tonsToKilograms(1.0);
        assertEquals(1000.0, kilograms, 0.01);
    }
}


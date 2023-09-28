package com.yourorganization.maven_sample.jUnit.hw1.task2.jUnitTests;

import com.yourorganization.maven_sample.jUnit.hw1.task2.models.CurrencyConverter;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CurrencyConverterTest {

    @Test
    public void testDollarsToEuros() {
        CurrencyConverter converter = new CurrencyConverter();
        double euros = converter.dollarsToEuros(100.0);
        assertEquals(88.0, euros, 0.01);
    }

    @Test
    public void testEurosToDollars() {
        CurrencyConverter converter = new CurrencyConverter();
        double dollars = converter.eurosToDollars(88.0);
        assertEquals(100.0, dollars, 0.01);
    }

    @Test
    public void testDollarsToPounds() {
        CurrencyConverter converter = new CurrencyConverter();
        double pounds = converter.dollarsToPounds(100.0);
        assertEquals(75.0, pounds, 0.01);
    }

    @Test
    public void testPoundsToDollars() {
        CurrencyConverter converter = new CurrencyConverter();
        double dollars = converter.poundsToDollars(75.0);
        assertEquals(100.0, dollars, 0.01);
    }

    @Test
    public void testDollarsToYen() {
        CurrencyConverter converter = new CurrencyConverter();
        double yen = converter.dollarsToYen(100.0);
        assertEquals(11015.0, yen, 0.01);
    }

    @Test
    public void testYenToDollars() {
        CurrencyConverter converter = new CurrencyConverter();
        double dollars = converter.yenToDollars(11015.0);
        assertEquals(100.0, dollars, 0.01);
    }

    @Test
    public void testNegativeAmount() {
        CurrencyConverter converter = new CurrencyConverter();
        assertThrows(IllegalArgumentException.class, () -> converter.dollarsToEuros(-100.0));
    }
}


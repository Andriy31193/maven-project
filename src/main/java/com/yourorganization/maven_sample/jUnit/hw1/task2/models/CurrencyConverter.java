package com.yourorganization.maven_sample.jUnit.hw1.task2.models;

public class CurrencyConverter {

    private static final double DOLLAR_TO_EURO_RATE = 0.88;
    private static final double DOLLAR_TO_POUND_RATE = 0.75;
    private static final double DOLLAR_TO_YEN_RATE = 110.15;

    public double dollarsToEuros(double dollars) {
        if (dollars < 0) {
            throw new IllegalArgumentException("Amount cannot be negative.");
        }
        return dollars * DOLLAR_TO_EURO_RATE;
    }

    public double eurosToDollars(double euros) {
        if (euros < 0) {
            throw new IllegalArgumentException("Amount cannot be negative.");
        }
        return euros / DOLLAR_TO_EURO_RATE;
    }

    public double dollarsToPounds(double dollars) {
        if (dollars < 0) {
            throw new IllegalArgumentException("Amount cannot be negative.");
        }
        return dollars * DOLLAR_TO_POUND_RATE;
    }

    public double poundsToDollars(double pounds) {
        if (pounds < 0) {
            throw new IllegalArgumentException("Amount cannot be negative.");
        }
        return pounds / DOLLAR_TO_POUND_RATE;
    }

    public double dollarsToYen(double dollars) {
        if (dollars < 0) {
            throw new IllegalArgumentException("Amount cannot be negative.");
        }
        return dollars * DOLLAR_TO_YEN_RATE;
    }

    public double yenToDollars(double yen) {
        if (yen < 0) {
            throw new IllegalArgumentException("Amount cannot be negative.");
        }
        return yen / DOLLAR_TO_YEN_RATE;
    }
}

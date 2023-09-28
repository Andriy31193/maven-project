package com.yourorganization.maven_sample.jUnit.hw1.task5.models;

public class WeightConverter {

    public double milligramsToGrams(double milligrams) {
        return milligrams / 1000.0;
    }

    public double gramsToMilligrams(double grams) {
        return grams * 1000.0;
    }

    public double gramsToKilograms(double grams) {
        return grams / 1000.0;
    }

    public double kilogramsToGrams(double kilograms) {
        return kilograms * 1000.0;
    }

    public double kilogramsToCentners(double kilograms) {
        return kilograms / 100.0;
    }

    public double centnersToKilograms(double centners) {
        return centners * 100.0;
    }

    public double kilogramsToTons(double kilograms) {
        return kilograms / 1000.0;
    }

    public double tonsToKilograms(double tons) {
        return tons * 1000.0;
    }
}


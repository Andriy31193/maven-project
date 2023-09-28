package com.yourorganization.maven_sample.jUnit.hw1.task4.models;

public class LengthConverter {

    public double millimetersToCentimeters(double millimeters) {
        return millimeters / 10.0;
    }

    public double centimetersToMillimeters(double centimeters) {
        return centimeters * 10.0;
    }

    public double centimetersToDecimeters(double centimeters) {
        return centimeters / 10.0;
    }

    public double decimetersToCentimeters(double decimeters) {
        return decimeters * 10.0;
    }

    public double metersToCentimeters(double meters) {
        return meters * 100.0;
    }

    public double centimetersToMeters(double centimeters) {
        return centimeters / 100.0;
    }

    public double metersToKilometers(double meters) {
        return meters / 1000.0;
    }

    public double kilometersToMeters(double kilometers) {
        return kilometers * 1000.0;
    }
}


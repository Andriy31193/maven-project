package com.yourorganization.maven_sample.jUnit.hw1.task1.models;

public class Triangle {
    public double calculateArea(double base, double height) {
        if (base <= 0 || height <= 0) {
            throw new IllegalArgumentException("Base and height must be positive numbers.");
        }
        return 0.5 * base * height;
    }
}

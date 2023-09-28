package com.yourorganization.maven_sample.jUnit.hw1.models;

public class Rhombus {
    public double calculateArea(double diagonal1, double diagonal2) {
        if (diagonal1 <= 0 || diagonal2 <= 0) {
            throw new IllegalArgumentException("Diagonals must be positive numbers.");
        }
        return (diagonal1 * diagonal2) / 2;
    }
}

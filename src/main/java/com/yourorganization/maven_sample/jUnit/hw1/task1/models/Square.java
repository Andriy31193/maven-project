package com.yourorganization.maven_sample.jUnit.hw1.task1.models;

public class Square {
    public double calculateArea(double sideLength) {
        if (sideLength <= 0) {
            throw new IllegalArgumentException("Side length must be a positive number.");
        }
        return sideLength * sideLength;
    }
}

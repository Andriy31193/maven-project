package com.yourorganization.maven_sample.jUnit.hw1.task1.models;

public class Rectangle {
    public double calculateArea(double length, double width) {
        if (length <= 0 || width <= 0) {
            throw new IllegalArgumentException("Length and width must be positive numbers.");
        }
        return length * width;
    }
}

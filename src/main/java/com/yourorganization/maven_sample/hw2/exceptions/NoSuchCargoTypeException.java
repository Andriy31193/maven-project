package com.yourorganization.maven_sample.hw2.exceptions;

public class NoSuchCargoTypeException  extends Exception {
    public NoSuchCargoTypeException(String message) {
        super(message);
    }
}
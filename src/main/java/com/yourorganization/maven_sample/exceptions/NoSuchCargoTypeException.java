package com.yourorganization.maven_sample.exceptions;

public class NoSuchCargoTypeException  extends Exception {
    public NoSuchCargoTypeException(String message) {
        super(message);
    }
}
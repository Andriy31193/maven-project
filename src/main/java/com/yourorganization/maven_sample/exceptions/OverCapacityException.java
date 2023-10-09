package com.yourorganization.maven_sample.exceptions;

public class OverCapacityException extends Exception {
    public OverCapacityException(String message) {
        super(message);
    }
}

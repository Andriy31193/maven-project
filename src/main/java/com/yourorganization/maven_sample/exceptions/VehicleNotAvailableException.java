package com.yourorganization.maven_sample.exceptions;

public class VehicleNotAvailableException extends Exception {
    public VehicleNotAvailableException(String message) {
        super(message);
    }
}

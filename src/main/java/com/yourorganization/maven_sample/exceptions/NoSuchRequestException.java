package com.yourorganization.maven_sample.exceptions;

public class NoSuchRequestException  extends Exception {
    public NoSuchRequestException(String message) {
        super(message);
    }
}
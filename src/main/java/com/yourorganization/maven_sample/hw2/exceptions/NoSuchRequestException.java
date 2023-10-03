package com.yourorganization.maven_sample.hw2.exceptions;

public class NoSuchRequestException  extends Exception {
    public NoSuchRequestException(String message) {
        super(message);
    }
}
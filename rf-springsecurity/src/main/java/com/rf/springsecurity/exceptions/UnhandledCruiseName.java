package com.rf.springsecurity.exceptions;

public class UnhandledCruiseName extends Exception{

    public UnhandledCruiseName() {
    }

    public UnhandledCruiseName(String name) {
        super(name);
    }
}

package com.rf.springsecurity.exceptions;

public class UnsupportedCruiseName extends Exception{

    public UnsupportedCruiseName() {
    }

    public UnsupportedCruiseName(String name) {
        super(name);
    }
}

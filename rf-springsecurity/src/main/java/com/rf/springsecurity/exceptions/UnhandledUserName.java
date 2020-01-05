package com.rf.springsecurity.exceptions;


public class UnhandledUserName extends Exception {
    public UnhandledUserName() {
    }

    public UnhandledUserName(String message) {
        super(message);
    }
}

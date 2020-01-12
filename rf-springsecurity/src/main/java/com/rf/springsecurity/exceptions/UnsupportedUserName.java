package com.rf.springsecurity.exceptions;


public class UnsupportedUserName extends Exception {

    public UnsupportedUserName() {
    }

    public UnsupportedUserName(String message) {
        super(message);
    }
}

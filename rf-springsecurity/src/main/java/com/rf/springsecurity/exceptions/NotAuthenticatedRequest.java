package com.rf.springsecurity.exceptions;

public class NotAuthenticatedRequest extends Exception {


    public NotAuthenticatedRequest() {
        super();
    }

    public NotAuthenticatedRequest(String message) {
        super(message);

    }


}

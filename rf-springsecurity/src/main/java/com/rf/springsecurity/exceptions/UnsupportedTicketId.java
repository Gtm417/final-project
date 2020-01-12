package com.rf.springsecurity.exceptions;

public class UnsupportedTicketId extends Exception{

    public UnsupportedTicketId() {
    }

    public UnsupportedTicketId(String message) {
        super(message);
    }
}

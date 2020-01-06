package com.rf.springsecurity.exceptions;

public class NotEnoughMoney extends Exception {
    public NotEnoughMoney() {
    }

    public NotEnoughMoney(String message) {
        super(message);
    }
}

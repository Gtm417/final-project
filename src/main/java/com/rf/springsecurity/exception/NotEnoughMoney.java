package com.rf.springsecurity.exception;

public class NotEnoughMoney extends Exception {
    public NotEnoughMoney() {
    }

    public NotEnoughMoney(String message) {
        super(message);
    }
}

package com.rf.springsecurity.exception;

public class DataBaseDuplicateConstraint extends Exception{


    public DataBaseDuplicateConstraint(String message, String name) {
        super(message + " " + name);
    }

}

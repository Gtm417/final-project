package com.rf.springsecurity.exceptions;

public class DataBaseDuplicateConstraint extends Exception{


    public DataBaseDuplicateConstraint(String message, String name) {
        super(message + " " + name);
    }

}

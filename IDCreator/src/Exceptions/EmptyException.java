package Exceptions;

import javax.management.ConstructorParameters;

public class EmptyException extends Exception {
    @ConstructorParameters("msg")
    public EmptyException(String msg) {
        super(msg);
    }
}
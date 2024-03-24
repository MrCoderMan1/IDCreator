package Exceptions;

import javax.management.ConstructorParameters;

public class AgeException extends Exception {
    @ConstructorParameters("msg")
    public AgeException(String msg) {
        super(msg);
    }
}
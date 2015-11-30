package com.selop.exception;

/**
 * Thrown when the DI container lookup for a class fails.
 *
 * @author selop
 */
public class BeanNotFoundException extends Exception{
    public BeanNotFoundException() {
        super();
    }
    public BeanNotFoundException(String message) {
        super(message);
    }
    public BeanNotFoundException(String message, Throwable cause){
        super(message, cause);
    }
    public BeanNotFoundException(Throwable cause) {
        super(cause);
    }
}

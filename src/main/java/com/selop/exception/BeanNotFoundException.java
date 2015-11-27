package com.selop.exception;

/**
 * Created by selop on 27/11/15.
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

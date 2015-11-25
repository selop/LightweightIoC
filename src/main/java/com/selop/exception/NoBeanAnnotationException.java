package com.selop.exception;

/**
 * Created by selop on 25/11/15.
 */
public class NoBeanAnnotationException extends Exception {
    public NoBeanAnnotationException() {
        super();
    }
    public NoBeanAnnotationException(String message) {
        super(message);
    }
    public NoBeanAnnotationException(String message, Throwable cause){
        super(message, cause);
    }
    public NoBeanAnnotationException(Throwable cause) {
        super(cause);
    }
}

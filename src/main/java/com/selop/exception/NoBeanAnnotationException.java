package com.selop.exception;

/**
 * Thrown when the scan of a class is aborted due to a missing {@code @Bean} annotation.
 *
 * @author selop
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

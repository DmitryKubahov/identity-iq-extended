package com.sailpoint.identityiq.ui.exception;

/**
 * Error of all component listeners
 */
public class ComponentListenerException extends RuntimeException {

    /**
     * Constructor with parameters
     *
     * @param message - error message
     */
    public ComponentListenerException(String message) {
        super(message);
    }
}

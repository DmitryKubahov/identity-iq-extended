package com.sailpoint.identityiq.ui.exception;

/**
 * Error of building component from xml
 */
public class BuildXmlComponentException extends RuntimeException {

    /**
     * Constructor with parameters
     *
     * @param message - error message
     */
    public BuildXmlComponentException(String message) {
        super(message);
    }
}

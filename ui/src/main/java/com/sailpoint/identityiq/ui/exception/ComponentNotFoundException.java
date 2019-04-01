package com.sailpoint.identityiq.ui.exception;

import java.text.MessageFormat;

/**
 * Error of getting component by id
 */
public class ComponentNotFoundException extends RuntimeException {

    /**
     * Error message for exception
     */
    private static final String COMPONENT_NOT_FOUND_ERROR = "Could not found component by id:[{0}]";

    /**
     * Constructor with parameters
     *
     * @param id - id of component
     */
    public ComponentNotFoundException(String id) {
        super(MessageFormat.format(COMPONENT_NOT_FOUND_ERROR, id));
    }
}

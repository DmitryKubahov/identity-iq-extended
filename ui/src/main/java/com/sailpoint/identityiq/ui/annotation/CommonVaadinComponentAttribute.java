package com.sailpoint.identityiq.ui.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Annotation for pointing common vaadin component attribute
 */
@Target(FIELD)
@Retention(RUNTIME)
public @interface CommonVaadinComponentAttribute {

    /**
     * Name of attribute. By default real field name.
     */
    String name() default "";

    /**
     * Is null valid value and needs to be set
     */
    boolean isNullValid() default false;
}

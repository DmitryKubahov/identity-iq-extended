package com.sailpoint.identityiq.ui.spring.context;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Optional;

/**
 * Spring application context
 */
public class SpringApplicationContext implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    /**
     * Return current application context
     *
     * @return application context
     */
    public static Optional<ApplicationContext> getApplicationContext() {
        return Optional.ofNullable(applicationContext);
    }

    /**
     * Set current application spring context
     *
     * @param applicationContext - spring context
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        SpringApplicationContext.applicationContext = applicationContext;
    }
}





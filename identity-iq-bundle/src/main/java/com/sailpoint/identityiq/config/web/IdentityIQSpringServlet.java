package com.sailpoint.identityiq.config.web;

import com.sailpoint.identityiq.ui.ui.IdentityIqUI;
import com.vaadin.flow.server.VaadinServletConfiguration;
import com.vaadin.flow.spring.SpringServlet;
import org.springframework.context.ApplicationContext;

@VaadinServletConfiguration(ui = IdentityIqUI.class, productionMode = false)
public class IdentityIQSpringServlet extends SpringServlet {
    /**
     * Creates a new Vaadin servlet instance with the application
     * {@code context} provided.
     *
     * @param context            the Spring application context
     * @param forwardingEnforced
     */
    public IdentityIQSpringServlet(ApplicationContext context, boolean forwardingEnforced) {
        super(context, forwardingEnforced);

    }
}

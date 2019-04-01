package com.sailpoint.identityiq.ui.spring;

import com.vaadin.flow.i18n.I18NProvider;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinServiceInitListener;

import java.util.stream.Stream;

/**
 * Vaadin instantiator handler
 */
public interface VaadinXMLComponentSpringInstantiatorHandler {

    /**
     * Get i18n provider
     *
     * @return i18n provider instance
     */
    I18NProvider getI18NProvider();

    /**
     * Return existed or create a new one component
     *
     * @param type - class of component
     * @param <T>  - type of class
     * @return new component
     */
    <T> T getOrCreate(Class<T> type);

    /**
     * Init with vaadin service
     *
     * @param vaadinService - vaadin service instance
     * @return is initialized
     */
    boolean init(VaadinService vaadinService);

    /**
     * Vaadin service init listeners
     *
     * @return stream of listeners
     */
    Stream<VaadinServiceInitListener> getServiceInitListeners();
}

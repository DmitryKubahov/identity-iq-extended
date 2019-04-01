package com.sailpoint.identityiq.ui.spring;

import com.vaadin.flow.di.Instantiator;
import com.vaadin.flow.i18n.I18NProvider;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinServiceInitListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Stream;

/**
 * Vaadin xml component instantiator
 * Do all stuff via handler for extended ability
 */
@Slf4j
public class VaadinXMLComponentSpringInstantiator implements Instantiator {

    /**
     * Handler of current instantiator
     */
    private final transient VaadinXMLComponentSpringInstantiatorHandler handler;

    /**
     * Creates a new vaadin xml instantiator instance.
     *
     * @param handler - instantiator handler bean
     */
    @Autowired
    public VaadinXMLComponentSpringInstantiator(VaadinXMLComponentSpringInstantiatorHandler handler) {
        this.handler = handler;
    }


    /**
     * Run init in handler
     *
     * @param service - vaadin service instance
     * @return is initialize complete
     */
    @Override
    public boolean init(VaadinService service) {
        return handler.init(service);
    }

    /**
     * Get all service listeners for handler
     *
     * @return stream of listeners
     */
    @Override
    public Stream<VaadinServiceInitListener> getServiceInitListeners() {
        return handler.getServiceInitListeners();
    }

    /**
     * Get i18n provider
     *
     * @return i18n provider instance
     */
    @Override
    public I18NProvider getI18NProvider() {
        return handler.getI18NProvider();
    }

    /**
     * Main function of creating views
     *
     * @param type - class of view
     * @param <T>  - type of view
     * @return view instance
     */
    @Override
    public <T> T getOrCreate(Class<T> type) {
        return handler.getOrCreate(type);
    }

}

package com.sailpoint.identityiq.ui.xml.provider.handler;

import com.sailpoint.identityiq.ui.xml.component.XmlComponent;
import com.sailpoint.identityiq.ui.xml.provider.XmlComponentListenerProvider;
import com.sailpoint.identityiq.ui.xml.provider.XmlComponentProvider;

/**
 * Handler for building components from xml
 */
public interface XmlComponentHandler<T> {

    /**
     * Check if handler can handle this component
     *
     * @param xmlComponent - xml component to handle
     * @return can handle
     */
    boolean canHandle(XmlComponent xmlComponent);

    /**
     * Build component from xml
     *
     * @param xmlComponent - xml component source
     * @return component from xml
     */
    T build(XmlComponent xmlComponent);

    /**
     * Set provider to component handler. Needs for containers components
     *
     * @param xmlComponentProvider - xml component provider
     * @param listenerProvider     - xml component listener provider
     */
    void init(XmlComponentProvider<T> xmlComponentProvider, XmlComponentListenerProvider listenerProvider);
}

package com.sailpoint.identityiq.ui.xml.provider;


import com.sailpoint.identityiq.ui.xml.component.listener.ComponentListener;

/**
 * Xml component listener provider
 */
public interface XmlComponentListenerProvider<T extends ComponentListener.VaadinComponentEventListener> {

    /**
     * Build listener component from xml listener
     *
     * @param componentListener - xml component listener source
     *
     * @return component from xml component source
     */
    T build(ComponentListener<T> componentListener);

}

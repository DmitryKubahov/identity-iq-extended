package com.sailpoint.identityiq.ui.xml.provider;

import com.sailpoint.identityiq.ui.xml.component.XmlComponent;

/**
 * Xml component provider for xml handlers
 */
public interface XmlComponentProvider<T> {

    /**
     * Build component from xml
     *
     * @param xml - xml source
     * @return component from xml
     */
    T build(String xml);

    /**
     * Build component from xml component
     *
     * @param xmlComponent - xml component source
     * @return component from xml component source
     */
    T build(XmlComponent xmlComponent);

}

package com.sailpoint.identityiq.ui.xml.component;

/**
 * Interface for all components
 */
public interface XmlComponent {

    /**
     * Default vaadin xml namespace
     */
    String DEFAULT_XML_NAMESPACE = "http://vaadin.xml.com";

    /**
     * Default layout vaadin xml component namespace
     */
    String LAYOUT_XML_NAMESPACE = DEFAULT_XML_NAMESPACE + "/layout";

    /**
     * Default vaadin xml component namespace
     */
    String COMPONENT_XML_NAMESPACE = DEFAULT_XML_NAMESPACE + "/component";

    /**
     * Default button vaadin xml namespace
     */
    String BUTTON_XML_NAMESPACE = DEFAULT_XML_NAMESPACE + "/button";

    /**
     * Providers xml namespace
     */
    String PROVIDER_XML_NAMESPACE = DEFAULT_XML_NAMESPACE + "/provider";

    /**
     * Default vaadin xml component listener namespace
     */
    String LISTENER_XML_NAMESPACE = DEFAULT_XML_NAMESPACE + "/listener";
}

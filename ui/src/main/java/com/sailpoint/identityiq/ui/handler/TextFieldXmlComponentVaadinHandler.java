package com.sailpoint.identityiq.ui.handler;

import com.sailpoint.identityiq.ui.xml.component.TextField;
import com.sailpoint.identityiq.ui.xml.component.XmlComponent;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Handler for building text field VAADIN components from xml
 */
@Slf4j
@Component
public class TextFieldXmlComponentVaadinHandler
        extends AbstractXmlComponentVaadinHandler<com.vaadin.flow.component.textfield.TextField> {

    /**
     * Check is xml component is text field component
     *
     * @param xmlComponent - xml component to handle
     * @return can build text field vaadin component
     */
    @Override
    public boolean canHandle(XmlComponent xmlComponent) {
        log.debug("Is xml component text field");
        return xmlComponent instanceof TextField;
    }

    /**
     * Build text field vaadin component from source
     *
     * @param source - xml text field component
     * @return vaadin text field component
     */
    @Override
    protected com.vaadin.flow.component.textfield.TextField internalBuild(@NonNull XmlComponent source) {
        log.debug("Build vaadin text field from source");
        return new com.vaadin.flow.component.textfield.TextField();
    }
}

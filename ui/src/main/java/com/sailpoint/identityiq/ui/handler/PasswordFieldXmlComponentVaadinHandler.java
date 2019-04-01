package com.sailpoint.identityiq.ui.handler;

import com.sailpoint.identityiq.ui.xml.component.PasswordField;
import com.sailpoint.identityiq.ui.xml.component.XmlComponent;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Handler for building password field VAADIN components from xml
 */
@Slf4j
@Component
public class PasswordFieldXmlComponentVaadinHandler
        extends AbstractXmlComponentVaadinHandler<com.vaadin.flow.component.textfield.PasswordField> {

    /**
     * Check is xml component is password field component
     *
     * @param xmlComponent - xml component to handle
     * @return can build password field vaadin component
     */
    @Override
    public boolean canHandle(XmlComponent xmlComponent) {
        log.debug("Is xml component password field");
        return xmlComponent instanceof PasswordField;
    }

    /**
     * Build password field vaadin component from source
     *
     * @param source - xml password field component
     * @return vaadin password field component
     */
    @Override
    protected com.vaadin.flow.component.textfield.PasswordField internalBuild(@NonNull XmlComponent source) {
        log.debug("Build vaadin password field from source");
        return new com.vaadin.flow.component.textfield.PasswordField();
    }
}

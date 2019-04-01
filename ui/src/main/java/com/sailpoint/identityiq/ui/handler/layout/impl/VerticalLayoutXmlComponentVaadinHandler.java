package com.sailpoint.identityiq.ui.handler.layout.impl;

import com.sailpoint.identityiq.ui.handler.layout.AbstractLayoutXmlComponentVaadinHandler;
import com.sailpoint.identityiq.ui.xml.component.XmlComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Handler for vertical layout xml component
 */
@Slf4j
@Component
public class VerticalLayoutXmlComponentVaadinHandler
        extends AbstractLayoutXmlComponentVaadinHandler<VerticalLayout> {

    /**
     * Check if xml component is vertical layout or not
     *
     * @param xmlComponent - xml component to check
     * @return can handle (is vertical layout)
     */
    @Override
    public boolean canHandle(XmlComponent xmlComponent) {
        log.debug("Check is xml component:[{}] vertical layout", xmlComponent);
        return xmlComponent instanceof com.sailpoint.identityiq.ui.xml.component.VerticalLayout;
    }

    /**
     * Creates vertical layout as hasComponent implementation
     *
     * @return vertical layout
     */
    @Override
    protected VerticalLayout getContainer() {
        log.debug("Create vertical layout");
        return new VerticalLayout();
    }
}

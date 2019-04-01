package com.sailpoint.identityiq.ui.handler;

import com.sailpoint.identityiq.ui.xml.component.Button;
import com.sailpoint.identityiq.ui.xml.component.XmlComponent;
import com.vaadin.flow.component.icon.Icon;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Handler for building VAADIN button from xml
 */
@Slf4j
@Component
public class ButtonXmlComponentVaadinHandler
        extends AbstractXmlComponentVaadinHandler<com.vaadin.flow.component.button.Button> {

    /**
     * Check is xml component is button component
     *
     * @param xmlComponent - xml component to handle
     *
     * @return can build vaadin button
     */
    @Override
    public boolean canHandle(XmlComponent xmlComponent) {
        log.debug("Is xml component button");
        return xmlComponent instanceof Button;
    }

    /**
     * Build vaadin button from source
     *
     * @param source - xml button component
     *
     * @return vaadin button
     */
    @Override
    protected com.vaadin.flow.component.button.Button internalBuild(@NonNull XmlComponent source) {
        log.debug("Build vaadin button from source");
        return new com.vaadin.flow.component.button.Button();
    }

    /**
     * Apply button specific attributes:
     * - icon
     *
     * @param button - vaadin button
     * @param source - xml component source
     */
    @Override
    protected void applySpecificComponentAttributes(com.vaadin.flow.component.button.Button button,
                                                    XmlComponent source) {
        super.applySpecificComponentAttributes(button, source);

        log.debug("Apply button specific attributes");
        Button xmlButton = (Button) source;
        if (xmlButton.getIcon() != null) {
            log.debug("Set icon:[{}] to button", xmlButton.getIcon());
            button.setIcon(new Icon(xmlButton.getIcon()));
        }

        log.debug("Set disable in click:[{}] to button", xmlButton.getDisableOnClick());
        button.setDisableOnClick(xmlButton.getDisableOnClick());

        log.debug("Set icon after text:[{}] to button", xmlButton.getIconAfterText());
        button.setIconAfterText(xmlButton.getIconAfterText());

    }
}

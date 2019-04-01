package com.sailpoint.identityiq.ui.handler.layout;

import com.sailpoint.identityiq.ui.exception.BuildXmlComponentException;
import com.sailpoint.identityiq.ui.handler.AbstractXmlComponentVaadinHandler;
import com.sailpoint.identityiq.ui.xml.component.XmlComponent;
import com.sailpoint.identityiq.ui.xml.provider.XmlComponentProvider;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

/**
 * Handler for vertical layout xml component
 */
@Slf4j
public abstract class AbstractLayoutXmlComponentVaadinHandler<T extends com.vaadin.flow.component.Component>
        extends AbstractXmlComponentVaadinHandler<T> {

    /**
     * Creates vertical layout from xml component source
     *
     * @param source - xml component source
     * @return vertical layout
     */
    @Override
    protected T internalBuild(XmlComponent source) {
        log.debug("Create vertical layout");
        T container = getContainer();

        com.sailpoint.identityiq.ui.xml.component.AbstractLayout xmlAbstractLayout =
                (com.sailpoint.identityiq.ui.xml.component.AbstractLayout) source;

        if (CollectionUtils.isEmpty(xmlAbstractLayout.getComponents())) {
            log.debug("Xml vertical layout does not contain components");
            return container;
        }

        log.debug("Check contains implementation of HasComponents interface");
        if (!(container instanceof HasComponents)) {
            throw new BuildXmlComponentException("Container is not implementing has component");
        }

        HasComponents hasComponents = (HasComponents) container;
        log.debug("Handler each xml component and adding to layout");

        log.debug("Try to get xml component provider for generating components");
        XmlComponentProvider<T> xmlComponentProvider = getProvider()
                .orElseThrow(() -> new BuildXmlComponentException("No provider in layout handler"));

        xmlAbstractLayout.getComponents().forEach(xmlComponent -> {
            log.debug("Try to handler xml component:[{}]", xmlComponent);
            com.vaadin.flow.component.Component component = xmlComponentProvider.build(xmlComponent);

            log.debug("Got:[{}] after handling xml component:[{}]", component, xmlComponent);
            log.debug("Add component:[{}] to layout", component);
            hasComponents.add(component);

            if (container instanceof FlexComponent) {
                FlexComponent flexContainer = (FlexComponent) container;
                log.debug("Container is flex component. Try to apply flex attributes from each component");
                if (xmlComponent.getAlignment() != null) {
                    log.debug("Set component:[{}] alignment:[{}] to layout", component, xmlComponent.getAlignment());
                    flexContainer.setAlignSelf(xmlComponent.getAlignment(), component);
                }
                if (xmlComponent.getFlexGrow() != null) {
                    log.debug("Set component:[{}] flex grow:[{}] to layout", component, xmlComponent.getFlexGrow());
                    flexContainer.setFlexGrow(xmlComponent.getFlexGrow(), component);
                }
            }
        });

        if (container instanceof FlexComponent && xmlAbstractLayout.getItemsAlignment() != null) {
            log.debug("Set items alignment:[{}] to layout", xmlAbstractLayout.getItemsAlignment());
            ((FlexComponent) container).setAlignItems(xmlAbstractLayout.getItemsAlignment());
        }

        return container;
    }

    /**
     * Create implementation of hasComponent
     *
     * @return implementation of hasComponent
     */
    protected abstract T getContainer();
}

package com.sailpoint.identityiq.ui.test.xml.component;

import com.sailpoint.identityiq.ui.xml.component.AbstractLayout;
import com.sailpoint.identityiq.ui.xml.component.AbstractVaadinXmlComponent;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static com.sailpoint.identityiq.ui.test.TestDataHelper.compareCommonProperties;
import static com.sailpoint.identityiq.ui.test.TestDataHelper.generateTestComponents;
import static com.sailpoint.identityiq.ui.test.TestDataHelper.getTestAlignment;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Common class for all layouts xml component test
 */
@Slf4j
public abstract class AbstractLayoutXmlComponentTest<T extends AbstractLayout> extends AbstractXmlComponentTest<T> {

    /**
     * Set specific attributes for all layout:
     *
     * - itemsAlignment
     * - components
     *
     * @param abstractLayout - target abstract layout instance
     */
    @Override
    protected void setSpecificAttributes(T abstractLayout) {
        super.setSpecificAttributes(abstractLayout);

        FlexComponent.Alignment itemsAlignment = getTestAlignment();
        log.debug("Setting items alignment:[{}]", itemsAlignment);
        abstractLayout.setItemsAlignment(itemsAlignment);

        List<AbstractVaadinXmlComponent> components = null;
        try {
            components = generateTestComponents();
        } catch (IllegalAccessException | InstantiationException e) {
            fail("Could not instantiate test component");
        }
        log.debug("Setting items alignment:[{}]", components);
        abstractLayout.setComponents(components);
    }

    @Override
    protected void compareSpecificAttributes(T expected, T actual) {
        super.compareSpecificAttributes(expected, actual);

        assertEquals(expected.getItemsAlignment(), actual.getItemsAlignment(), "Item alignment is not equals");

        assertEquals(expected.getComponents().size(), actual.getComponents().size(), "Components size is not equals");
        for (AbstractVaadinXmlComponent actualComponent : actual.getComponents()) {
            compareCommonProperties(expected.getComponents().stream()
                            .filter(expectedComponent -> actualComponent.getId().equals(expectedComponent.getId())).findFirst()
                            .orElseThrow(() -> new IllegalArgumentException("Could nit find component in actual list")),
                    actualComponent);
        }
    }
}

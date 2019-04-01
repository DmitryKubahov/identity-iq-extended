package com.sailpoint.identityiq.ui.test.handler;

import com.sailpoint.identityiq.ui.test.TestDataHelper;
import com.sailpoint.identityiq.ui.xml.component.AbstractVaadinXmlComponent;
import com.sailpoint.identityiq.ui.xml.component.listener.ComponentListener;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.HasSize;
import lombok.extern.slf4j.Slf4j;

import static com.sailpoint.identityiq.ui.test.TestDataHelper.setTestCommonProperties;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Abstract class for all handlers
 */
@Slf4j
public abstract class AbstractXmlComponentVaadinHandlerTest<T extends AbstractVaadinXmlComponent> {

    private static final String HUNDRED_PERCENTS = "100%";

    /**
     * Generate text xml component
     *
     * @return test xml component
     */
    protected T generateTestXmlComponentWithTestData() {
        log.debug("Create component");
        T xmlComponent = generateTestXmlComponent();

        log.debug("Set test attributes");
        xmlComponent.setAttributes(TestDataHelper.generateTestAttributes());

        log.debug("Set test common properties");
        setTestCommonProperties(xmlComponent);

        log.debug("Set specific attributes");
        setSpecificAttributes(xmlComponent);

        return xmlComponent;
    }

    /**
     * Default size full check
     */
    protected void checkFullSize(HasSize component) {
        assertEquals(HUNDRED_PERCENTS, component.getHeight(), "Height is not full sized");
        assertEquals(HUNDRED_PERCENTS, component.getWidth(), "Width is not full sized");
    }

    /**
     * Set specific xml component attributes
     *
     * @param xmlComponent - source of attributes
     */
    protected void setSpecificAttributes(T xmlComponent) {}

    /**
     * Create specific xml component
     *
     * @return xml component for test
     */
    protected abstract T generateTestXmlComponent();

    /**
     * Test class for testing listeners mapping
     */
    protected static class TestValueChangeListener
            implements ComponentListener.VaadinComponentEventListener<AbstractField.ComponentValueChangeEvent<Component, Object>> {

        @Override
        public Class<? extends ComponentEvent> eventType() {
            return AbstractField.ComponentValueChangeEvent.class;
        }

        @Override
        public void onComponentEvent(AbstractField.ComponentValueChangeEvent<Component, Object> event) {
            throw new UnsupportedOperationException();
        }
    }
}

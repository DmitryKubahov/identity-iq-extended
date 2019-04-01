package com.sailpoint.identityiq.ui.test.xml;

import com.sailpoint.identityiq.ui.xml.component.listener.ComponentListener;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;

/**
 * Test of marshaller all xml components
 */
public class MarshallerTest {

    /**
     * Just test class for marshaller listener testing
     */
    public static class MarshallerTestListener
            implements ComponentListener.VaadinComponentEventListener<ComponentEvent<Component>> {

        @Override
        public Class<ComponentEvent> eventType() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void onComponentEvent(ComponentEvent<Component> event) {
            throw new UnsupportedOperationException();
        }
    }
}

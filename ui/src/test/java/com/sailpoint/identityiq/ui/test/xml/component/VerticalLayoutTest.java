package com.sailpoint.identityiq.ui.test.xml.component;

import com.sailpoint.identityiq.ui.xml.component.VerticalLayout;

/**
 * Test for marshalling/unmarshalling vertical layout xml component
 */
public class VerticalLayoutTest extends AbstractLayoutXmlComponentTest<VerticalLayout> {

    /**
     * Create vertical layout for testing
     *
     * @return vertical layout instance
     */
    @Override
    protected VerticalLayout generateTestXmlComponent() {
        return new VerticalLayout();
    }

}

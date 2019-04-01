package com.sailpoint.identityiq.ui.test.xml.component;

import com.sailpoint.identityiq.ui.xml.component.TextField;

/**
 * Test for marshalling/unmarshalling text field xml component
 */
public class TextFieldTest extends AbstractXmlComponentTest<TextField> {

    /**
     * Create text field for testing
     *
     * @return text field instance
     */
    @Override
    protected TextField generateTestXmlComponent() {
        return new TextField();
    }
}

package com.sailpoint.identityiq.ui.test.xml.component;

import com.sailpoint.identityiq.ui.xml.component.PasswordField;

/**
 * Test for marshalling/unmarshalling password field xml component
 */
public class PasswordFieldTest extends AbstractXmlComponentTest<PasswordField> {

    /**
     * Create password field for testing
     *
     * @return password field instance
     */
    @Override
    protected PasswordField generateTestXmlComponent() {
        return new PasswordField();
    }
}

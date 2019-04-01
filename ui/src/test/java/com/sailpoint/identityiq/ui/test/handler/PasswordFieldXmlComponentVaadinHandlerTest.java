package com.sailpoint.identityiq.ui.test.handler;

import com.sailpoint.identityiq.ui.handler.PasswordFieldXmlComponentVaadinHandler;
import com.sailpoint.identityiq.ui.xml.component.Button;
import com.sailpoint.identityiq.ui.xml.component.Grid;
import com.sailpoint.identityiq.ui.xml.component.PasswordField;
import com.sailpoint.identityiq.ui.xml.component.TextField;
import com.sailpoint.identityiq.ui.xml.component.VerticalLayout;
import com.sailpoint.identityiq.ui.xml.component.XmlComponent;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Test for password field handler
 */
@Slf4j
public class PasswordFieldXmlComponentVaadinHandlerTest extends AbstractXmlComponentVaadinHandlerTest<PasswordField> {

    /**
     * Handler for password field
     */
    private PasswordFieldXmlComponentVaadinHandler handler = new PasswordFieldXmlComponentVaadinHandler();

    /**
     * Test for supporting handling type
     */
    @Test
    void testCanHandle() {
        List<XmlComponent> notSupportedTypes = Arrays.asList(
                new Button(),
                new Grid(),
                new VerticalLayout(),
                new TextField()
        );

        log.debug("Test canHandle with not supported types:[{}]", notSupportedTypes);
        assertFalse(notSupportedTypes.stream().anyMatch(handler::canHandle),
                "Can handle return true for some unsupported type");
    }

    /**
     * Test setting size full attributes
     */
    @Test
    void testFullSize() {
        PasswordField passwordField = generateTestXmlComponent();
        log.debug("Set size full attribute to true");
        passwordField.setSizeFull(true);

        log.debug("Handle text field");
        com.vaadin.flow.component.textfield.PasswordField vaadinPasswordField = handler.build(passwordField);

        checkFullSize(vaadinPasswordField);
    }

    /**
     * Test common attributes mapping
     */
    @Test
    void testCommonAttributes() {
        log.debug("Create password xml field with attributes");
        PasswordField source = generateTestXmlComponentWithTestData();

        com.vaadin.flow.component.textfield.PasswordField target = handler.build(source);

        assertEquals(source.getEnabled(), target.isEnabled(), "Enabled is not equals");
        assertEquals(source.getVisible(), target.isVisible(), "Visible is not equals");
        assertEquals(source.getId(), target.getId().get(), "Id is not equals");
        assertEquals(source.getLabel(), target.getLabel(), "Label is not equals");
        assertEquals(source.getTitle(), target.getTitle(), "Title is not equals");
        assertEquals(source.getPlaceholder(), target.getPlaceholder(), "Placeholder is not equals");
    }

    /**
     * Create xml password field instance
     *
     * @return password field instance
     */
    @Override
    protected PasswordField generateTestXmlComponent() {
        return new PasswordField();
    }
}

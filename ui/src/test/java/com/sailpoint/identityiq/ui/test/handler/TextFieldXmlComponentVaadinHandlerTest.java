package com.sailpoint.identityiq.ui.test.handler;

import com.sailpoint.identityiq.ui.handler.TextFieldXmlComponentVaadinHandler;
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
 * Test for text field handler
 */
@Slf4j
public class TextFieldXmlComponentVaadinHandlerTest extends AbstractXmlComponentVaadinHandlerTest<TextField> {

    /**
     * Handler for text field
     */
    private TextFieldXmlComponentVaadinHandler handler = new TextFieldXmlComponentVaadinHandler();

    /**
     * Test for supporting handling type
     */
    @Test
    void testCanHandle() {
        List<XmlComponent> notSupportedTypes = Arrays.asList(
                new Button(),
                new Grid(),
                new VerticalLayout(),
                new PasswordField()
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
        TextField textField = generateTestXmlComponent();
        log.debug("Set size full attribute to true");
        textField.setSizeFull(true);

        log.debug("Handle text field");
        com.vaadin.flow.component.textfield.TextField vaadinTextField = handler.build(textField);

        checkFullSize(vaadinTextField);
    }

    /**
     * Test common attributes mapping
     */
    @Test
    void testCommonAttributes() {
        log.debug("Create text xml field with attributes");
        TextField source = generateTestXmlComponentWithTestData();

        com.vaadin.flow.component.textfield.TextField target = handler.build(source);

        assertEquals(source.getEnabled(), target.isEnabled(), "Enabled is not equals");
        assertEquals(source.getVisible(), target.isVisible(), "Visible is not equals");
        assertEquals(source.getId(), target.getId().get(), "Id is not equals");
        assertEquals(source.getLabel(), target.getLabel(), "Label is not equals");
        assertEquals(source.getTitle(), target.getTitle(), "Title is not equals");
        assertEquals(source.getPlaceholder(), target.getPlaceholder(), "Placeholder is not equals");
    }

    /**
     * Create xml text field instance
     *
     * @return text field instance
     */
    @Override
    protected TextField generateTestXmlComponent() {
        return new TextField();
    }
}

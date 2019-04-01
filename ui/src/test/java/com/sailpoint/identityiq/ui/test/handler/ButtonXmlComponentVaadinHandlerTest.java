package com.sailpoint.identityiq.ui.test.handler;

import com.sailpoint.identityiq.ui.handler.ButtonXmlComponentVaadinHandler;
import com.sailpoint.identityiq.ui.test.TestDataHelper;
import com.sailpoint.identityiq.ui.xml.component.Button;
import com.sailpoint.identityiq.ui.xml.component.Grid;
import com.sailpoint.identityiq.ui.xml.component.PasswordField;
import com.sailpoint.identityiq.ui.xml.component.TextField;
import com.sailpoint.identityiq.ui.xml.component.VerticalLayout;
import com.sailpoint.identityiq.ui.xml.component.XmlComponent;
import com.vaadin.flow.component.icon.VaadinIcon;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static com.sailpoint.identityiq.ui.test.TestDataHelper.getRandomBoolean;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Test for ButtonXmlComponentVaadinHandler class
 */
@Slf4j
public class ButtonXmlComponentVaadinHandlerTest extends AbstractXmlComponentVaadinHandlerTest<Button> {

    /**
     * Handler for button field
     */
    private ButtonXmlComponentVaadinHandler handler = new ButtonXmlComponentVaadinHandler();

    /**
     * Test for supporting handling type
     */
    @Test
    void testCanHandle() {
        List<XmlComponent> notSupportedTypes = Arrays.asList(
                new Grid(),
                new VerticalLayout(),
                new TextField(),
                new PasswordField()
        );

        log.debug("Test canHandle with not supported types:[{}]", notSupportedTypes);
        assertFalse(notSupportedTypes.stream().anyMatch(handler::canHandle),
                "Can handle return true for some unsupported type");
    }

    /**
     * Test common attributes mapping
     */
    @Test
    void testCommonAttributes() {
        log.debug("Create button xml field with attributes");
        Button source = generateTestXmlComponentWithTestData();

        com.vaadin.flow.component.button.Button target = handler.build(source);

        assertEquals(source.getEnabled(), target.isEnabled(), "Enabled is not equals");
        assertEquals(source.getVisible(), target.isVisible(), "Visible is not equals");
        assertEquals(source.getId(), target.getId().get(), "Id is not equals");
        assertEquals(source.getText(), target.getText(), "Label is not equals");

        assertNotNull(target.getIcon(), "Icon must be set");
        assertEquals(source.getDisableOnClick(), target.isDisableOnClick(), "DisableOnClick is not equals");
        assertEquals(source.getIconAfterText(), target.isIconAfterText(), "IconAfterText is not equals");
    }

    /**
     * Set specific attributes for button xml component
     *
     * @param xmlButton - button xml component
     */
    @Override
    protected void setSpecificAttributes(Button xmlButton) {
        VaadinIcon icon = TestDataHelper.getRandomVaadinIcon();
        log.debug("Setting icon:[{}]", icon);
        xmlButton.setIcon(icon);

        Boolean disableOnClick = getRandomBoolean();
        log.debug("Setting disableOnClick:[{}]", disableOnClick);
        xmlButton.setDisableOnClick(disableOnClick);

        Boolean iconAfterText = getRandomBoolean();
        log.debug("Setting iconAfterText:[{}]", iconAfterText);
        xmlButton.setIconAfterText(iconAfterText);
    }

    /**
     * Creates new instance of button
     *
     * @return button instance
     */
    @Override
    protected Button generateTestXmlComponent() {
        return new Button();
    }
}

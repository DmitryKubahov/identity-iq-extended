package com.sailpoint.identityiq.ui.test.xml.component;

import com.sailpoint.identityiq.ui.test.TestDataHelper;
import com.sailpoint.identityiq.ui.xml.component.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import lombok.extern.slf4j.Slf4j;

import static com.sailpoint.identityiq.ui.test.TestDataHelper.getRandomBoolean;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test for marshalling/unmarshalling button  xml component
 */
@Slf4j
public class ButtonTest extends AbstractXmlComponentTest<Button> {

    /**
     * Create button for testing
     *
     * @return button instance
     */
    @Override
    protected Button generateTestXmlComponent() {
        return new Button();
    }

    /**
     * Add button specific attributes:
     * - icon
     *
     * @param xmlButton - target instance
     */
    @Override
    protected void setSpecificAttributes(Button xmlButton) {
        super.setSpecificAttributes(xmlButton);

        VaadinIcon icon = TestDataHelper.getRandomVaadinIcon();
        log.debug("Setting icon:[{}]", icon);
        xmlButton.setIcon(icon);

        Boolean disableOnClick = getRandomBoolean();
        log.debug("Setting disableOnClick:[{}]", disableOnClick);
        xmlButton.setDisableOnClick(getRandomBoolean());

        Boolean iconAfterText = getRandomBoolean();
        log.debug("Setting iconAfterText:[{}]", iconAfterText);
        xmlButton.setIconAfterText(getRandomBoolean());
    }

    /**
     * Compare button specific attributes
     * - icon
     *
     * @param expected - expected component
     * @param actual   - actual component
     */
    @Override
    protected void compareSpecificAttributes(Button expected, Button actual) {
        assertEquals(expected.getIcon(), actual.getIcon(), "Icon is not the same");
    }
}

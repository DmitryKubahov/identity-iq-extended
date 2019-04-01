package com.sailpoint.identityiq.ui.test;

import com.sailpoint.identityiq.ui.test.xml.MarshallerTest;
import com.sailpoint.identityiq.ui.xml.component.AbstractVaadinXmlComponent;
import com.sailpoint.identityiq.ui.xml.component.Button;
import com.sailpoint.identityiq.ui.xml.component.Grid;
import com.sailpoint.identityiq.ui.xml.component.PasswordField;
import com.sailpoint.identityiq.ui.xml.component.TextField;
import com.sailpoint.identityiq.ui.xml.component.VerticalLayout;
import com.sailpoint.identityiq.ui.xml.component.WithAttributesXmlComponent;
import com.sailpoint.identityiq.ui.xml.component.listener.ComponentListener;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test data generator class
 */
@Slf4j
public class TestDataHelper {

    /**
     * List of default test components types
     */
    private static final List<Class<? extends AbstractVaadinXmlComponent>> ALL_COMPONENTS_TYPES = Arrays.asList(
            Button.class,
            Grid.class,
            PasswordField.class,
            TextField.class,
            VerticalLayout.class
    );

    /**
     * Default bound value for random int
     */
    private static final int DEFAULT_INT_BOUND = 500;


    /**
     * Generate test components
     */
    public static List<AbstractVaadinXmlComponent> generateTestComponents()
            throws IllegalAccessException, InstantiationException {
        log.debug("Generate test components for all types:[{}]", ALL_COMPONENTS_TYPES);
        return generateTestComponentsByTypes(ALL_COMPONENTS_TYPES);

    }

    /**
     * Generate test components
     */
    public static List<AbstractVaadinXmlComponent> generateTestComponentsByTypes(
            List<Class<? extends AbstractVaadinXmlComponent>> types)
            throws IllegalAccessException, InstantiationException {

        List<AbstractVaadinXmlComponent> result = new ArrayList<>(types.size());
        for (Class<? extends AbstractVaadinXmlComponent> type : types) {
            log.debug("Generate component by type:[{}]", type);
            AbstractVaadinXmlComponent component = type.newInstance();
            setTestCommonProperties(component);
            result.add(component);
        }
        return result;

    }

    /**
     * Generate test attributes
     */
    public static List<WithAttributesXmlComponent.XmlComponentAttribute> generateTestAttributes() {
        int i = new Random().nextInt(500);
        List<WithAttributesXmlComponent.XmlComponentAttribute> attributes = new ArrayList<>(i);
        while (i-- > 1) {
            WithAttributesXmlComponent.XmlComponentAttribute attribute = new WithAttributesXmlComponent.XmlComponentAttribute();
            attribute.setName(UUID.randomUUID().toString());
            attribute.setValue(UUID.randomUUID().toString());
            attributes.add(attribute);
        }
        return attributes;
    }

    /**
     * Generate random size list of listeners with only one class - MarshallerTest.MarshallerTestListener
     *
     * @return list of listeners
     */
    public static List<ComponentListener> generateListeners() {
        int i = new Random().nextInt(500);
        List<ComponentListener> result = new ArrayList<>(i);
        while (i-- > 1) {
            ComponentListener<MarshallerTest.MarshallerTestListener> listener = new ComponentListener<>();
            listener.setType(MarshallerTest.MarshallerTestListener.class);
            result.add(listener);
        }
        return result;
    }

    /**
     * Get random test alignment value
     *
     * @return random alignment value
     */
    public static FlexComponent.Alignment getTestAlignment() {
        return FlexComponent.Alignment.values()[new Random().nextInt(FlexComponent.Alignment.values().length - 1)];
    }

    /**
     * Get random test grid selection mode
     *
     * @return random grid selection mode instance
     */
    public static com.vaadin.flow.component.grid.Grid.SelectionMode getTestSelectionMode() {
        return com.vaadin.flow.component.grid.Grid.SelectionMode.values()[new Random()
                .nextInt(com.vaadin.flow.component.grid.Grid.SelectionMode.values().length - 1)];
    }

    /**
     * Get random vaadin icon value
     *
     * @return random icon value
     */
    public static VaadinIcon getRandomVaadinIcon() {
        return VaadinIcon.values()[getRandomIntBound(VaadinIcon.values().length - 1)];
    }

    /**
     * Get random boolean
     */
    public static Boolean getRandomBoolean() {
        return new Random().nextBoolean();
    }

    /**
     * Get random string
     */
    public static String getRandomString() {
        return UUID.randomUUID().toString();
    }

    /**
     * Get random int with DEFAULT_INT_BOUND bound value
     */
    public static int getRandomInt() {
        return getRandomIntBound(DEFAULT_INT_BOUND);
    }

    /**
     * Get random int with bound
     *
     * @param bound - bound for random int
     */
    public static int getRandomIntBound(int bound) {
        return new Random().nextInt(bound);
    }

    /**
     * Get random double
     */
    public static double getRandomDouble() {
        return new Random().nextDouble();
    }

    /**
     * Set all test values to AbstractVaadinXmlComponent properties:
     * - alignment
     *
     * - enabled
     * - sizeFull
     * - visible
     *
     * - flexGrow
     *
     * - id
     * - label
     * - text
     * - title
     * - placeholder
     * - width
     * - height
     *
     * @param xmlComponent - target instance
     */
    public static void setTestCommonProperties(AbstractVaadinXmlComponent xmlComponent) {
        FlexComponent.Alignment alignment = getTestAlignment();
        log.debug("Setting alignment:[{}]", alignment);
        xmlComponent.setAlignment(alignment);

        Boolean enabled = getRandomBoolean();
        log.debug("Setting enabled:[{}]", enabled);
        xmlComponent.setEnabled(enabled);

        Boolean sizeFull = getRandomBoolean();
        log.debug("Setting sizeFull:[{}]", sizeFull);
        xmlComponent.setSizeFull(sizeFull);

        Boolean visible = getRandomBoolean();
        log.debug("Setting visible:[{}]", visible);
        xmlComponent.setVisible(visible);

        Double flexGrow = getRandomDouble();
        log.debug("Setting flexGrow:[{}]", flexGrow);
        xmlComponent.setFlexGrow(flexGrow);

        String id = getRandomString();
        log.debug("Setting id:[{}]", id);
        xmlComponent.setId(id);

        String label = getRandomString();
        log.debug("Setting label:[{}]", label);
        xmlComponent.setLabel(label);

        String text = getRandomString();
        log.debug("Setting text:[{}]", text);
        xmlComponent.setText(text);

        String title = getRandomString();
        log.debug("Setting title:[{}]", title);
        xmlComponent.setTitle(title);

        String placeholder = getRandomString();
        log.debug("Setting placeholder:[{}]", placeholder);
        xmlComponent.setPlaceholder(placeholder);

        String width = Integer.toString(getRandomInt()).concat("%");
        log.debug("Setting width:[{}]", width);
        xmlComponent.setWidth(width);

        String height = Integer.toString(getRandomInt()).concat("px");
        log.debug("Setting height:[{}]", height);
        xmlComponent.setHeight(height);

    }

    /**
     * Compare common properties from AbstractVaadinXmlComponent
     * All setters in {@link #setTestCommonProperties}
     *
     * @param expected - expected component
     * @param actual   - actual component
     */
    public static void compareCommonProperties(AbstractVaadinXmlComponent expected, AbstractVaadinXmlComponent actual) {
        assertEquals(expected.getAlignment(), actual.getAlignment(), "Alignment is not equals");

        assertEquals(expected.getEnabled(), actual.getEnabled(), "Enabled is not equals");
        assertEquals(expected.getSizeFull(), actual.getSizeFull(), "Size full is not equals");
        assertEquals(expected.getVisible(), actual.getVisible(), "Visible is not equals");

        assertEquals(expected.getFlexGrow(), actual.getFlexGrow(), "Flex grow is not equals");

        assertEquals(expected.getId(), actual.getId(), "Id is not equals");
        assertEquals(expected.getLabel(), actual.getLabel(), "Label is not equals");
        assertEquals(expected.getText(), actual.getText(), "Text is not equals");
        assertEquals(expected.getTitle(), actual.getTitle(), "Title is not equals");
        assertEquals(expected.getPlaceholder(), actual.getPlaceholder(), "Placeholder is not equals");
        assertEquals(expected.getWidth(), actual.getWidth(), "Width is not equals");
        assertEquals(expected.getHeight(), actual.getHeight(), "Height is not equals");
    }
}

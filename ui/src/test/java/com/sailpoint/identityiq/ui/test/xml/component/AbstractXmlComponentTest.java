package com.sailpoint.identityiq.ui.test.xml.component;

import com.sailpoint.identityiq.ui.test.TestDataHelper;
import com.sailpoint.identityiq.ui.xml.component.AbstractVaadinXmlComponent;
import com.sailpoint.identityiq.ui.xml.component.WithAttributesXmlComponent;
import com.sailpoint.identityiq.ui.xml.component.XmlComponent;
import com.sailpoint.identityiq.ui.xml.component.listener.ComponentListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

import static com.sailpoint.identityiq.ui.test.TestDataHelper.compareCommonProperties;
import static com.sailpoint.identityiq.ui.test.TestDataHelper.generateListeners;
import static com.sailpoint.identityiq.ui.test.TestDataHelper.setTestCommonProperties;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Common class for all xml component test
 */
@Slf4j
public abstract class AbstractXmlComponentTest<T extends AbstractVaadinXmlComponent> {

    /**
     * Marshaller instance
     */
    protected Jaxb2Marshaller marshaller;

    /**
     * Initialize marshaller instance
     */
    @BeforeEach
    void init() {
        log.debug("Init marshaller");
        this.marshaller = new Jaxb2Marshaller();
        this.marshaller.setPackagesToScan(XmlComponent.class.getPackage().getName());
        log.debug("Generate properties");
    }

    /**
     * Steps:
     * 1 - create component
     * 2 - set test attributes
     * 3 - set test properties
     * 4 - set test listeners
     * 5 - set specified attributes
     *
     * 6 - marshalling this test component to string
     * 7 - unmarshalling this test string to component
     *
     * 8 - compare attributes
     * 9 - compare properties
     * 10 - compare listeners
     * 11 - compare specific attributes
     */
    @Test
    void testMarshallingUnmarshallingComponent() {

        log.debug("Step 1: create component");
        T xmlComponent = generateTestXmlComponent();

        log.debug("Step 2: set test attributes");
        xmlComponent.setAttributes(TestDataHelper.generateTestAttributes());

        log.debug("Step 3: set test common properties");
        setTestCommonProperties(xmlComponent);

        log.debug("Step 4: set test listeners");
        setTestListeners(xmlComponent);

        log.debug("Step 5: set specific attributes");
        setSpecificAttributes(xmlComponent);

        log.debug("Step 6: marshaling component");
        StringWriter resultXMLWriter = new StringWriter();
        marshaller.marshal(xmlComponent, new StreamResult(resultXMLWriter));
        String resultXML = resultXMLWriter.toString();
        log.debug("xml:[{}]", resultXML);

        log.debug("Step 7: unmarshaling xml");
        T resultComponent = (T) marshaller.unmarshal(new StreamSource(new StringReader(resultXML)));

        log.debug("Step 8: compare attributes");
        compareAttributes(xmlComponent, resultComponent);

        log.debug("Step 9: compare common properties");
        compareCommonProperties(xmlComponent, resultComponent);

        log.debug("Step 10: compare listeners");
        compareListeners(xmlComponent, resultComponent);

        log.debug("Step 11: compare specific attributes");
        compareSpecificAttributes(xmlComponent, resultComponent);
    }

    /**
     * Implements to compare specific attributes. Empty by default.
     *
     * @param expected - expected component
     * @param actual   - actual component
     */
    protected void compareSpecificAttributes(T expected, T actual) { }

    /**
     * Compare expected component listeners with actual
     *
     * @param expected - expected component
     * @param actual   - actual component
     */
    protected void compareListeners(T expected, T actual) {
        assertEquals(expected.getListeners().size(), actual.getListeners().size(),
                "Size of attributes must be the same");
        assertTrue(CollectionUtils.isEqualCollection(expected.getListeners(), actual.getListeners()),
                "Listeners are no equal");
    }

    /**
     * Compare expected component attributes with actual
     *
     * @param expected - expected component
     * @param actual   - actual component
     */
    protected void compareAttributes(WithAttributesXmlComponent expected, WithAttributesXmlComponent actual) {
        assertEquals(expected.getAttributes().size(), actual.getAttributes().size(),
                "Size of attributes must be the same");
        assertTrue(CollectionUtils.isEqualCollection(expected.getAttributes(), actual.getAttributes()),
                "Attributes are no equal");
    }


    /**
     * Set specific attributes for component. Each test should override it.
     *
     * @param xmlComponent - target instance
     */
    protected void setSpecificAttributes(T xmlComponent) {
        log.debug("No specific attributes");
    }

    /**
     * Set test listeners to AbstractVaadinXmlComponent
     *
     * @param xmlComponent - target instance
     */
    protected void setTestListeners(T xmlComponent) {
        List<ComponentListener> listeners = generateListeners();
        log.debug("Set listeners count:[{}]", listeners.size());
        xmlComponent.setListeners(listeners);
    }


    /**
     * Create specific xml component
     *
     * @return xml component for test
     */
    protected abstract T generateTestXmlComponent();
}

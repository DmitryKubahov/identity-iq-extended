package com.sailpoint.identityiq.ui.xml.component;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;
import java.util.stream.Collectors;

import static com.sailpoint.identityiq.ui.test.TestDataHelper.getRandomString;
import static com.sailpoint.identityiq.ui.xml.component.TestGridDataProviderEntity.PROPERTY_NAMES;
import static com.sailpoint.identityiq.ui.xml.component.XmlComponent.PROVIDER_XML_NAMESPACE;

/**
 * Test class for testing provider mapping
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "testGridDataProvider", namespace = PROVIDER_XML_NAMESPACE)
@XmlRootElement(name = "testGridDataProvider", namespace = PROVIDER_XML_NAMESPACE)
public class TestGridDataProvider extends Grid.GridDataProvider<TestGridDataProviderEntity> {

    /**
     * Test columns data for test entity
     */
    private final List<Grid.GridComponentColumn> columns;

    /**
     * Default constructor
     */
    public TestGridDataProvider() {
        super();
        this.columns = PROPERTY_NAMES.stream().map(propertyName -> {
            Grid.GridComponentColumn column = new Grid.GridComponentColumn();
            column.setProperty(propertyName);
            column.setCaption(getRandomString());
            return column;
        }).collect(Collectors.toList());
    }

    /**
     * Return test entity type
     *
     * @return test entity class
     */
    @Override
    public Class<TestGridDataProviderEntity> getEntityType() {
        return TestGridDataProviderEntity.class;
    }
}

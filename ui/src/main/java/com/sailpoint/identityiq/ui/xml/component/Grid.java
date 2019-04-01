package com.sailpoint.identityiq.ui.xml.component;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

import static com.sailpoint.identityiq.ui.xml.component.XmlComponent.COMPONENT_XML_NAMESPACE;

/**
 * Grid component
 */
@Data
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "grid", namespace = COMPONENT_XML_NAMESPACE)
@XmlRootElement(name = "grid", namespace = COMPONENT_XML_NAMESPACE)
public class Grid extends AbstractVaadinXmlComponent {

    /**
     * Selection mode
     */
    @XmlAttribute(name = "selectionMode")
    public com.vaadin.flow.component.grid.Grid.SelectionMode selectionMode;

    /**
     * Data provider
     */
    @XmlElementRef(name = "provider")
    private GridDataProvider provider;

    /**
     * Grid columns data
     */
    @XmlElementWrapper(name = "columns")
    @XmlElement(name = "column")
    private List<GridComponentColumn> columns;

    /**
     * Class for grid column component
     */
    @Data
    @NoArgsConstructor
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "/grid/column", namespace = DEFAULT_XML_NAMESPACE)
    public static class GridComponentColumn {

        /**
         * Bean property of grid column
         */
        @XmlAttribute(name = "property")
        private String property;

        /**
         * Caption of column
         */
        @XmlAttribute(name = "caption")
        private String caption;
    }

    /**
     * Grid data provider
     */
    @Data
    @NoArgsConstructor
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "abstractGridProvider", namespace = PROVIDER_XML_NAMESPACE)
    public abstract static class GridDataProvider<T> extends WithAttributesXmlComponent {

        /**
         * Entity type (bean) class for building grid
         */
        @XmlAttribute(name = "entityType")
        private Class<T> entityType;
    }
}

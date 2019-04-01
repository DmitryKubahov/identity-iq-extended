package com.sailpoint.identityiq.ui.xml.component;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

import static com.sailpoint.identityiq.ui.xml.component.XmlComponent.DEFAULT_XML_NAMESPACE;

/**
 * Abstract xml components contains all common attributes
 */
@Data
@XmlTransient
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)

@XmlType(name = "attributesXmlComponent", namespace = DEFAULT_XML_NAMESPACE)
public abstract class WithAttributesXmlComponent implements XmlComponent {

    /**
     * Custom attributes
     */
    @XmlElementWrapper(name = "attributes")
    @XmlElement(name = "attribute")
    private List<XmlComponentAttribute> attributes;

    /**
     * Xml component custom attribute
     */
    @Data
    @NoArgsConstructor
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "attribute", namespace = DEFAULT_XML_NAMESPACE)
    public static class XmlComponentAttribute {

        /**
         * Attribute name
         */
        @XmlAttribute(name = "name", required = true)
        private String name;

        /**
         * Attribute value
         */
        @XmlAttribute(name = "value", required = true)
        private String value;
    }
}

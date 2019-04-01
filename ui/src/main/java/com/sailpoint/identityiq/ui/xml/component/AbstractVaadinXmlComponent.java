package com.sailpoint.identityiq.ui.xml.component;


import com.sailpoint.identityiq.ui.annotation.CommonVaadinComponentAttribute;
import com.sailpoint.identityiq.ui.xml.component.listener.ComponentListener;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

import static com.sailpoint.identityiq.ui.xml.component.XmlComponent.DEFAULT_XML_NAMESPACE;

/**
 * Abstract xml components contains all common attributes
 */
@Data
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "abstractVaadinElement", namespace = DEFAULT_XML_NAMESPACE)
public abstract class AbstractVaadinXmlComponent extends WithAttributesXmlComponent {

    /**
     * Id of component
     * Failed to set as common vaadin attribute - return method type Optional<String>
     */
    @XmlAttribute(name = "id")
    private String id;

    /**
     * Height of component
     */
    @XmlAttribute(name = "height")
    @CommonVaadinComponentAttribute
    private String height;

    /**
     * Width of component
     */
    @XmlAttribute(name = "width")
    @CommonVaadinComponentAttribute
    private String width;

    /**
     * Visible flag
     */
    @XmlAttribute(name = "visible")
    @CommonVaadinComponentAttribute
    private Boolean visible = true;

    /**
     * Enabled flag
     */
    @XmlAttribute(name = "enabled")
    @CommonVaadinComponentAttribute
    private Boolean enabled = true;

    /**
     * Title of field
     */
    @XmlAttribute(name = "title")
    @CommonVaadinComponentAttribute
    private String title;

    /**
     * Label of field
     */
    @XmlAttribute(name = "label")
    @CommonVaadinComponentAttribute
    private String label;

    /**
     * Text component
     */
    @XmlAttribute(name = "text")
    @CommonVaadinComponentAttribute
    private String text;

    /**
     * Placeholder of text field
     */
    @XmlAttribute(name = "placeholder")
    @CommonVaadinComponentAttribute
    private String placeholder;

    /**
     * Size full flag
     * Not a common - need specific handling
     */
    @XmlAttribute(name = "sizeFull")
    private Boolean sizeFull = false;

    /**
     * Component alignment
     * Not a common - need specific handling for parent component
     */
    @XmlAttribute(name = "alignment")
    private FlexComponent.Alignment alignment;

    /**
     * Flex grow double value
     * Not a common - need specific handling
     */
    @XmlAttribute(name = "flexGrow")
    private Double flexGrow;

    /**
     * Listeners for component
     * Not a common - need specific handling
     */
    @XmlElementRef
    @XmlElementWrapper(name = "listeners")
    private List<ComponentListener> listeners;

}

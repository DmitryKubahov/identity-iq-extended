package com.sailpoint.identityiq.ui.xml.component;

import com.vaadin.flow.component.orderedlayout.FlexComponent;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlTransient;
import java.util.List;

/**
 * Abstract layout
 */
@Data
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlTransient
public class AbstractLayout extends AbstractVaadinXmlComponent {

    /**
     * Container components alignments
     */
    @XmlAttribute(name = "itemsAlignment")
    private FlexComponent.Alignment itemsAlignment;

    /**
     * Container components list
     */
    @XmlElementRef(name = "component")
    @XmlElementWrapper(name = "components")
    private List<AbstractVaadinXmlComponent> components;
}

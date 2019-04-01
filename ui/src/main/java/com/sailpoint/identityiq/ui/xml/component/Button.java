package com.sailpoint.identityiq.ui.xml.component;

import com.vaadin.flow.component.icon.VaadinIcon;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import static com.sailpoint.identityiq.ui.xml.component.XmlComponent.BUTTON_XML_NAMESPACE;

/**
 * Button component
 */
@Data
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "button", namespace = BUTTON_XML_NAMESPACE)
@XmlRootElement(name = "button", namespace = BUTTON_XML_NAMESPACE)
public class Button extends AbstractVaadinXmlComponent {

    /**
     * DisableOnClick flag
     */
    @XmlAttribute(name = "disableOnClick")
    private Boolean disableOnClick = false;

    /**
     * IconAfterText flag
     */
    @XmlAttribute(name = "iconAfterText")
    private Boolean iconAfterText = false;

    /**
     * Icon value
     */
    @XmlAttribute(name = "icon")
    private VaadinIcon icon;

}

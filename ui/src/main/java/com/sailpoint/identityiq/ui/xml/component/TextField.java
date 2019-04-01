package com.sailpoint.identityiq.ui.xml.component;

import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import static com.sailpoint.identityiq.ui.xml.component.XmlComponent.COMPONENT_XML_NAMESPACE;

/**
 * Text field component
 */
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "textField", namespace = COMPONENT_XML_NAMESPACE)
@XmlRootElement(name = "textField", namespace = COMPONENT_XML_NAMESPACE)
public class TextField extends AbstractVaadinXmlComponent {

}

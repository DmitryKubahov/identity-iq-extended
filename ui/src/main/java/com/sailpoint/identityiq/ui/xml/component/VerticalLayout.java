package com.sailpoint.identityiq.ui.xml.component;

import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import static com.sailpoint.identityiq.ui.xml.component.AbstractLayout.LAYOUT_XML_NAMESPACE;

/**
 * Vertical layout
 */
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "verticalLayout", namespace = LAYOUT_XML_NAMESPACE)
@XmlRootElement(name = "verticalLayout", namespace = LAYOUT_XML_NAMESPACE)
public class VerticalLayout extends AbstractLayout {

}

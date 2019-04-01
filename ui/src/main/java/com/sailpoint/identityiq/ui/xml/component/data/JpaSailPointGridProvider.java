package com.sailpoint.identityiq.ui.xml.component.data;

import com.sailpoint.identityiq.model.entity.SailPointEntity;
import com.sailpoint.identityiq.ui.xml.component.Grid;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import static com.sailpoint.identityiq.ui.xml.component.Grid.GridDataProvider.PROVIDER_XML_NAMESPACE;

/**
 * Common jpa entity sail point grid provider
 */
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "jpaSailPointGridProvider", namespace = PROVIDER_XML_NAMESPACE)
@XmlRootElement(name = "jpaSailPointGridProvider", namespace = PROVIDER_XML_NAMESPACE)
public class JpaSailPointGridProvider<T extends SailPointEntity> extends Grid.GridDataProvider<T> {

}

package com.sailpoint.identityiq.ui.xml.component.listener;

import com.sailpoint.identityiq.ui.xml.component.WithAttributesXmlComponent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import static com.sailpoint.identityiq.ui.xml.component.XmlComponent.LISTENER_XML_NAMESPACE;

/**
 * Component xml listener
 */
@Data
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "componentListener", namespace = LISTENER_XML_NAMESPACE)
@XmlRootElement(name = "componentListener", namespace = LISTENER_XML_NAMESPACE)
public class ComponentListener<T extends ComponentListener.VaadinComponentEventListener>
        extends WithAttributesXmlComponent {

    /**
     * Component listener type
     */
    @XmlAttribute(name = "type", required = true)
    private Class<T> type;


    /**
     * Common class for all vaadin component listeners
     *
     * @param <T> - type of event
     */
    public interface VaadinComponentEventListener<T extends ComponentEvent<? extends Component>>
            extends ComponentEventListener<T> {

        /**
         * Return type of event
         */
        Class<? extends ComponentEvent> eventType();

    }
}

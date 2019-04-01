package com.sailpoint.identityiq.ui.provider.component;

import com.sailpoint.identityiq.ui.exception.BuildXmlComponentException;
import com.sailpoint.identityiq.ui.xml.component.listener.ComponentListener;
import com.sailpoint.identityiq.ui.xml.provider.XmlComponentListenerProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

import static com.sailpoint.identityiq.ui.spring.impl.DefaultVaadinXMLComponentSpringInstantiator.EXTENDED_TYPE_SPRING_CONTEXT_NAME;

/**
 * Implementation of xml component listener provider for vaadin component listeners
 *
 * @param <T> - type of listener
 */
@Slf4j
@Component
public class XmlVaadinComponentListenerProviderImpl<T extends ComponentListener.VaadinComponentEventListener>
        implements XmlComponentListenerProvider<T> {

    /**
     * Spring application context
     */
    private final ApplicationContext applicationContext;

    /**
     * Constructor with parameters
     *
     * @param applicationContext - spring application context
     */
    @Autowired
    public XmlVaadinComponentListenerProviderImpl(
            ApplicationContext applicationContext) {this.applicationContext = applicationContext;}

    /**
     * Build vaadin listener from xml source
     *
     * @param componentListener - xml component listener source
     *
     * @return vaadin component listener
     */
    @Override
    public T build(ComponentListener<T> componentListener) {
        Class<T> type = componentListener.getType();

        log.debug("Try to find extended listener type:[{}]", type);
        type = findExtendedClass(type);

        log.debug("Try to find listener in spring context by type:[{}]", type);
        String[] beansByType = applicationContext.getBeanNamesForType(type);
        log.debug("Beans count:{}", beansByType.length);
        if (beansByType.length == 1) {
            log.debug("Try to find listener in spring context by type:[{}]", type);
            return applicationContext.getBean(type);
        }
        try {
            log.debug("Try to instantiate listener by type:[{}]", type);
            return type.newInstance();
        } catch (IllegalAccessException | InstantiationException ex) {
            log.error("Error while applying listener to component", ex);
            throw new BuildXmlComponentException(ex.getMessage());
        }
    }

    /**
     * Try to find extended class for type
     *
     * @param type - source type
     *
     * @return extended class or null
     */
    private Class<T> findExtendedClass(Class<T> type) {
        log.debug("Try to find extended type");
        String extendedTypeName = MessageFormat.format(EXTENDED_TYPE_SPRING_CONTEXT_NAME, type.getName());
        log.debug("Extended type string:[{}]", extendedTypeName);
        try {
            return (Class<T>) Class.forName(extendedTypeName);
        } catch (ClassNotFoundException e) {}
        log.debug("Could not find extended class");
        return type;
    }
}

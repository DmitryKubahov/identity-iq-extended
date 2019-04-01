package com.sailpoint.identityiq.ui.spring.impl;

import com.sailpoint.identityiq.ui.exception.BuildXmlComponentException;
import com.sailpoint.identityiq.ui.spring.VaadinXMLComponentSpringInstantiatorHandler;
import com.sailpoint.identityiq.ui.xml.provider.XmlComponentProvider;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.i18n.I18NProvider;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinServiceInitListener;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.stream.Stream;

import static com.vaadin.flow.di.DefaultInstantiator.getServiceLoaderListeners;

/**
 * Default xml spring instantiator class
 */
@Slf4j
@Getter
@org.springframework.stereotype.Component
public class DefaultVaadinXMLComponentSpringInstantiator implements VaadinXMLComponentSpringInstantiatorHandler {

    /**
     * Patter for extended component name in context or class name
     */
    public static final String EXTENDED_TYPE_SPRING_CONTEXT_NAME = "{0}Extended";
    /**
     * Patter for extended xml name
     */
    public static final String EXTENDED_XML_FILE_NAME = "/ui/xml/{0}Extended.xml";
    /**
     * Patter for xml name
     */
    public static final String XML_FILE_NAME = "/ui/xml/{0}.xml";

    /**
     * Spring application context
     */
    private final ApplicationContext context;
    /**
     * I18n provider
     */
    private final I18NProvider i18NProvider;
    /**
     * XML vaadin component provider
     */
    private final XmlComponentProvider<? extends Component> xmlComponentProvider;
    /**
     * Vaadin service instance
     */
    private VaadinService vaadinService;

    /**
     * Constructor with parameters
     *
     * @param context              - spring context
     * @param xmlComponentProvider - vaadin xml component provider
     * @param i18NProvider         - i18n provider
     */
    @Autowired
    public DefaultVaadinXMLComponentSpringInstantiator(ApplicationContext context,
                                                       XmlComponentProvider<? extends Component> xmlComponentProvider,
                                                       I18NProvider i18NProvider) {
        this.context = context;
        this.xmlComponentProvider = xmlComponentProvider;
        this.i18NProvider = i18NProvider;


    }

    /**
     * Create component method. Consists of 4 step:
     * 1 - searching extended class in spring context
     * 2 - searching extended class in local context
     * 3 - searching extended xml
     * 4 - searching xml
     * 5 - create default component
     *
     * @param type - current type of component
     * @param <T>  - class type (e.g. Component)
     * @return component to show
     */
    @Override
    public <T> T getOrCreate(Class<T> type) {
        String currentClassName = type.getSimpleName();
        log.debug("Source class name:[{}]", currentClassName);

        String extendedType = MessageFormat.format(EXTENDED_TYPE_SPRING_CONTEXT_NAME, currentClassName);
        log.debug("Extended class name:[{}]", extendedType);

        log.debug("Extended class name:[{}], try to find it in spring context by name", extendedType);
        if (this.context.containsBean(extendedType)) {
            log.debug("Spring context contains bean with name:[{}], return it", extendedType);
            return (T) this.context.getBean(extendedType);
        }

        log.debug("Spring context does not contain bean with name:[{}]", extendedType);
        log.debug("Create root element:[{}]", type);
        T root = context.getAutowireCapableBeanFactory().createBean(type);
        if (root instanceof HasComponents) {
            log.debug("Try to find xml components for it");
            String xml = getXMLString(currentClassName);
            if (StringUtils.isEmpty(xml)) {
                log.debug("No satiable xml for class:[{}]", currentClassName);
                return root;
            }
            log.debug("Build components from xml");
            Component content = xmlComponentProvider.build(xml);
            log.debug("Setting content to root element");
            ((HasComponents) root).add(content);

            return root;
        }
        log.warn("Element by type:[{}] is not container components, return it", type);
        return root;

    }

    /**
     * Initialize vaadin service instance
     *
     * @param vaadinService - vaadin service instance
     * @return always true
     */
    @Override
    public boolean init(VaadinService vaadinService) {
        log.info("Initialize via vaadin service");
        this.vaadinService = vaadinService;
        return true;
    }

    /**
     * Get default stream of listeners
     *
     * @return default listeners
     */
    @Override
    public Stream<VaadinServiceInitListener> getServiceInitListeners() {
        return getServiceLoaderListeners(vaadinService.getClassLoader());
    }

    /**
     * Try to read xml for classpath
     *
     * @param currentClassName - current class name
     * @return string of xml or null if no files (extended/default) not found
     */
    private String getXMLString(String currentClassName) {
        log.debug("It is a new component. Try to find extended xml");
        String extendedPathToXML = MessageFormat.format(EXTENDED_XML_FILE_NAME, currentClassName);
        log.debug("Extended xml path:[{}]", extendedPathToXML);

        String extendedXML = readXML(extendedPathToXML);
        if (!StringUtils.isEmpty(extendedXML)) {
            log.debug("Extended xml file existed");
            log.trace("Extended xml file:[{}]", extendedXML);
            return extendedXML;
        }

        log.debug("Extended xml file not found, try to find default one");
        String defaultPathToXML = MessageFormat.format(XML_FILE_NAME, currentClassName);
        log.debug("Default xml path:[{}]", defaultPathToXML);

        String defaultXML = readXML(defaultPathToXML);
        if (!StringUtils.isEmpty(defaultXML)) {
            log.debug("Default xml file existed");
            log.trace("Default  xml file:[{}]", defaultXML);
            return defaultXML;
        }
        log.debug("No extended/default xml file for class:[{}]", currentClassName);
        return null;
    }

    /**
     * Try to read xml for classpath
     *
     * @param xmlPath - current xml path
     * @return string of xml file or null if no file not found
     */
    private String readXML(String xmlPath) {
        log.debug("Try to find xml by path:[{}]", xmlPath);
        URL url = getClass().getClassLoader().getResource(xmlPath);
        if (url == null) {
            log.debug("File:[{}] does not exist, return null", xmlPath);
            return null;
        }

        log.debug("Try ti read file:[{}] to string", xmlPath);
        try {
            return IOUtils.toString(url, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            log.error("Got error:[{}] while reading xml:[{}] to string", ex.getMessage(), xmlPath, ex);
            throw new BuildXmlComponentException(ex.getMessage());
        }
    }
}

package com.sailpoint.identityiq.ui.provider.component;

import com.sailpoint.identityiq.ui.exception.BuildXmlComponentException;
import com.sailpoint.identityiq.ui.xml.component.XmlComponent;
import com.sailpoint.identityiq.ui.xml.provider.XmlComponentListenerProvider;
import com.sailpoint.identityiq.ui.xml.provider.XmlComponentProvider;
import com.sailpoint.identityiq.ui.xml.provider.handler.XmlComponentHandler;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;

import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.text.MessageFormat;
import java.util.List;

/**
 * Provider for hadling components and build vaadin components
 */
@Slf4j
@Component
public class XmlComponentVaadinProviderImpl<T extends com.vaadin.flow.component.Component>
        implements XmlComponentProvider<T>, InitializingBean {

    /**
     * Handler for xml component not found error message
     */
    public static final String HANDLER_NOT_FOUND_ERROR = "Could not find xml component handler for:[{0}]";

    /**
     * Xml component marshaller
     */
    private final Jaxb2Marshaller marshaller;

    /**
     * Component listener provider
     */
    private final XmlComponentListenerProvider xmlComponentListenerProvider;

    /**
     * All available handlers
     */
    private final List<XmlComponentHandler<T>> handlers;

    /**
     * Constructor with parameters
     *
     * @param marshaller                   - xml component marshaller
     * @param xmlComponentListenerProvider - xml component listener provider
     * @param handlers                     - vaadin xml handlers
     */
    @Autowired
    public XmlComponentVaadinProviderImpl(Jaxb2Marshaller marshaller,
                                          XmlComponentListenerProvider xmlComponentListenerProvider,
                                          List<XmlComponentHandler<T>> handlers) {
        log.debug("Init xml component provider");

        log.debug("Setting xml component marshaller");
        this.marshaller = marshaller;

        log.debug("Setting handlers");
        this.handlers = handlers;

        log.debug("Setting xml component listener provider");
        this.xmlComponentListenerProvider = xmlComponentListenerProvider;

        log.debug("Init xml component provider finished");
    }

    /**
     * Build component from xml source
     *
     * @param xml - xml string source
     *
     * @return component after handling
     */
    @Override
    public T build(@NonNull String xml) {
        log.debug("Try to handle xml component");
        log.trace("Xml component:[{}]", xml);
        XmlComponent xmlComponent;
        try {
            log.debug("Try to unmarshal xml");
            xmlComponent = (XmlComponent) marshaller.unmarshal(new StreamSource(new StringReader(xml)));
            log.trace("After unmarshalling xml component:[{}]", xmlComponent);
            return build(xmlComponent);
        } catch (Exception ex) {
            log.error("Error while handling xml:[{}]", xml, ex);
            throw ex;
        }
    }

    @Override
    public T build(XmlComponent xmlComponent) {
        log.debug("Try to find handler for xml component:[{}]", xmlComponent);
        XmlComponentHandler<T> handler = handlers.stream()
                .filter(handlerToFilter -> handlerToFilter.canHandle(xmlComponent))
                .findFirst().orElseThrow(() -> {
                    String errorMessage = MessageFormat.format(HANDLER_NOT_FOUND_ERROR, xmlComponent);
                    log.error(errorMessage);
                    return new BuildXmlComponentException(errorMessage);
                });
        log.debug("Handler:[{}] can handle xml component:[{}]", handler, xmlComponent);

        log.debug("Try to handle component");
        return handler.build(xmlComponent);
    }

    /**
     * Add provider to each handler
     */
    @Override
    public void afterPropertiesSet() {
        log.debug("Set xml component provider to xml component handler");
        handlers.forEach(handler -> handler.init(this, xmlComponentListenerProvider));
    }
}

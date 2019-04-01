package com.sailpoint.identityiq.config.vaadin;

import com.sailpoint.identityiq.ui.handler.AbstractXmlComponentVaadinHandler;
import com.sailpoint.identityiq.ui.i18n.SpringI18NProvider;
import com.sailpoint.identityiq.ui.provider.component.XmlComponentVaadinProviderImpl;
import com.sailpoint.identityiq.ui.spring.VaadinXMLComponentSpringInstantiator;
import com.sailpoint.identityiq.ui.spring.VaadinXMLComponentSpringInstantiatorHandler;
import com.sailpoint.identityiq.ui.spring.context.SpringApplicationContext;
import com.sailpoint.identityiq.ui.spring.impl.DefaultVaadinXMLComponentSpringInstantiator;
import com.sailpoint.identityiq.ui.xml.provider.XmlComponentProvider;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.i18n.I18NProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for vaadin components
 */
@Configuration
@ComponentScan(basePackageClasses = {
        AbstractXmlComponentVaadinHandler.class,
        XmlComponentVaadinProviderImpl.class,
})
public class VaadinComponentConfiguration {

    /**
     * Create i18n provider - a default one
     *
     * @param context - spring application context
     * @return i18n provider instance
     */
    @Bean
    public I18NProvider i18NProvider(ApplicationContext context) {
        return new SpringI18NProvider(context);
    }

    /**
     * Create vaadin spring custom instantiator
     *
     * @param vaadinXMLComponentSpringInstantiatorHandler - vaadin xml component handler
     * @return vaadin spring instantiator bean
     */
    @Bean
    public VaadinXMLComponentSpringInstantiator vaadinXMLComponentSpringInstantiator(
            VaadinXMLComponentSpringInstantiatorHandler vaadinXMLComponentSpringInstantiatorHandler) {
        return new VaadinXMLComponentSpringInstantiator(vaadinXMLComponentSpringInstantiatorHandler);
    }

    /**
     * Vaadin handler for spring instantiator
     *
     * @param context              - spring context
     * @param xmlComponentProvider - xml component provider
     * @param i18NProvider         - i18n provider
     * @return vaadin soring instantiator handler
     */
    @Bean
    public VaadinXMLComponentSpringInstantiatorHandler vaadinXMLComponentSpringInstantiatorHandler(
            ApplicationContext context,
            XmlComponentProvider<? extends Component> xmlComponentProvider,
            I18NProvider i18NProvider) {
        return new DefaultVaadinXMLComponentSpringInstantiator(context, xmlComponentProvider, i18NProvider);
    }

    /**
     * Init static spring context bean
     */
    @Bean
    public SpringApplicationContext springApplicationContext() {
        return new SpringApplicationContext();
    }


}

package com.sailpoint.identityiq.config.component;

import com.sailpoint.identityiq.ui.xml.component.XmlComponent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

/**
 * Common configuration for xml component
 */
@Configuration
public class ComponentConfiguration {

    /**
     * XML marshaller for component serialization/deserialization from string xml
     *
     * @return marshaller instance
     */
    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setPackagesToScan(XmlComponent.class.getPackage().getName());
        return marshaller;
    }
}

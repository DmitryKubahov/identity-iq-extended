package com.sailpoint.identityiq.config.project;

import com.sailpoint.identityiq.config.component.ComponentConfiguration;
import com.sailpoint.identityiq.config.db.SailPointDataBaseConfiguration;
import com.sailpoint.identityiq.config.security.SecurityConfiguration;
import com.sailpoint.identityiq.config.vaadin.VaadinComponentConfiguration;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;

/**
 * Main project configuration
 */
@Configuration
@Import({
        SecurityConfiguration.class,
        SailPointDataBaseConfiguration.class,
        ComponentConfiguration.class,
        VaadinComponentConfiguration.class
})
public class SailPointConfiguration {

    /**
     * Create yaml properties placeholder for yaml
     * It is necessary to load YAML
     *
     * @return yaml properties place holder bean
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer properties() {
        PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResources(new ClassPathResource("application.yml"));
        propertySourcesPlaceholderConfigurer.setProperties(yaml.getObject());
        return propertySourcesPlaceholderConfigurer;
    }

    /**
     * Message source for application context from sailpoint messages
     *
     * @return message source
     */
    @Bean
    public MessageSource messageSource() {
        final ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("sailpoint/web/messages/iiqMessages");
        return messageSource;
    }

}

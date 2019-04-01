package com.sailpoint.identityiq.ui.i18n;

import com.vaadin.flow.i18n.I18NProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * Spring context i18n provider implementation
 */
@Slf4j
public class SpringI18NProvider implements I18NProvider {

    /**
     * Spring application context
     */
    private final transient ApplicationContext applicationContext;

    /**
     * Constructor with parameters
     *
     * @param applicationContext - spring application context
     */
    public SpringI18NProvider(ApplicationContext applicationContext) {this.applicationContext = applicationContext;}

    /**
     * Return all available locale for component - but need only 1
     *
     * @return fake list with 1 locale - default one
     */
    @Override
    public List<Locale> getProvidedLocales() {
        log.debug("Try to get locals -> return fake list only with default one");
        return Collections.singletonList(Locale.getDefault());
    }

    /**
     * Get message from application context
     *
     * @param key    - key of message
     * @param locale - locale of message
     * @param params - parameters of message
     * @return localized message
     */
    @Override
    public String getTranslation(String key, Locale locale, Object... params) {
        log.debug("Try to get localized message");
        log.trace("Message key:[{}], locale:[{}], arguments:[{}]", key, locale, params);
        return applicationContext.getMessage(key, params, locale);
    }
}

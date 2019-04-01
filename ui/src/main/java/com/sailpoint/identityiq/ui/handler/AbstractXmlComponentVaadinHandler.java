package com.sailpoint.identityiq.ui.handler;

import com.sailpoint.identityiq.ui.annotation.CommonVaadinComponentAttribute;
import com.sailpoint.identityiq.ui.xml.component.AbstractVaadinXmlComponent;
import com.sailpoint.identityiq.ui.xml.component.WithAttributesXmlComponent;
import com.sailpoint.identityiq.ui.xml.component.XmlComponent;
import com.sailpoint.identityiq.ui.xml.component.listener.ComponentListener;
import com.sailpoint.identityiq.ui.xml.provider.XmlComponentListenerProvider;
import com.sailpoint.identityiq.ui.xml.provider.XmlComponentProvider;
import com.sailpoint.identityiq.ui.xml.provider.handler.XmlComponentHandler;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.HasSize;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.Optional;

/**
 * Abstract xml component handler
 */
@Slf4j
@Component
public abstract class AbstractXmlComponentVaadinHandler<T extends com.vaadin.flow.component.Component>
        implements XmlComponentHandler<T> {

    /**
     * Add FluentPropertyBeanIntrospector to bean utils
     */
    static {
        log.debug("Add fluent bean introspector");
        BeanUtilsBean.getInstance().getPropertyUtils().addBeanIntrospector(new FluentPropertyBeanIntrospector());
    }

    /**
     * Provider of xml component
     */
    private XmlComponentProvider<T> provider;

    /**
     * Provider of xml component listener
     */
    private XmlComponentListenerProvider listenerProvider;

    /**
     * Build vaadin component from source via internal method and apply attributes to it
     *
     * @param source - xml component
     *
     * @return vaadin component
     */
    @Override
    public T build(@NonNull XmlComponent source) {
        log.debug("Build vaadin component from source");
        T component = internalBuild(source);

        log.debug("Apply vaadin components attributes");
        applyComponentAttributes(component, source);
        log.debug("Finished applying vaadin components attributes");

        log.debug("Apply specific vaadin components attributes");
        applySpecificComponentAttributes(component, source);
        log.debug("Finished applying specific vaadin components attributes");

        log.debug("Try to apply attributes");
        applyAttributes(component, source);
        log.debug("Finished applying attributes");

        return component;
    }

    /**
     * Apply common components attributes
     *
     * @param component - vaadin component
     * @param source    - xml component source
     */
    protected void applyComponentAttributes(T component, XmlComponent source) {
        if (!(source instanceof AbstractVaadinXmlComponent)) {
            log.debug(
                    "Source is not AbstractVaadinXmlComponent class. Could not set common vaadin component attributes");
            return;
        }
        AbstractVaadinXmlComponent vaadinXmlComponent = (AbstractVaadinXmlComponent) source;

        log.debug("Try to apply size full attribute");
        applySizeAttributes(component, vaadinXmlComponent);
        log.debug("Finished applying size full attribute");

        log.debug("Try to apply common attributes");
        applyCommonAttributes(component, vaadinXmlComponent);
        log.debug("Finished applying common attributes");

        log.debug("Try to apply none common attribute");
        applyComplexCommonAttributes(component, vaadinXmlComponent);
        log.debug("Finished applying none common attribute");

        log.debug("Try to apply listeners");
        applyListeners(component, vaadinXmlComponent);
        log.debug("Finished applying listeners");

    }


    /**
     * Appply listeners from xml component to vaadin component
     *
     * @param component          - vaadin component target
     * @param vaadinXmlComponent - source of listeners
     */
    protected void applyListeners(T component, AbstractVaadinXmlComponent vaadinXmlComponent) {
        if (CollectionUtils.isEmpty(vaadinXmlComponent.getListeners())) {
            log.debug("No listeners for component:[{}]", component.getId());
            return;
        }
        for (ComponentListener componentListener : vaadinXmlComponent.getListeners()) {
            log.debug("Try to apply listener:[{}] to component:[{}]", componentListener, component.getId());

            log.debug("Get listener by type:[{}] from listener provider", componentListener.getType());
            ComponentListener.VaadinComponentEventListener listener = listenerProvider.build(componentListener);
            log.debug("Apply attributes to listener");
            applyAttributes(listener, componentListener);
            log.debug("Add listener to component");
            ComponentUtil.addListener(component, listener.eventType(), listener);
        }
    }

    /**
     * Check if component is HasSize set attributes from xml component source
     *
     * @param component          - vaadin component target
     * @param vaadinXmlComponent - source of attributes
     */
    protected void applySizeAttributes(T component, AbstractVaadinXmlComponent vaadinXmlComponent) {
        log.debug("Try to set size full, width, height");

        if (component instanceof HasSize && vaadinXmlComponent.getSizeFull()) {
            log.debug("Set size full to component:[{}]", component);
            ((HasSize) component).setSizeFull();
        }
    }

    /**
     * Apply complex common attributes to vaadin component from source
     * E.g. id - because return type of id is optional and default implementation of BeanUtils is not working
     *
     * @param component - vaadin component target
     * @param source    - source of common attributes
     */
    protected void applyComplexCommonAttributes(T component, AbstractVaadinXmlComponent source) {
        log.debug("Try to apply id to component");
        if (!StringUtils.isEmpty(source.getId())) {
            log.debug("Apply id:[{}] to component", source.getId());
            component.setId(source.getId());
        }
    }

    /**
     * Apply common attributes to vaadin component from source
     *
     * @param component - vaadin component target
     * @param source    - source of common attributes
     */
    protected void applyCommonAttributes(T component, AbstractVaadinXmlComponent source) {
        log.debug("Get all fields from:[{}] with CommonVaadinComponentAttribute annotation", source);
        Field[] fields = FieldUtils.getFieldsWithAnnotation(source.getClass(), CommonVaadinComponentAttribute.class);
        if (fields == null || fields.length == 0) {
            log.debug("Source does not contains common attributes");
            return;
        }
        log.debug("Try to set common attributes value to component");
        try {
            for (Field field : fields) {
                log.debug("Get CommonVaadinComponentAttribute from field:[{}]", field.getName());
                CommonVaadinComponentAttribute annotation = field.getAnnotation(CommonVaadinComponentAttribute.class);
                String attributeName = StringUtils.isEmpty(annotation.name())
                        ? field.getName()
                        : annotation.name();
                log.debug("Real attribute name is:[{}]", attributeName);

                log.debug("Getting field value");
                field.setAccessible(true);
                Object value = field.get(source);
                log.debug("Real field value:[{}]", value);

                if (value != null || annotation.isNullValid()) {
                    log.debug("Try to set attribute:[{}], value:[{}]", attributeName, value);
                    BeanUtils.setProperty(component, attributeName, value);
                }
            }
        } catch (Exception ex) {
            log.warn("Got error:[{}] while applying common attributes from:[{}] to:[{}]", ex.getMessage(), source,
                    component);
        }
    }

    /**
     * Apply attributes to object
     *
     * @param target - target object
     * @param source - source of attributes
     */
    protected void applyAttributes(Object target, XmlComponent source) {
        log.debug("Check source to WithAttributesXmlComponent class");
        if (!(source instanceof WithAttributesXmlComponent)) {
            log.debug("Source is not WithAttributesXmlComponent class, no attributes to set");
            return;
        }
        WithAttributesXmlComponent attributesSource = (WithAttributesXmlComponent) source;
        if (CollectionUtils.isEmpty(attributesSource.getAttributes())) {
            log.debug("Source does not contain attributes");
            return;
        }
        attributesSource.getAttributes().stream().filter(attribute -> !StringUtils.isEmpty(attribute.getName()))
                .forEach(attribute -> applyProperty(target, attribute.getName(), attribute.getValue()));
    }

    /**
     * Apply property to object
     *
     * @param target        - target object
     * @param attributeName - attribute name
     * @param value         - attribute value
     */
    protected void applyProperty(Object target, String attributeName, Object value) {
        BeanUtilsBean.getInstance().getPropertyUtils().addBeanIntrospector(new FluentPropertyBeanIntrospector());
        log.debug("Try to apply attribute name:[{}] value:[{}] to component:[{}]", attributeName, value, target);
        try {
            BeanUtils.setProperty(target, attributeName, value);
        } catch (Exception ex) {
            log.warn("Could not set property name:[{}], value:[{}], component:[{}]", attributeName, value, target);
        }
    }

    /**
     * Set provider to handler
     *
     * @param xmlComponentProvider - xml component provider
     */
    @Override
    public void init(XmlComponentProvider<T> xmlComponentProvider, XmlComponentListenerProvider listenerProvider) {
        this.provider = xmlComponentProvider;
        this.listenerProvider = listenerProvider;
    }

    /**
     * Return xml component provider
     *
     * @return xml component provider
     */
    public Optional<XmlComponentProvider<T>> getProvider() {
        return Optional.ofNullable(provider);
    }

    /**
     * Internal method for building vaadin component
     *
     * @param source - xml component source
     *
     * @return vaadin component
     */
    protected abstract T internalBuild(XmlComponent source);

    /**
     * Apply specific components attributes.
     * Default - do not do anything
     *
     * @param component - vaadin component
     * @param source    - xml component source
     */
    protected void applySpecificComponentAttributes(T component, XmlComponent source) { }
}

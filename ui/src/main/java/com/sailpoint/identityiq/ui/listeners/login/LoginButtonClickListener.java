package com.sailpoint.identityiq.ui.listeners.login;

import com.sailpoint.identityiq.ui.component.ComponentHelper;
import com.sailpoint.identityiq.ui.component.dashboard.Dashboard;
import com.sailpoint.identityiq.ui.spring.context.SpringApplicationContext;
import com.sailpoint.identityiq.ui.xml.component.listener.ComponentListener;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;

import java.util.Optional;

/**
 * Login button click listener
 */
@Data
@Slf4j
public class LoginButtonClickListener
        implements ComponentListener.VaadinComponentEventListener<ClickEvent<Component>> {

    /**
     * User name field id in page
     */
    private String userNameFieldId;

    /**
     * Password field id in page
     */
    private String passwordFieldId;

    /**
     * Component event handler
     *
     * @param event - click event
     */
    @Override
    public void onComponentEvent(ClickEvent<Component> event) {
        log.debug("Try to login");
        if (StringUtils.isEmpty(userNameFieldId)) {
            throw new IllegalArgumentException("User name field id is not set");
        }
        log.debug("Try to find user name field");
        HasValue userName = findComponentById(userNameFieldId, event.getSource());
        log.debug("Try to find password field");
        HasValue password = findComponentById(passwordFieldId, event.getSource());

        ApplicationContext appletContext = SpringApplicationContext.getApplicationContext()
                .orElseThrow(() -> new IllegalArgumentException("Could not find spring application context"));
        try {
            SecurityContextHolder.getContext().setAuthentication(appletContext.getBean(AuthenticationProvider.class)
                    .authenticate(new UsernamePasswordAuthenticationToken(userName.getValue(), password.getValue())));
            successLogin(event.getSource().getUI());
        } catch (AuthenticationException ex) {
            Notification.show(ex.getMessage());
            failedLogin(event);
        }
    }

    /**
     * Call when user failed to login -> empty
     *
     * @param event - button click event
     */
    protected void failedLogin(ClickEvent<Component> event) { }

    /**
     * Call when user login -> move to dashboard component
     *
     * @param eventUI - button click event UI
     */
    protected void successLogin(Optional<UI> eventUI) {
        log.debug("Login success");
        eventUI.ifPresent(ui -> ui.navigate(Dashboard.class));
    }

    /**
     * Try to find field by id (password or login) in layout
     *
     * @param id     - component id
     * @param source - source of event
     * @return component with id
     */
    protected HasValue findComponentById(String id, Component source) {
        log.debug("Try to find component by ID");
        if (StringUtils.isEmpty(id)) {
            throw new IllegalArgumentException("Id is empty");
        }
        Component component = ComponentHelper.getComponentFromSource(source, id);
        if (!(component instanceof HasValue)) {
            throw new IllegalArgumentException("Component does not contains value attributes");
        }
        return (HasValue) component;

    }

    /**
     * Return component event type
     *
     * @return click event class
     */
    @Override
    public Class<? extends ComponentEvent> eventType() {
        return ClickEvent.class;
    }
}

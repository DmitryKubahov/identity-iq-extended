package com.sailpoint.identityiq.ui.component.login;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import lombok.extern.slf4j.Slf4j;

/**
 * Login page
 */
@Slf4j
@Route(LoginPage.LOGIN_URL)
@Theme(value = Lumo.class, variant = Lumo.DARK)
public class LoginPage extends VerticalLayout {

    /**
     * Login page url
     */
    public static final String LOGIN_URL = "login";

    /**
     * Build simple login page
     */
    public LoginPage() {
        setSizeFull();
    }
}

package com.sailpoint.identityiq.ui.ui;

import com.sailpoint.identityiq.security.SecurityUtils;
import com.sailpoint.identityiq.ui.component.login.LoginPage;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * Main UI for identity iq
 */
@Slf4j
public class IdentityIqUI extends UI {

    /**
     * Init UI for vaadin request. Add checking security stuff
     *
     * @param request - vaddin request
     */
    @Override
    protected void init(VaadinRequest request) {
        log.info("Adding before entering listener for security check");

        addBeforeEnterListener(event -> {
            if (!event.getNavigationTarget().equals(LoginPage.class)) {
                log.debug("Check current user auth");
                if (!SecurityUtils.isUserLoggedIn()) {
                    log.debug("User is not logged in. Reroute user to login page");
                    getSession().setAttribute("targetNavigation", event.getNavigationTarget());
                    event.rerouteTo(LoginPage.class);
                }
            }
        });

    }
}

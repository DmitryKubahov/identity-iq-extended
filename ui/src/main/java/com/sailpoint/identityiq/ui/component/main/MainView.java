package com.sailpoint.identityiq.ui.component.main;

import com.sailpoint.identityiq.ui.component.dashboard.Dashboard;
import com.sailpoint.identityiq.ui.component.identity.Identities;
import com.vaadin.flow.component.applayout.AbstractAppRouterLayout;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.AppLayoutMenu;
import com.vaadin.flow.component.applayout.AppLayoutMenuItem;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import lombok.extern.slf4j.Slf4j;

/**
 * Main view of sail point prototype application
 */
@Slf4j
@Theme(value = Lumo.class, variant = Lumo.DARK)
public class MainView extends AbstractAppRouterLayout {

    /**
     * Configure main app layout
     *
     * @param appLayout     - application layout
     * @param appLayoutMenu - application layout menu
     */
    @Override
    protected void configure(AppLayout appLayout, AppLayoutMenu appLayoutMenu) {
        log.debug("Building application menu");

        log.debug("Adding dashboard menu item");
        appLayoutMenu.addMenuItem(new AppLayoutMenuItem("Dashboard", Dashboard.class.getSimpleName().toLowerCase()));
        log.debug("Adding identities menu item");
        appLayoutMenu.addMenuItem(new AppLayoutMenuItem("Identities", Identities.class.getSimpleName().toLowerCase()));

        log.debug("Application layout has been built");
    }
}

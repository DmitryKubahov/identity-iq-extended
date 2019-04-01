package com.sailpoint.identityiq.ui.component.identity;

import com.sailpoint.identityiq.ui.component.main.MainView;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import lombok.extern.slf4j.Slf4j;

/**
 * Identities page
 */
@Slf4j
@Route(layout = MainView.class)
public class Identities extends VerticalLayout {

    /**
     * Constructor where page is building
     */
    public Identities() {
        log.debug("Set size full for identities page");
        setSizeFull();
    }

}

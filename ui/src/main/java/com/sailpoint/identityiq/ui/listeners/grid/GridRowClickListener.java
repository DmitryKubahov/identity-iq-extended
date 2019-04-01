package com.sailpoint.identityiq.ui.listeners.grid;

import com.sailpoint.identityiq.model.entity.IdentityIq;
import com.sailpoint.identityiq.ui.xml.component.listener.ComponentListener;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.grid.ItemClickEvent;
import com.vaadin.flow.component.notification.Notification;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * Grid row click listener
 */
@Data
@Slf4j
public class GridRowClickListener
        implements ComponentListener.VaadinComponentEventListener<ItemClickEvent<IdentityIq>> {

    /**
     * User name field id in page
     */
    private String userNameFieldId;

    /**
     * Password field id in page
     */
    private String passwordFieldId;

    /**
     * Return component event type
     *
     * @return item click event
     */
    @Override
    public Class<? extends ComponentEvent> eventType() {
        return ItemClickEvent.class;
    }

    @Override
    public void onComponentEvent(ItemClickEvent<IdentityIq> event) {
        Notification.show(event.getItem().getDisplayName());
    }
}

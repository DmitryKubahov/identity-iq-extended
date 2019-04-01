package com.sailpoint.identityiq.ui.listeners.login;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lombok.extern.slf4j.Slf4j;

import java.text.MessageFormat;
import java.util.Optional;

/**
 * Login button click listener
 */
@Slf4j
public class LoginButtonClickListenerExtended extends LoginButtonClickListener {

    /**
     * Notice message
     */
    private static final String MESSAGE = "Really important notice message";

    /**
     * Notice message
     */
    private static final String CLOSE_COUNTER_MESSAGE = "[X]:{0} sec";
    /**
     * Counter value
     */
    private int counter = 10;

    /**
     * Success login extend -> just show decrement dialog from 10 to 0 than call standard method
     *
     * @param eventUI - login button UI
     */
    @Override
    protected void successLogin(Optional<UI> eventUI) {
        log.debug("Call super success");
        super.successLogin(eventUI);

        log.debug("Build count dialog");
        Dialog dialog = new Dialog();
        VerticalLayout dialogContent = new VerticalLayout();

        log.debug("Adding counter");
        Label counterText = new Label(MessageFormat.format(CLOSE_COUNTER_MESSAGE, counter));
        dialogContent.add(counterText);
        dialogContent.setAlignSelf(FlexComponent.Alignment.END, counterText);

        log.debug("Add message notice");
        H1 noticeMessage = new H1(MESSAGE);
        dialog.add(noticeMessage);
        dialogContent.setAlignSelf(FlexComponent.Alignment.CENTER, noticeMessage);

        dialog.add(dialogContent);
        dialog.setCloseOnEsc(false);
        dialog.setCloseOnOutsideClick(false);


        dialogContent.add(counterText);

        dialog.addOpenedChangeListener(openEvent -> {
            Thread decrementCounter = new Thread(() -> {
                while (counter > 1) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        dialog.close();
                    }
                    counter--;
                    openEvent.getSource().getUI()
                            .ifPresent(ui -> ui.access(() -> {
                                counterText.setText(MessageFormat.format(CLOSE_COUNTER_MESSAGE, counter));
                            }));
                }
                dialog.getUI()
                        .ifPresent(ui -> ui.accessSynchronously(dialog::close));
            });
            decrementCounter.start();
        });
        dialog.open();
    }

}

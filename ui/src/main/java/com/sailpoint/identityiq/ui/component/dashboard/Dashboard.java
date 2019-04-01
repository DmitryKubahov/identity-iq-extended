package com.sailpoint.identityiq.ui.component.dashboard;

import com.github.appreciated.css.grid.GridLayoutComponent;
import com.github.appreciated.css.grid.sizes.Flex;
import com.github.appreciated.css.grid.sizes.Length;
import com.github.appreciated.css.grid.sizes.MinMax;
import com.github.appreciated.css.grid.sizes.Repeat;
import com.github.appreciated.layout.FlexibleGridLayout;
import com.sailpoint.identityiq.ui.component.main.MainView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;

/**
 * Dashboard - main layout
 * Contains 2 layouts:
 * 1 - quick links
 * 2 - widgets
 */
@Slf4j
@Route(layout = MainView.class)
@RouteAlias(value = "", layout = MainView.class)
public class Dashboard extends VerticalLayout {

    /**
     * Constructor with initializing stuff
     */
    public Dashboard() {
        log.debug("Adding quick links layout");
        add(new Label("Quick Links"));
        add(buildQuickLinksLayout());

        add(new Label("Widgets"));
        log.debug("Adding widgets layout");
        add(buildWidgetsLayout());
    }

    /**
     * Create quick links layout
     *
     * @return quick links layout instance
     */
    private Component buildQuickLinksLayout() {
        FlexibleGridLayout quickLinksLayout = new FlexibleGridLayout()
                .withColumns(Repeat.RepeatMode.AUTO_FILL, new MinMax(new Length("200px"), new Flex(1)))
                .withAutoRows(new Length("100px"))
                .withPadding(true)
                .withSpacing(true)
                .withAutoFlow(GridLayoutComponent.AutoFlow.ROW_DENSE)
                .withOverflow(GridLayoutComponent.Overflow.AUTO);
        quickLinksLayout.add(getComponents(16, "QL "));
        quickLinksLayout.setSizeFull();
        return quickLinksLayout;
    }


    /**
     * Create widget layout
     *
     * @return widget layout instance
     */
    private Component buildWidgetsLayout() {
        FlexibleGridLayout widgetsLayout = new FlexibleGridLayout()
                .withColumns(Repeat.RepeatMode.AUTO_FILL, new MinMax(new Length("600px"), new Flex(1)))
                .withAutoRows(new Length("350px"))
                .withPadding(true)
                .withSpacing(true)
                .withAutoFlow(GridLayoutComponent.AutoFlow.ROW_DENSE)
                .withOverflow(GridLayoutComponent.Overflow.AUTO);
        widgetsLayout.add(getComponents(50, "W "));
        widgetsLayout.setSizeFull();
        return widgetsLayout;
    }

    /**
     * Generate ${size} layouts with random color and label inside with prefix
     *
     * @param size   - size of layouts
     * @param prefix - prefix of label
     * @return commponets array
     */
    private Component[] getComponents(int size, String prefix) {
        Random colorRandomize = new Random();
        Component[] result = new Component[size];
        int iter = 0;
        while (iter < size) {
            VerticalLayout layout = new VerticalLayout();
            layout.setSizeFull();
            H2 label = new H2(prefix.concat(String.valueOf(iter)));
            layout.add(label);
            layout.setAlignItems(FlexComponent.Alignment.CENTER);
            layout.getStyle()
                    .set("background", "#" + Integer.toHexString(colorRandomize.nextInt()));
            result[iter++] = layout;
        }
        return result;

    }
}

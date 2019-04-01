package com.sailpoint.identityiq.ui.test.component;


import com.sailpoint.identityiq.ui.component.ComponentHelper;
import com.sailpoint.identityiq.ui.exception.ComponentNotFoundException;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test for component helper class
 */
public class ComponentHelperTest {

    /**
     * Test getComponentFromSource method normal execution
     * Case1: - call get getComponentFromSource. => component with same id
     * Case2: - call get findComponentFromSource. => optional with component with same id
     */
    @Test
    void getFindComponentFromSourceTestNormalExecution() {
        String id = UUID.randomUUID().toString();
        Component testComponent = generateRandomComponent(Collections.singletonList(id));

        //Case1
        Component getComponent = ComponentHelper.getComponentFromSource(testComponent, id);
        assertNotNull(getComponent, "Expected GET component must be not null");
        assertEquals(id, getComponent.getId().get(), "Id of GET component must be the same");

        //Case2
        Optional<Component> findComponent = ComponentHelper.findComponentFromSource(testComponent, id);
        assertTrue(findComponent.isPresent(), "Expected FIND optional must contains component");
        assertEquals(id, findComponent.get().getId().get(),
                "Id of FIND optional component must be the same");

    }

    /**
     * Test getComponentFromSource with passing id which is not part of container
     * Case1: - call get getComponentFromSource. => ComponentNotFoundException
     * Case2: - call get findComponentFromSource. => empty optional
     */
    @Test
    void getFindComponentFromSourceComponentNotFoundTest() {
        String id = UUID.randomUUID().toString();
        Component testComponent = generateRandomComponent(Collections.singletonList(id));

        //Case1
        Assertions.assertThrows(ComponentNotFoundException.class,
                () -> ComponentHelper.getComponentFromSource(testComponent, UUID.randomUUID().toString()));

        //Case2
        Assertions.assertTrue(!ComponentHelper.findComponentFromSource(testComponent, UUID.randomUUID().toString())
                .isPresent(), "Must ben empty");
    }

    /**
     * Test getComponentsFromSource method normal execution
     * Case1: - call get getComponentsFromSource. => components with same ids
     * Case2: - call get findComponentsFromSource. => optional with component with same id
     */
    @Test
    void getFindComponentsFromSourceTestNormalExecution() {
        List<String> ids = generateTestIds();
        Component testComponent = generateRandomComponent(ids);

        //Case1
        List<Component> geComponents = ComponentHelper.getComponentsFromSource(testComponent, ids);
        assertEquals(ids.size(), geComponents.size(), "Count of GET components must equals to count of ids");
        assertFalse(geComponents.stream().anyMatch(id -> id.getId().isPresent() && !ids.contains(id.getId().get())),
                "GET components list contains component with id not from ids list ");

        //Case2
        List<Component> findComponents = ComponentHelper.getComponentsFromSource(testComponent, ids);
        assertEquals(ids.size(), findComponents.size(), "Count of FIND components must equals to count of ids");
        assertFalse(findComponents.stream()
                        .anyMatch(component -> component.getId().isPresent() && !ids.contains(component.getId().get())),
                "FIND components list contains component with id not from ids list ");
    }

    /**
     * Test getComponentsFromSource method normal execution
     * Case1: - call get getComponentsFromSource. => components with same ids
     * Case2: - call get findComponentsFromSource. => optional with component with same id
     */
    @Test
    void getFindComponentsFromSourceTestNotAllFound() {
        List<String> ids = generateTestIds();
        Component testComponent = generateRandomComponent(ids);
        ids.set(0, UUID.randomUUID().toString());

        //Case1
        Assertions.assertThrows(ComponentNotFoundException.class,
                () -> ComponentHelper.getComponentsFromSource(testComponent, ids));

        //Case2
        List<Component> findComponents = ComponentHelper.findComponentsFromSource(testComponent, ids);
        assertTrue(ids.size() > findComponents.size(), "FIND components list must contains list elements that ids");
    }


    /**
     * Generate component container and insert ids to random components
     * Each component will be on its own layer, deeper than prev
     * E.g.:
     * VerticalLayout
     * ..|-component[id[0]]
     * ..|-VerticalLayout
     * ....|--component[id[1]]
     * ....|-VerticalLayout
     * .......|--component[id[2]]
     * l
     *
     * @return lowest layer
     */
    private Component generateRandomComponent(List<String> ids) {
        VerticalLayout mainContainer = new VerticalLayout();
        VerticalLayout layer = mainContainer;
        for (String id : ids) {
            TextField fieldForId = new TextField();
            fieldForId.setId(id);
            layer.add(fieldForId);
            VerticalLayout newLayer = new VerticalLayout();
            layer.add(newLayer);
            layer = newLayer;
        }
        assertNotNull(layer, "Test component can be null");
        return layer;
    }

    /**
     * Generate random counts of random ids
     *
     * @return test ids
     */
    private List<String> generateTestIds() {
        int i = new Random().nextInt(500);
        List<String> ids = new ArrayList<>(i);
        while (i-- > 1) {
            ids.add(UUID.randomUUID().toString());
        }
        return ids;
    }
}

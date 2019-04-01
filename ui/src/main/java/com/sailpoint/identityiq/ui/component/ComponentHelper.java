package com.sailpoint.identityiq.ui.component;

import com.sailpoint.identityiq.ui.exception.ComponentNotFoundException;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Helper class for components stuff
 */
@Slf4j
public class ComponentHelper {

    /**
     * Only helpers methods
     */
    private ComponentHelper() {}

    /**
     * Search component (by id) in source component and throw ComponentNotFoundException if not found
     *
     * @param source - entry point of source
     * @param id     - id to search
     * @return component
     */
    public static Component getComponentFromSource(@NonNull Component source, @NonNull String id) {
        return findComponentFromSource(source, id).orElseThrow(() -> new ComponentNotFoundException(id));
    }

    /**
     * Search all components (by id from components list) in source component and throw ComponentNotFoundException if not found
     *
     * @param source     - entry point of source
     * @param components - ids to search
     * @return list of components
     */
    public static List<Component> getComponentsFromSource(@NonNull Component source, List<String> components) {
        List<Component> result = findComponentsFromSource(source, components);
        log.debug("Check size of result:[{}] and expected ids:[{}]", result.size(), components.size());
        if (result.size() != components.size()) {
            log.error("Not all components were found. Ids:[{}]", components);
            throw new ComponentNotFoundException(components.toString());
        }
        log.error("All components were found");
        return result;
    }

    /**
     * Search component (by id) in source component
     *
     * @param source - entry point of source
     * @param id     - id to search
     * @return component
     */
    public static Optional<Component> findComponentFromSource(@NonNull Component source, @NonNull String id) {
        log.debug("Try to find components from source by id:[{}]", id);
        log.trace("Source:[{}]", source);

        log.debug("Init result");
        List<Component> result = new ArrayList<>(1);

        log.debug("Start searching");
        findComponentsFromSource(getParent(source), Collections.singletonList(id), result);
        log.debug("Start finished");

        return CollectionUtils.isEmpty(result) ? Optional.empty() : Optional.of(result.get(0));
    }

    /**
     * Search all components (by id from components list) in source component
     *
     * @param source     - entry point of source
     * @param components - ids to search
     * @return list of components
     */
    public static List<Component> findComponentsFromSource(@NonNull Component source, List<String> components) {
        log.debug("Try to find all components from source by ids");
        log.trace("Source:[{}]", source);
        log.trace("Ids:[{}]", components);

        log.debug("Init result list");
        List<Component> result = new ArrayList<>(components.size());

        log.debug("Start searching");
        findComponentsFromSource(getParent(source), components, result);
        log.debug("Finished searching");

        return result;
    }

    /**
     * Return most heist parent for component
     *
     * @param source - entry point of heist parent searching
     * @return heist parent
     */
    private static Component getParent(@NonNull Component source) {
        return source.getParent().map(ComponentHelper::getParent).orElse(source);
    }

    /**
     * Search all components (by id from components list) in source component and its source. All matched components by id add to target list
     *
     * @param source     - entry point of source
     * @param components - ids to search
     * @param result     - result list to add
     * @return is all components were found
     */
    private static boolean findComponentsFromSource(@NonNull Component source, List<String> components,
                                                    List<Component> result) {
        log.debug("Check current source id with id list to search");
        source.getId().filter(components::contains).ifPresent(id -> result.add(source));

        if (result.size() == components.size()) {
            log.debug("Result list contains the same count of elements as components ids list, searching completed");
            return true;
        }
        log.debug("Check source if has components");
        if (source instanceof HasComponents) {
            log.debug("Source has components. Try ti find in its child");
            return source.getChildren().anyMatch(component -> findComponentsFromSource(component, components, result));
        }
        return false;
    }
}

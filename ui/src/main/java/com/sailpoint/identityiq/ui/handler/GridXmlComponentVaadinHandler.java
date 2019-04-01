package com.sailpoint.identityiq.ui.handler;

import com.sailpoint.identityiq.ui.exception.BuildXmlComponentException;
import com.sailpoint.identityiq.ui.handler.data.grid.GridDataProviderHandler;
import com.sailpoint.identityiq.ui.xml.component.Grid;
import com.sailpoint.identityiq.ui.xml.component.XmlComponent;
import com.vaadin.flow.data.provider.DataProvider;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.List;

/**
 * Handler for building grid VAADIN components from xml
 */
@Slf4j
@Component
public class GridXmlComponentVaadinHandler
        extends AbstractXmlComponentVaadinHandler<com.vaadin.flow.component.grid.Grid> {

    /**
     * Can not find data provider handler
     */
    public static final String DATA_PROVIDER_HANDLER_NOT_FOUND_ERROR = "Could not find data provider handler for:[{0}] with type:[{1}]";

    /**
     * Data provider handlers
     */
    private final List<GridDataProviderHandler> handlers;

    /**
     * Constructor with parameters
     *
     * @param handlers - data provider handlers
     */
    @Autowired
    public GridXmlComponentVaadinHandler(List<GridDataProviderHandler> handlers) {
        this.handlers = handlers;
    }

    /**
     * Check is xml component is grid component
     *
     * @param xmlComponent - xml component to handle
     * @return can build grid vaadin component
     */
    @Override
    public boolean canHandle(XmlComponent xmlComponent) {
        log.debug("GRID - is xml component can be handled");
        return xmlComponent instanceof Grid;
    }

    /**
     * Build grid vaadin component from source
     *
     * @param source - xml grid component
     * @return vaadin grid component
     */
    @Override
    protected com.vaadin.flow.component.grid.Grid internalBuild(@NonNull XmlComponent source) {
        log.debug("Build vaadin grid from source");

        log.trace("Source component:[{}]", source);
        log.debug("Cast source to Grid");
        Grid grid = (Grid) source;

        log.debug("Creating data provider");
        DataProvider dataProvider = handlers.stream().filter(handler -> handler.canHandle(grid.getProvider()))
                .findFirst().orElseThrow(() -> new BuildXmlComponentException(MessageFormat
                        .format(DATA_PROVIDER_HANDLER_NOT_FOUND_ERROR, grid.getProvider(),
                                grid.getProvider().getEntityType()))).build(grid.getProvider());
        log.debug("Data provider was created");

        log.debug("Create vaadin grid component for IDENTITY");
        com.vaadin.flow.component.grid.Grid vaadinGid = new com.vaadin.flow.component.grid.Grid<>(
                grid.getProvider().getEntityType(), false);
        log.debug("Create vaadin grid component columns");

        grid.getColumns().forEach(column -> {
            log.debug("Add column. Property:[{}], caption:[{}]", column.getProperty(), column.getCaption());
            vaadinGid.addColumn(column.getProperty()).setHeader(column.getCaption());
        });

        log.debug("Set data provider");
        vaadinGid.setDataProvider(dataProvider);

        if(grid.getSelectionMode() != null){
            log.debug("Set selection mode");
            vaadinGid.setSelectionMode(grid.getSelectionMode());
        }

        log.debug("Building vaadin grid has been finished");
        return vaadinGid;
    }
}

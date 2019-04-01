package com.sailpoint.identityiq.ui.handler.data.grid;

import com.sailpoint.identityiq.ui.xml.component.Grid;
import com.vaadin.flow.data.provider.DataProvider;

/**
 * Handler for data grid providers
 */
public interface GridDataProviderHandler {
    /**
     * Check if handler can handle this data provider
     *
     * @param gridDataProvider - grid data provider instance
     * @return can handle
     */
    boolean canHandle(Grid.GridDataProvider gridDataProvider);

    /**
     * Build data provider from grid data provider
     *
     * @param gridDataProvider - grid data provider instance
     * @return data provider for grid
     */
    DataProvider build(Grid.GridDataProvider gridDataProvider);
}

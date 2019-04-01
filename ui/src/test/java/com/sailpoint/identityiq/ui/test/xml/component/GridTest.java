package com.sailpoint.identityiq.ui.test.xml.component;

import com.sailpoint.identityiq.ui.test.TestDataHelper;
import com.sailpoint.identityiq.ui.xml.component.Grid;
import com.sailpoint.identityiq.ui.xml.component.TestGridDataProvider;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static com.sailpoint.identityiq.ui.test.TestDataHelper.getTestSelectionMode;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Test for marshalling/unmarshalling grid xml component
 */
@Slf4j
public class GridTest extends AbstractXmlComponentTest<Grid> {

    /**
     * Create grid for testing
     *
     * @return grid instance
     */
    @Override
    protected Grid generateTestXmlComponent() {
        return new Grid();
    }

    /**
     * Setting grid specific attributes:
     * - selection mode
     * - provider
     * - columns
     *
     * @param grid - target grid instance
     */
    @Override
    protected void setSpecificAttributes(Grid grid) {
        super.setSpecificAttributes(grid);

        com.vaadin.flow.component.grid.Grid.SelectionMode selectionMode = getTestSelectionMode();
        log.debug("Setting select mode:[{}]", selectionMode);
        grid.setSelectionMode(selectionMode);

        TestGridDataProvider provider = new TestGridDataProvider();
        log.debug("Setting provider:[{}]", provider);
        grid.setProvider(provider);

        log.debug("Setting test attributes to grid provider");
        provider.setAttributes(TestDataHelper.generateTestAttributes());

        List<Grid.GridComponentColumn> columns = provider.getColumns();
        log.debug("Setting test columns:[{}]", columns);
        grid.setColumns(columns);
    }

    @Override
    protected void compareSpecificAttributes(Grid expected, Grid actual) {
        super.compareSpecificAttributes(expected, actual);
        assertEquals(expected.getSelectionMode(), actual.getSelectionMode(), "Selection mode is not equals");
        assertEquals(expected.getProvider().getEntityType(), actual.getProvider().getEntityType(),
                "Provider entity type is not equals");
        compareAttributes(expected.getProvider(), actual.getProvider());

        assertEquals(expected.getColumns().size(), actual.getColumns().size(), "Columns size is not equals");
        for (Grid.GridComponentColumn expectedColumn : expected.getColumns()) {
            if (actual.getColumns().stream().noneMatch(actualColumn -> actualColumn.equals(expectedColumn))) {
                fail("Columns are not equals");
            }
        }
    }
}

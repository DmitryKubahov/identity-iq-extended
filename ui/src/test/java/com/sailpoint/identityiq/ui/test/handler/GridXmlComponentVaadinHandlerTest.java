package com.sailpoint.identityiq.ui.test.handler;

import com.sailpoint.identityiq.ui.exception.BuildXmlComponentException;
import com.sailpoint.identityiq.ui.handler.GridXmlComponentVaadinHandler;
import com.sailpoint.identityiq.ui.handler.data.grid.GridDataProviderHandler;
import com.sailpoint.identityiq.ui.xml.component.Button;
import com.sailpoint.identityiq.ui.xml.component.Grid;
import com.sailpoint.identityiq.ui.xml.component.PasswordField;
import com.sailpoint.identityiq.ui.xml.component.TestGridDataProvider;
import com.sailpoint.identityiq.ui.xml.component.TextField;
import com.sailpoint.identityiq.ui.xml.component.VerticalLayout;
import com.sailpoint.identityiq.ui.xml.component.XmlComponent;
import com.vaadin.flow.component.grid.GridMultiSelectionModel;
import com.vaadin.flow.component.grid.GridNoneSelectionModel;
import com.vaadin.flow.component.grid.GridSingleSelectionModel;
import com.vaadin.flow.data.provider.DataProvider;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.sailpoint.identityiq.ui.test.TestDataHelper.getTestSelectionMode;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test for grid handler
 */
@Slf4j
public class GridXmlComponentVaadinHandlerTest extends AbstractXmlComponentVaadinHandlerTest<Grid> {

    /**
     * Handler for text field
     */
    private GridXmlComponentVaadinHandler handler;

    /**
     * Grid data provider handler
     */
    private GridDataProviderHandler gridDataProviderHandler;
    /**
     * Xml grid data provider
     */
    private TestGridDataProvider xmlProvider;
    /**
     * Grid data provider
     */
    private DataProvider provider;


    /**
     * Init grid handler
     */
    @BeforeEach
    void init() {
        this.xmlProvider = new TestGridDataProvider();
        this.provider = mock(DataProvider.class);

        this.gridDataProviderHandler = mock(GridDataProviderHandler.class);
        when(gridDataProviderHandler.canHandle(eq(this.xmlProvider))).thenReturn(true);
        when(gridDataProviderHandler.build(eq(this.xmlProvider))).thenReturn(this.provider);


        this.handler = new GridXmlComponentVaadinHandler(Collections.singletonList(gridDataProviderHandler));

    }

    /**
     * Test for unsupported data provider type handler
     */
    @Test
    void testUnsupportedDataProviderTypeHandler() {
        when(gridDataProviderHandler.canHandle(any())).thenReturn(false);
        Grid xmlGrid = generateTestXmlComponentWithTestData();

        assertThrows(BuildXmlComponentException.class, () -> handler.build(xmlGrid));
    }

    /**
     * Test for supporting handling type
     */
    @Test
    void testCanHandle() {
        List<XmlComponent> notSupportedTypes = Arrays.asList(
                new Button(),
                new VerticalLayout(),
                new PasswordField(),
                new TextField()
        );

        log.debug("Test canHandle with not supported types:[{}]", notSupportedTypes);
        assertFalse(notSupportedTypes.stream().anyMatch(handler::canHandle),
                "Can handle return true for some unsupported type");
    }

    /**
     * Test setting size full attributes
     */
    @Test
    void testFullSize() {
        Grid xmlGrid = generateTestXmlComponentWithTestData();
        log.debug("Set size full attribute to true");
        xmlGrid.setSizeFull(true);
        xmlGrid.setHeight(null);
        xmlGrid.setWidth(null);

        log.debug("Handle text field");
        com.vaadin.flow.component.grid.Grid grid = handler.build(xmlGrid);

        checkFullSize(grid);
    }

    /**
     * Test common attributes mapping
     */
    @Test
    void testCommonAttributes() {
        log.debug("Create grid xml with attributes");
        Grid source = generateTestXmlComponentWithTestData();

        com.vaadin.flow.component.grid.Grid target = handler.build(source);

        assertEquals(source.getEnabled(), target.isEnabled(), "Enabled is not equals");
        assertEquals(source.getVisible(), target.isVisible(), "Visible is not equals");
        assertEquals(source.getId(), target.getId().get(), "Id is not equals");

        assertEquals(source.getColumns().size(), target.getColumns().size(), "Columns count is not the same");
    }

    /**
     * Check selection mode mapping:
     * Case1 - selectionMode = SINGLE => grid selection model instance of {@link GridSingleSelectionModel}
     * Case2 - selectionMode = MULTI => grid selection model instance of {@link GridMultiSelectionModel}
     * Case3 - selectionMode = NONE => grid selection model instance of {@link GridNoneSelectionModel}
     * Case4 - selectionMode is null => grid selection model instance of {@link GridSingleSelectionModel}
     */
    @Test
    void testMappingSelectionMode() {
        log.debug("Create grid xml with attributes");
        Grid source = generateTestXmlComponentWithTestData();

        //Case1
        source.setSelectionMode(com.vaadin.flow.component.grid.Grid.SelectionMode.SINGLE);
        com.vaadin.flow.component.grid.Grid target = handler.build(source);
        assertTrue(target.getSelectionModel() instanceof GridSingleSelectionModel, "Selection model is not single");

        //Case2
        source.setSelectionMode(com.vaadin.flow.component.grid.Grid.SelectionMode.MULTI);
        target = handler.build(source);
        assertTrue(target.getSelectionModel() instanceof GridMultiSelectionModel, "Selection model is not multi");

        //Case3
        source.setSelectionMode(com.vaadin.flow.component.grid.Grid.SelectionMode.NONE);
        target = handler.build(source);
        assertTrue(target.getSelectionModel() instanceof GridNoneSelectionModel, "Selection model is not null");

        //Case4
        source.setSelectionMode(null);
        target = handler.build(source);
        assertTrue(target.getSelectionModel() instanceof GridSingleSelectionModel, "Selection model is not null");
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
        com.vaadin.flow.component.grid.Grid.SelectionMode selectionMode = getTestSelectionMode();
        log.debug("Setting select mode:[{}]", selectionMode);
        grid.setSelectionMode(selectionMode);

        log.debug("Setting provider:[{}]", xmlProvider);
        grid.setProvider(xmlProvider);

        List<Grid.GridComponentColumn> columns = xmlProvider.getColumns();
        log.debug("Setting test columns:[{}]", columns);
        grid.setColumns(columns);
    }

    /**
     * Create xml grid instance
     *
     * @return grid instance
     */
    @Override
    protected Grid generateTestXmlComponent() {
        return new Grid();
    }
}

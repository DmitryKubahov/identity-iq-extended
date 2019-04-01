package com.sailpoint.identityiq.ui.handler.data.grid.impl;

import com.sailpoint.identityiq.model.entity.SailPointEntity;
import com.sailpoint.identityiq.ui.exception.BuildXmlComponentException;
import com.sailpoint.identityiq.ui.handler.data.grid.GridDataProviderHandler;
import com.sailpoint.identityiq.ui.provider.data.JpaRepositoryProvider;
import com.sailpoint.identityiq.ui.xml.component.Grid;
import com.sailpoint.identityiq.ui.xml.component.data.JpaSailPointGridProvider;
import com.vaadin.flow.data.provider.DataProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.support.Repositories;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

/**
 * Handler for jpa sail point data grid provider
 */
@Slf4j
@Component
public class JpaSailPointGridDataProviderHandler<T extends SailPointEntity, ID extends Object>
        implements GridDataProviderHandler {

    /**
     * Repository not found error
     */
    public static final String REPOSITORY_NOT_FOUND_ERROR = "Could not find jpa repository for:[{0}]";

    /**
     * Spring data repository resolver
     */
    private final Repositories repositories;

    /**
     * Constructor with parameters
     *
     * @param applicationContext - spring application context
     */
    @Autowired
    public JpaSailPointGridDataProviderHandler(ApplicationContext applicationContext) {
        this.repositories = new Repositories(applicationContext);
    }


    /**
     * Check if handler is JpaSailPointGridProvider
     *
     * @param gridDataProvider - grid data provider instance to check
     * @return is gridDataProvider is JpaSailPointGridProvider
     */
    public boolean canHandle(Grid.GridDataProvider gridDataProvider) {
        log.debug("Try to check gridDataProvider:[{}]", gridDataProvider);
        return gridDataProvider instanceof JpaSailPointGridProvider;
    }

    /**
     * Build data provider from grid data provider
     *
     * @param gridDataProvider - grid data provider instance
     * @return data provider for grid
     */
    public DataProvider build(Grid.GridDataProvider gridDataProvider) {
        log.debug("Try to find repository for:[{}]", gridDataProvider);
        JpaRepository<T, ID> repository = (JpaRepository<T, ID>) repositories
                .getRepositoryFor(gridDataProvider.getEntityType())
                .orElseThrow(() -> new BuildXmlComponentException(
                        MessageFormat.format(REPOSITORY_NOT_FOUND_ERROR, gridDataProvider.getEntityType())));
        log.debug("Repository was found for:[{}]", gridDataProvider);
        return new JpaRepositoryProvider<>(repository);


    }
}

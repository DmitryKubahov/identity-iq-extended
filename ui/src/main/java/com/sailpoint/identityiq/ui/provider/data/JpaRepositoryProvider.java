package com.sailpoint.identityiq.ui.provider.data;

import com.sailpoint.identityiq.model.entity.SailPointEntity;
import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.provider.QuerySortOrder;
import com.vaadin.flow.data.provider.SortDirection;
import lombok.NonNull;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Data provider for grid via spring jpa repository
 *
 * @param <T>  - entity type
 * @param <ID> - id type
 */
public class JpaRepositoryProvider<T extends SailPointEntity, F extends Specification<T>, ID>
        extends AbstractBackEndDataProvider<T, F> {

    /**
     * Source of data
     */
    private final transient JpaRepository<T, ID> repository;

    /**
     * Constructor of provider with repository source
     *
     * @param repository - source of data
     */
    public JpaRepositoryProvider(@NonNull JpaRepository<T, ID> repository) {
        this.repository = repository;
    }

    /**
     * Get data from repository
     *
     * @param query - query with parameters as orders, filters
     * @return stream of entities from repository
     */
    @Override
    protected Stream<T> fetchFromBackEnd(Query<T, F> query) {
        return repository.findAll(
                PageRequest.of(
                        query.getOffset() / query.getLimit(),
                        query.getLimit(),
                        getSort(query.getSortOrders())))
                .get();
    }

    /**
     * Build sort for repository
     *
     * @param sortOrders - current sort orders
     * @return spring data sort instance
     */
    private Sort getSort(List<QuerySortOrder> sortOrders) {
        if (CollectionUtils.isEmpty(sortOrders)) {
            return Sort.unsorted();
        }
        return Sort.by(sortOrders.stream().map(querySortOrder ->
                SortDirection.ASCENDING.equals(querySortOrder.getDirection())
                        ? Sort.Order.asc(querySortOrder.getSorted())
                        : Sort.Order.desc(querySortOrder.getSorted())
        ).collect(Collectors.toList()));
    }

    /**
     * Get current size of entities
     *
     * @param query - query parameters
     * @return current count
     */
    @Override
    protected int sizeInBackEnd(Query<T, F> query) {
        return (int) repository.count();
    }
}

package com.sailpoint.identityiq.model.repository;

import com.sailpoint.identityiq.model.entity.IdentityIq;
import org.springframework.stereotype.Repository;

/**
 * Repository for Identity entity
 */
@Repository
public interface IdentityRepository extends SailPointRepository<IdentityIq> {

    /**
     * Find identity iq by name
     *
     * @param name - name for searching
     * @return identity with name or null
     */
    IdentityIq findByName(String name);
}

package com.sailpoint.identityiq.model.repository;

import com.sailpoint.identityiq.model.entity.SailPointEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Common interface for all sail point repositories
 */
@NoRepositoryBean
public interface SailPointRepository<T extends SailPointEntity> extends JpaRepository<T, String> {
}

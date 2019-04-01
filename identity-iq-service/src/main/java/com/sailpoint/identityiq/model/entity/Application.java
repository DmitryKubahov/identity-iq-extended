package com.sailpoint.identityiq.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Application entity
 */
@Data
@Entity
@Table(name = "SPT_APPLICATION", schema = "identityiq")
public class Application implements SailPointEntity {

    /**
     * Identifier for entity
     */
    @Id
    @Column(name = "id")
    private String id;

    /**
     * Application name
     */
    @Column(name = "name")
    private String name;

}

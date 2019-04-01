package com.sailpoint.identityiq.model.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Account entity
 */
@Data
@Entity
@Table(name = "SPT_LINK", schema = "identityiq")
public class Account implements SailPointEntity {

    /**
     * Identifier for entity
     */
    @Id
    @Column(name = "id")
    private String id;

    /**
     * UUID value
     */
    @Column(name = "uuid")
    private String uuid;

    /**
     * Account display name
     */
    @Column(name = "display_name")
    private String displayName;

    /**
     * Native identity (id from real application)
     */
    @Column(name = "native_identity")
    private String nativeIdentity;

    /**
     * Current identity for account
     */
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "identity_id")
    private IdentityIq identity;

    /**
     * Current application for account
     */
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "application")
    private Application application;

}

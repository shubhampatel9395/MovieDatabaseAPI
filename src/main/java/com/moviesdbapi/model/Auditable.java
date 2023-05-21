package com.moviesdbapi.model;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
public abstract class Auditable<U> {
	@CreatedBy
    protected U createdBy;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    protected Timestamp createdDate;

    @LastModifiedBy
    protected U lastModifiedBy;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    protected Timestamp lastModifiedDate;
}
package com.progettotirocinio.restapi.data.entities;


import com.progettotirocinio.restapi.data.entities.listeners.UUIDEntityListener;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(value = {UUIDEntityListener.class, AuditingEntityListener.class})
@MappedSuperclass
public abstract class GenericEntity
{
    @Id
    @Column(name = "ID",nullable = false,updatable = false)
    protected UUID id;

    @Column(name = "CREATED_DATE",nullable = false,updatable = false)
    @CreatedDate
    protected LocalDate createdDate;

    @Column(name = "LAST_MODIFIED_DATE",nullable = false,updatable = false)
    @LastModifiedDate
    protected LocalDate lastModifiedDate;
}

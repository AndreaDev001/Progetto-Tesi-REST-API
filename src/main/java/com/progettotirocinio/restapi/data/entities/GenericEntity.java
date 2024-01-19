package com.progettotirocinio.restapi.data.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
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
@Entity
@EntityListeners(value = AuditingEntityListener.class)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class GenericEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    protected UUID id;

    @Column(name = "CREATED_DATE",nullable = false,updatable = false)
    @CreatedDate
    protected LocalDate createdDate;

    @Column(name = "LAST_MODIFIED_DATE",nullable = false,updatable = false)
    @LastModifiedDate
    protected LocalDate lastModifiedDate;
}

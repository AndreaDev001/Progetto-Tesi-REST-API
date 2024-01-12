package com.progettotirocinio.restapi.data.entities;


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
@Builder
@Entity
@EntityListeners(value = AuditingEntityListener.class)
@Table(name = "ROLE_OWNERS")
public class RoleOwner
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "ROLE_ID",nullable = false,updatable = false)
    private Role role;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "OWNER_ID",nullable = false,updatable = false)
    private User owner;

    @CreatedDate
    @Column(name = "CREATED_DATE",nullable = false,updatable = false)
    private LocalDate createdDate;

    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_DATE",nullable = false,updatable = false)
    private LocalDate lastModifiedDate;
}

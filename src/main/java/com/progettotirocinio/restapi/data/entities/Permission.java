package com.progettotirocinio.restapi.data.entities;


import com.progettotirocinio.restapi.data.converters.TrimConverter;
import com.progettotirocinio.restapi.data.entities.enums.PermissionType;
import com.progettotirocinio.restapi.data.entities.interfaces.OwnableEntity;
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
@Table(name = "PERMISSIONS")
public class Permission implements OwnableEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "NAME",updatable = false,nullable = false)
    @Convert(converter = TrimConverter.class)
    private String name;

    @Column(name = "TYPE",updatable = false,nullable = false)
    @Enumerated(value = EnumType.STRING)
    private PermissionType type;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "ROLE_ID",nullable = false,updatable = false)
    private Role role;

    @CreatedDate
    @Column(name = "CREATED_DATE",nullable = false,updatable = false)
    private LocalDate createdDate;

    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_DATE",nullable = false,updatable = false)
    private LocalDate lastModifiedDate;

    @Override
    public UUID getOwnerID() {
        return this.role.getOwnerID();
    }
}

package com.progettotirocinio.restapi.data.entities;


import com.progettotirocinio.restapi.data.converters.TrimConverter;
import com.progettotirocinio.restapi.data.entities.enums.PermissionType;
import com.progettotirocinio.restapi.data.entities.interfaces.OwnableEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@Entity
@EntityListeners(value = AuditingEntityListener.class)
@Table(name = "PERMISSIONS")
public class Permission extends GenericEntity implements OwnableEntity
{
    @Column(name = "NAME",updatable = false,nullable = false)
    @Convert(converter = TrimConverter.class)
    private String name;

    @Column(name = "TYPE",updatable = false,nullable = false)
    @Enumerated(value = EnumType.STRING)
    private PermissionType type;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "ROLE_ID",nullable = false,updatable = false)
    private Role role;

    @Override
    public UUID getOwnerID() {
        return this.role.getOwnerID();
    }
}

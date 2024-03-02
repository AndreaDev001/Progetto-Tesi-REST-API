package com.progettotirocinio.restapi.data.entities;


import com.progettotirocinio.restapi.data.converters.TrimConverter;
import com.progettotirocinio.restapi.data.entities.enums.PermissionType;
import com.progettotirocinio.restapi.data.entities.interfaces.BoardElement;
import com.progettotirocinio.restapi.data.entities.interfaces.OwnableEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@EntityListeners(value = AuditingEntityListener.class)
@Table(name = "PERMISSIONS")
public class Permission extends GenericEntity implements OwnableEntity, BoardElement
{
    @Column(name = "NAME",updatable = false,nullable = false)
    @Convert(converter = TrimConverter.class)
    private String name;

    @Column(name = "TYPE",updatable = false,nullable = false)
    @Enumerated(value = EnumType.STRING)
    private PermissionType type;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "ROLE_ID",nullable = false,updatable = false)
    private Role role;

    @Override
    public UUID getOwnerID() {
        return this.role.getOwnerID();
    }

    @Override
    public UUID getBoardID() {
        return this.role.getBoard().getId();
    }
}

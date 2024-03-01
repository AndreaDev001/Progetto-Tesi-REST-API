package com.progettotirocinio.restapi.data.entities;


import com.progettotirocinio.restapi.data.entities.interfaces.BoardElement;
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
@Table(name = "ROLE_OWNERS")
public class RoleOwner extends GenericEntity implements OwnableEntity, BoardElement
{
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "ROLE_ID",nullable = false,updatable = false)
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "OWNER_ID",nullable = false,updatable = false)
    private User owner;

    @Override
    public UUID getBoardID() {
        return role.getBoard().getId();
    }

    @Override
    public UUID getOwnerID() {
        return owner.getId();
    }
}

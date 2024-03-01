package com.progettotirocinio.restapi.data.entities;


import com.progettotirocinio.restapi.data.entities.interfaces.OwnableEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@EntityListeners(value = AuditingEntityListener.class)
@Table(name = "BOARD_MEMBERS",uniqueConstraints = {@UniqueConstraint(columnNames = {"USER_ID","BOARD_ID"})})
public class BoardMember extends GenericEntity implements OwnableEntity
{
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "USER_ID",nullable = false,updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "BOARD_ID",nullable = false,updatable = false)
    private Board board;

    @Override
    public UUID getOwnerID() {
        return this.user.getId();
    }
}

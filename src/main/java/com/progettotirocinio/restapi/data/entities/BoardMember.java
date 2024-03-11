package com.progettotirocinio.restapi.data.entities;


import com.progettotirocinio.restapi.data.entities.interfaces.BoardElement;
import com.progettotirocinio.restapi.data.entities.interfaces.OwnableEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(value = AuditingEntityListener.class)
@Table(name = "BOARD_MEMBERS",uniqueConstraints = {@UniqueConstraint(columnNames = {"USER_ID","BOARD_ID"})})
public class BoardMember extends AmountEntity implements OwnableEntity, BoardElement
{
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "USER_ID",nullable = false,updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "BOARD_ID",nullable = false,updatable = false)
    private Board board;

    @OneToMany(cascade = CascadeType.REMOVE,fetch = FetchType.EAGER,mappedBy = "member",orphanRemoval = true)
    private Set<TaskAssignment> assignedTasks = new HashSet<>();

    @OneToMany(cascade = CascadeType.REMOVE,fetch = FetchType.EAGER,mappedBy = "member",orphanRemoval = true)
    private Set<TeamMember> teams = new HashSet<>();

    @Override
    public UUID getOwnerID() {
        return this.user.getId();
    }

    @Override
    public UUID getBoardID() {
        return board.getId();
    }
}

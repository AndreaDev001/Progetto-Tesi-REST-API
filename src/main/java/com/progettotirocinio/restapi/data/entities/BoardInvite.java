package com.progettotirocinio.restapi.data.entities;


import com.progettotirocinio.restapi.data.converters.TrimConverter;
import com.progettotirocinio.restapi.data.entities.enums.BoardInviteStatus;
import com.progettotirocinio.restapi.data.entities.interfaces.BoardElement;
import com.progettotirocinio.restapi.data.entities.interfaces.OwnableEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@EntityListeners(value = AuditingEntityListener.class)
@Table(name = "BOARD_INVITES",uniqueConstraints = {@UniqueConstraint(columnNames = {"RECEIVER_ID","BOARD_ID"})})
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardInvite extends GenericEntity implements OwnableEntity, BoardElement
{
    @Column(name = "TEXT",nullable = false,updatable = false)
    @Convert(converter = TrimConverter.class)
    @Length(min = 3,max = 20)
    private String text;

    @Column(name = "STATUS",nullable = false)
    private BoardInviteStatus status;

    @Column(name = "EXPIRATION_DATE",nullable = false,updatable = false)
    private LocalDate expirationDate;

    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(name = "PUBLISHER_ID",nullable = false,updatable = false)
    private User publisher;

    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(name = "RECEIVER_ID",nullable = false,updatable = false)
    private User receiver;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "BOARD_ID",nullable = false,updatable = false)
    private Board board;

    @Override
    public UUID getBoardID() {
        return board.getId();
    }

    @Override
    public UUID getOwnerID() {
        return publisher.getId();
    }
}

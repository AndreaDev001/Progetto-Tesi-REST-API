package com.progettotirocinio.restapi.data.entities;


import com.progettotirocinio.restapi.data.converters.TrimConverter;
import com.progettotirocinio.restapi.data.entities.enums.TaskGroupStatus;
import com.progettotirocinio.restapi.data.entities.interfaces.BoardElement;
import com.progettotirocinio.restapi.data.entities.interfaces.OwnableEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(value = AuditingEntityListener.class)
@Table(name = "TASK_GROUPS")
public class TaskGroup extends AmountEntity implements OwnableEntity, BoardElement
{
    @Column(name = "NAME",nullable = false)
    @Convert(converter = TrimConverter.class)
    private String name;

    @Column(name = "CURRENT_ORDER",nullable = false)
    private Integer currentOrder;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS",nullable = false)
    private TaskGroupStatus status;

    @Column(name = "EXPIRATION_DATE",updatable = false)
    private LocalDate expirationDate;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "PUBLISHER_ID",nullable = false)
    private User publisher;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "BOARD_ID",nullable = false,updatable = false)
    @EqualsAndHashCode.Exclude
    private Board board;

    @OneToMany(cascade = CascadeType.REMOVE,fetch = FetchType.EAGER,mappedBy = "group",orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    private Set<Task> tasks = new HashSet<>();
    @Override
    public UUID getOwnerID() {
        return this.publisher.getId();
    }

    @Override
    public UUID getBoardID() {
        return this.board.getId();
    }
}

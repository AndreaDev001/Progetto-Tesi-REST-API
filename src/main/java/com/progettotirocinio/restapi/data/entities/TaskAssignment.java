package com.progettotirocinio.restapi.data.entities;

import com.progettotirocinio.restapi.data.entities.interfaces.OwnableEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Entity
@EntityListeners(value = AuditingEntityListener.class)
@Table(name = "TASK_ASSIGNMENTS",uniqueConstraints = {@UniqueConstraint(columnNames = {"TASK_ID","USER_ID"})})
public class TaskAssignment extends GenericEntity implements OwnableEntity
{
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "USER_ID",nullable = false,updatable = false)
    private User user;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "TASK_ID",nullable = false,updatable = false)
    private Task task;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "PUBLISHER_ID",nullable = false,updatable = false)
    private User publisher;

    @Override
    public UUID getOwnerID() {
        return publisher.getId();
    }
}

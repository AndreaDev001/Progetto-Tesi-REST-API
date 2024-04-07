package com.progettotirocinio.restapi.data.entities.checklists;

import com.progettotirocinio.restapi.data.converters.TrimConverter;
import com.progettotirocinio.restapi.data.entities.AmountEntity;
import com.progettotirocinio.restapi.data.entities.Task;
import com.progettotirocinio.restapi.data.entities.User;
import com.progettotirocinio.restapi.data.entities.interfaces.BoardElement;
import com.progettotirocinio.restapi.data.entities.interfaces.OwnableEntity;
import com.progettotirocinio.restapi.data.entities.interfaces.TaskElement;
import com.progettotirocinio.restapi.data.entities.listeners.UUIDEntityListener;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@EntityListeners(value = {AuditingEntityListener.class, UUIDEntityListener.class})
public class CheckList extends AmountEntity implements OwnableEntity, BoardElement, TaskElement
{
    @Column(name = "NAME",nullable = false)
    @Convert(converter = TrimConverter.class)
    @Length(min = 5,max = 15)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "TASK_ID",nullable = false,updatable = false)
    private Task task;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "PUBLISHER_ID",nullable = false,updatable = false)
    private User publisher;

    @OneToMany(cascade = CascadeType.REMOVE,fetch = FetchType.EAGER,orphanRemoval = true,mappedBy = "checkList")
    private Set<CheckListOption> options = new HashSet<>();

    @Override
    public UUID getOwnerID() {
        return this.publisher.getId();
    }

    @Override
    public UUID getBoardID() {
        return this.task.getBoardID();
    }

    @Override
    public UUID getTaskID() {
        return this.task.getId();
    }
}

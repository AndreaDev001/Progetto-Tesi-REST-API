package com.progettotirocinio.restapi.data.entities.tags;

import com.progettotirocinio.restapi.data.entities.GenericEntity;
import com.progettotirocinio.restapi.data.entities.Task;
import com.progettotirocinio.restapi.data.entities.interfaces.BoardElement;
import com.progettotirocinio.restapi.data.entities.interfaces.TaskElement;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(value = AuditingEntityListener.class)
@Entity
@Table(name = "TAG_ASSIGNMENTS",uniqueConstraints = {@UniqueConstraint(columnNames = {"TAG_ID","TASK_ID"})})
public class TagAssignment extends GenericEntity implements BoardElement, TaskElement
{
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "TAG_ID",nullable = false,updatable = false)
    private Tag tag;

    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    @JoinColumn(name = "TASK_ID",nullable = false,updatable = false)
    private Task task;

    @Override
    public UUID getBoardID() {
        return this.tag.getBoard().getId();
    }

    @Override
    public UUID getTaskID() {
        return this.task.getId();
    }
}

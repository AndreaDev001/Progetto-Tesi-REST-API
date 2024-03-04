package com.progettotirocinio.restapi.data.entities.comments;

import com.progettotirocinio.restapi.data.entities.Task;
import com.progettotirocinio.restapi.data.entities.interfaces.BoardElement;
import com.progettotirocinio.restapi.data.entities.interfaces.TaskElement;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "COMMENT_TASKS")
public class CommentTask extends Comment implements BoardElement, TaskElement
{
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "TASK_ID",nullable = false,updatable = false)
    private Task task;

    @Override
    public UUID getBoardID() {
        return this.task.getBoardID();
    }

    @Override
    public UUID getTaskID() {
        return this.task.getId();
    }
}

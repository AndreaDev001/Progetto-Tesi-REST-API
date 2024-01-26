package com.progettotirocinio.restapi.data.entities.likes;

import com.progettotirocinio.restapi.data.entities.Task;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "TASK_LIKES",uniqueConstraints = {@UniqueConstraint(columnNames = {"USER_ID","TASK_ID"})})
public class TaskLike extends Like
{
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "TASK_ID",nullable = false,updatable = false)
    private Task task;
}

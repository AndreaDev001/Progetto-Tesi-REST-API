package com.progettotirocinio.restapi.data.dto.output.refs;


import com.progettotirocinio.restapi.data.dto.output.GenericOutput;
import com.progettotirocinio.restapi.data.entities.TaskGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TaskGroupRef extends GenericOutput<TaskGroupRef>
{
    private String name;
    private BoardRef board;

    public TaskGroupRef(TaskGroup taskGroup) {
        this.id = taskGroup.getId();
        this.name = taskGroup.getName();
        this.board = new BoardRef(taskGroup.getBoard());
        this.createdDate = taskGroup.getCreatedDate();
    }
}

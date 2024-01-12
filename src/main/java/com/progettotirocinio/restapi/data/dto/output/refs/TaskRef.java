package com.progettotirocinio.restapi.data.dto.output.refs;


import com.progettotirocinio.restapi.data.dto.output.GenericOutput;
import com.progettotirocinio.restapi.data.entities.Task;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TaskRef extends GenericOutput<TaskRef>
{
    private String title;
    private String name;
    private UserRef publisher;
    private TaskGroupRef taskGroup;

    public TaskRef(Task task) {
        this.id = task.getId();
        this.name = task.getName();
        this.createdDate = task.getCreatedDate();
    }
}

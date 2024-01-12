package com.progettotirocinio.restapi.data.dto.output;


import com.progettotirocinio.restapi.data.dto.output.refs.TaskGroupRef;
import com.progettotirocinio.restapi.data.dto.output.refs.UserRef;
import com.progettotirocinio.restapi.data.entities.Task;
import com.progettotirocinio.restapi.data.entities.enums.Priority;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TaskDto extends GenericOutput<TaskDto>
{
    private String title;
    private String name;
    private String description;
    private Priority priority;
    private UserRef publisher;
    private TaskGroupRef taskGroup;

    @Override
    public void addLinks(Object... params) {

    }
}

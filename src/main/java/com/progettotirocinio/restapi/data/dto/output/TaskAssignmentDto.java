package com.progettotirocinio.restapi.data.dto.output;

import com.progettotirocinio.restapi.data.dto.output.refs.TaskRef;
import com.progettotirocinio.restapi.data.dto.output.refs.UserRef;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TaskAssignmentDto extends GenericOutput<TaskAssignmentDto>
{
    public UserRef user;
    public TaskRef task;
    public UserRef publisher;
}

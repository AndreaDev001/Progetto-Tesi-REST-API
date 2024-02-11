package com.progettotirocinio.restapi.data.dto.output;


import com.progettotirocinio.restapi.data.dto.annotations.AmountReference;
import com.progettotirocinio.restapi.data.dto.output.refs.BoardRef;
import com.progettotirocinio.restapi.data.dto.output.refs.UserRef;
import com.progettotirocinio.restapi.data.entities.TaskGroup;
import com.progettotirocinio.restapi.data.entities.enums.TaskGroupStatus;
import lombok.*;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Relation(collectionRelation = "content")
public class TaskGroupDto extends GenericOutput<TaskGroupDto>
{
    private String name;
    private TaskGroupStatus status;
    private LocalDate expirationDate;
    private UserRef publisher;
    private BoardRef board;
    @AmountReference(name = "tasks")
    private Integer amountOfTasks;
}

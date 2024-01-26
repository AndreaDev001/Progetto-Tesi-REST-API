package com.progettotirocinio.restapi.data.dto.output;


import com.progettotirocinio.restapi.data.dto.annotations.AmountReference;
import com.progettotirocinio.restapi.data.dto.output.refs.TaskGroupRef;
import com.progettotirocinio.restapi.data.dto.output.refs.UserRef;
import com.progettotirocinio.restapi.data.entities.Task;
import com.progettotirocinio.restapi.data.entities.enums.Priority;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.server.core.Relation;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Relation(collectionRelation = "content")
public class TaskDto extends GenericOutput<TaskDto>
{
    private String title;
    private String name;
    private String description;
    private Priority priority;
    private UserRef publisher;
    private TaskGroupRef taskGroup;
    @AmountReference(name = "receivedLikes")
    private Integer amountOfLikes;
}

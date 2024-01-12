package com.progettotirocinio.restapi.data.dto.output;


import com.progettotirocinio.restapi.data.dto.output.refs.BoardRef;
import com.progettotirocinio.restapi.data.dto.output.refs.UserRef;
import com.progettotirocinio.restapi.data.entities.TaskGroup;
import lombok.*;
import org.springframework.hateoas.server.core.Relation;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Relation(collectionRelation = "content")
public class TaskGroupDto extends GenericOutput<TaskGroupDto>
{
    private String name;
    private UserRef publisher;
    private BoardRef board;
}

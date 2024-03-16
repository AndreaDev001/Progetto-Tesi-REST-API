package com.progettotirocinio.restapi.data.dto.output;

import com.progettotirocinio.restapi.data.dto.output.refs.BoardMemberRef;
import com.progettotirocinio.restapi.data.dto.output.refs.TaskRef;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.server.core.Relation;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Relation(collectionRelation = "content")
public class TaskURLDto extends GenericOutput<TaskURLDto>
{
    private String name;
    private String url;
    private BoardMemberRef publisher;
    private TaskRef task;
}

package com.progettotirocinio.restapi.data.dto.output;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.progettotirocinio.restapi.data.dto.output.refs.BoardMemberRef;
import com.progettotirocinio.restapi.data.dto.output.refs.TaskRef;
import com.progettotirocinio.restapi.data.entities.BoardMember;
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
public class TaskFileDto extends GenericOutput<TaskFileDto>
{
    private String name;
    private String fileName;
    private String type;
    private String extension;
    private TaskRef task;
    private BoardMemberRef publisher;
}

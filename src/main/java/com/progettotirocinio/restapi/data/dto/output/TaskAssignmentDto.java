package com.progettotirocinio.restapi.data.dto.output;

import com.progettotirocinio.restapi.data.dto.output.refs.BoardMemberRef;
import com.progettotirocinio.restapi.data.dto.output.refs.TaskRef;
import com.progettotirocinio.restapi.data.dto.output.refs.UserRef;
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
public class TaskAssignmentDto extends GenericOutput<TaskAssignmentDto>
{
    public BoardMemberRef member;
    public TaskRef task;
    public UserRef publisher;
}

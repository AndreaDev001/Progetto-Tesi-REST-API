package com.progettotirocinio.restapi.data.dto.output.tags;

import com.progettotirocinio.restapi.data.dto.output.GenericOutput;
import com.progettotirocinio.restapi.data.dto.output.refs.TagRef;
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
public class TagAssignmentDto extends GenericOutput<TagAssignmentDto>
{
    private TagRef tag;
    private TaskRef task;
}

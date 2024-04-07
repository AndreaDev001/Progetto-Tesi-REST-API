package com.progettotirocinio.restapi.data.dto.output.checklist;

import com.progettotirocinio.restapi.data.dto.output.GenericOutput;
import com.progettotirocinio.restapi.data.dto.output.refs.CheckListRef;
import com.progettotirocinio.restapi.data.dto.output.refs.UserRef;
import lombok.*;
import org.springframework.hateoas.server.core.Relation;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Relation(collectionRelation = "content")
public class CheckListOptionDto extends GenericOutput<CheckListOptionDto>
{
    private String name;
    private boolean completed;
    private CheckListRef checkList;
    private UserRef publisher;
}

package com.progettotirocinio.restapi.data.dto.output.checklist;

import com.progettotirocinio.restapi.data.dto.annotations.AmountReference;
import com.progettotirocinio.restapi.data.dto.output.GenericOutput;
import com.progettotirocinio.restapi.data.dto.output.refs.TaskGroupRef;
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
public class CheckListDto extends GenericOutput<CheckListDto> {
    private String name;
    private TaskGroupRef taskGroup;
    private UserRef publisher;
    @AmountReference(name = "options")
    private Integer amountOfOptions;
}

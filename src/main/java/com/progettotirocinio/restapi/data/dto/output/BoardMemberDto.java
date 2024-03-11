package com.progettotirocinio.restapi.data.dto.output;


import com.progettotirocinio.restapi.data.dto.annotations.AmountReference;
import com.progettotirocinio.restapi.data.dto.output.refs.BoardRef;
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
public class BoardMemberDto extends GenericOutput<BoardMemberDto>
{
    private UserRef user;
    private BoardRef board;
    @AmountReference(name = "assignedTasks")
    private Integer amountOfAssignedTasks;
}

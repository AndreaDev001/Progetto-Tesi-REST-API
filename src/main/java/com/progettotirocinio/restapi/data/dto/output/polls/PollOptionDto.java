package com.progettotirocinio.restapi.data.dto.output.polls;

import com.progettotirocinio.restapi.data.dto.annotations.AmountReference;
import com.progettotirocinio.restapi.data.dto.output.GenericOutput;
import com.progettotirocinio.restapi.data.dto.output.refs.PollRef;
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
public class PollOptionDto extends GenericOutput<PollOptionDto>
{
    private String name;
    private PollRef poll;
    @AmountReference(name = "receivedVotes")
    private Integer amountOfVotes;
}

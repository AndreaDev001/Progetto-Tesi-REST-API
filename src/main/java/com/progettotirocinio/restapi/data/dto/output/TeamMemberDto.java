package com.progettotirocinio.restapi.data.dto.output;


import com.progettotirocinio.restapi.data.dto.output.refs.TeamRef;
import com.progettotirocinio.restapi.data.dto.output.refs.UserRef;
import com.progettotirocinio.restapi.data.entities.Team;
import lombok.*;
import org.springframework.hateoas.server.core.Relation;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Relation(collectionRelation = "content")
public class TeamMemberDto extends GenericOutput<TeamMemberDto>
{
    private UserRef member;
    private TeamRef team;
}

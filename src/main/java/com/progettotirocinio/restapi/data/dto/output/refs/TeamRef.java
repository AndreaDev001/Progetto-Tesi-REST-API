package com.progettotirocinio.restapi.data.dto.output.refs;


import com.progettotirocinio.restapi.data.dto.output.GenericOutput;
import com.progettotirocinio.restapi.data.entities.Team;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TeamRef extends GenericOutput<TeamRef> {
    private String name;
    private BoardRef board;

    public TeamRef(Team team) {
        this.id = team.getId();
        this.name = team.getName();
        this.createdDate = team.getCreatedDate();
    }
}

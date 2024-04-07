package com.progettotirocinio.restapi.data.dto.output.refs;


import com.progettotirocinio.restapi.data.dto.output.GenericOutput;
import com.progettotirocinio.restapi.data.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class RoleRef extends GenericOutput<RoleRef>
{
    private String name;
    private BoardRef board;

    public RoleRef(Role role) {
        this.id = role.getId();
        this.name = role.getName();
        this.createdDate = role.getCreatedDate();
    }
}

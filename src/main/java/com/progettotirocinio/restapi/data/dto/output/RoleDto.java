package com.progettotirocinio.restapi.data.dto.output;


import com.progettotirocinio.restapi.controllers.PermissionController;
import com.progettotirocinio.restapi.data.dto.input.PaginationRequest;
import com.progettotirocinio.restapi.data.dto.output.refs.BoardRef;
import com.progettotirocinio.restapi.data.dto.output.refs.RoleRef;
import com.progettotirocinio.restapi.data.dto.output.refs.UserRef;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class RoleDto extends GenericOutput<RoleDto>
{
    private String name;
    private UserRef publisher;
    private BoardRef board;
    private Integer amountOfPermissions;

    @Override
    public void addLinks(Object... params) {
        PaginationRequest paginationRequest = new PaginationRequest(0,20);
        this.add(linkTo(methodOn(PermissionController.class).getPermissionsByRole(this.id,paginationRequest)).withRel("permissions").withName("permissions"));
    }
}

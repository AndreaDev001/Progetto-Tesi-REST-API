package com.progettotirocinio.restapi.data.dto.output;


import com.progettotirocinio.restapi.controllers.RoleController;
import com.progettotirocinio.restapi.controllers.TagController;
import com.progettotirocinio.restapi.controllers.TaskGroupController;
import com.progettotirocinio.restapi.data.dto.input.PaginationRequest;
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
public class BoardDto extends GenericOutput<BoardDto>
{
    private String title;
    private String description;
    private Integer minMembers;
    private Integer maxMembers;
    private LocalDate expirationDate;
    private Integer amountOfGroups;
    private Integer amountOfRoles;
    private Integer amountOfTags;

    @Override
    public void addLinks(Object... params) {
        PaginationRequest paginationRequest = new PaginationRequest(0,20);
        this.add(linkTo(methodOn(TaskGroupController.class).getTaskGroupsByPublisher(this.id,paginationRequest)).withRel("taskGroups").withName("taskGroups"));
        this.add(linkTo(methodOn(RoleController.class).getRoles(paginationRequest)).withRel("roles").withName("roles"));
        this.add(linkTo(methodOn(TagController.class).getTagsByBoard(this.id,paginationRequest)).withRel("tags").withName("tags"));
    }
}

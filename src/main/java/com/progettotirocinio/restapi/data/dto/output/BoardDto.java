package com.progettotirocinio.restapi.data.dto.output;


import com.progettotirocinio.restapi.controllers.RoleController;
import com.progettotirocinio.restapi.controllers.TagController;
import com.progettotirocinio.restapi.controllers.TaskGroupController;
import com.progettotirocinio.restapi.data.dto.annotations.AmountReference;
import com.progettotirocinio.restapi.data.dto.input.PaginationRequest;
import com.progettotirocinio.restapi.data.entities.enums.BoardVisibility;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDate;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Relation(collectionRelation = "content")
public class BoardDto extends GenericOutput<BoardDto>
{
    private String title;
    private String description;
    private Integer minMembers;
    private Integer maxMembers;
    private LocalDate expirationDate;
    private BoardVisibility visibility;
    @AmountReference(name = "groups")
    private Integer amountOfGroups;
    @AmountReference(name = "roles")
    private Integer amountOfRoles;
    @AmountReference(name = "tags")
    private Integer amountOfTags;

    @Override
    public void addLinks(Object... params) {
        PaginationRequest paginationRequest = new PaginationRequest(0,20);
        this.add(linkTo(methodOn(TaskGroupController.class).getTaskGroupsByPublisher(this.id,paginationRequest)).slash(paginationRequest.toString()).withRel("taskGroups").withName("taskGroups"));
        this.add(linkTo(methodOn(RoleController.class).getRoles(paginationRequest)).slash(paginationRequest.toString()).withRel("roles").withName("roles"));
        this.add(linkTo(methodOn(TagController.class).getTagsByBoard(this.id,paginationRequest)).slash(paginationRequest.toString()).withRel("tags").withName("tags"));
    }
}

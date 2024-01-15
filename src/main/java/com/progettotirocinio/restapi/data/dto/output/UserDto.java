package com.progettotirocinio.restapi.data.dto.output;


import com.progettotirocinio.restapi.controllers.*;
import com.progettotirocinio.restapi.data.dto.annotations.AmountReference;
import com.progettotirocinio.restapi.data.dto.input.PaginationRequest;
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
public class UserDto extends GenericOutput<UserDto>
{
    private String email;
    private String username;
    private String name;
    private String surname;
    @AmountReference(name = "createdBoards")
    private Integer amountOfCreatedBoards;
    @AmountReference(name = "createdTasks")
    private Integer amountOfCreatedTasks;
    @AmountReference(name = "createdTaskGroups")
    private Integer amountOfCreatedTaskGroups;
    @AmountReference(name = "createdPolls")
    private Integer amountOfCreatedPolls;
    @AmountReference(name = "createdTags")
    private Integer amountOfCreatedTags;
    @AmountReference(name = "createdRoles")
    private Integer amountOfCreatedRoles;
    @AmountReference(name = "rolesOwned")
    private Integer amountOfOwnedRoles;

    @Override
    public void addLinks(Object... params) {
        PaginationRequest paginationRequest = new PaginationRequest(0,20);
        this.add(linkTo(methodOn(BoardController.class).getBoardsByPublisher(this.id,paginationRequest)).slash(paginationRequest.toString()).withRel("createdBoards").withName("createdBoards"));
        this.add(linkTo(methodOn(TaskController.class).getTasksByPublisher(this.id,paginationRequest)).slash(paginationRequest.toString()).withRel("createdTasks").withName("createdTasks"));
        this.add(linkTo(methodOn(TaskGroupController.class).getTaskGroupsByPublisher(this.id,paginationRequest)).slash(paginationRequest.toString()).withRel("createdTaskGroups").withName("createdTaskGroups"));
        this.add(linkTo(methodOn(CommentController.class).getCommentsByPublisher(this.id,paginationRequest)).slash(paginationRequest.toString()).withRel("createdComments").withName("createdComments"));
        this.add(linkTo(methodOn(DiscussionController.class).getDiscussionsByPublisher(this.id,paginationRequest)).slash(paginationRequest.toString()).withRel("createdDiscussions").withName("createdDiscussions"));
        this.add(linkTo(methodOn(PollController.class).getPollsByPublisher(this.id,paginationRequest)).slash(paginationRequest.toString()).withRel("createdPolls").withName("createdPolls"));
        this.add(linkTo(methodOn(TagController.class).getTagsByPublisher(this.id,paginationRequest)).slash(paginationRequest.toString()).withRel("createdTags").withName("createdTags"));
        this.add(linkTo(methodOn(RoleOwnerController.class).getRoleOwnersByOwner(this.id,paginationRequest)).slash(paginationRequest.toString()).withRel("rolesOwned").withName("rolesOwned"));
        this.add(linkTo(methodOn(BoardInviteController.class).getBoardInvitesByPublisher(this.id,paginationRequest)).slash(paginationRequest.toString()).withRel("createdInvites").withName("createdInvites"));
    }
}

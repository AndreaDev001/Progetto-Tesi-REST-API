package com.progettotirocinio.restapi.data.dto.output;


import com.progettotirocinio.restapi.controllers.*;
import com.progettotirocinio.restapi.controllers.bans.BanController;
import com.progettotirocinio.restapi.controllers.likes.CommentLikeController;
import com.progettotirocinio.restapi.controllers.likes.DiscussionLikeController;
import com.progettotirocinio.restapi.controllers.likes.PollLikeController;
import com.progettotirocinio.restapi.controllers.likes.TaskLikeController;
import com.progettotirocinio.restapi.controllers.reports.ReportController;
import com.progettotirocinio.restapi.data.dto.annotations.AmountReference;
import com.progettotirocinio.restapi.data.dto.input.PaginationRequest;
import com.progettotirocinio.restapi.data.entities.enums.Gender;
import com.progettotirocinio.restapi.data.entities.enums.UserVisibility;
import com.progettotirocinio.restapi.data.entities.reports.Report;
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
    private Gender gender;
    private UserVisibility visibility;
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
    @AmountReference(name = "joinedBoards")
    private Integer amountOfJoinedBoards;
    @AmountReference(name = "likedTasks")
    private Integer amountOfCreatedLikes;
    @AmountReference(name = "createdReports")
    private Integer amountOfCreatedReports;
    @AmountReference(name = "receivedReports")
    private Integer amountOfReceivedReports;
    @AmountReference(name = "createdBans")
    private Integer amountOfCreatedBans;
    @AmountReference(name = "receivedBans")
    private Integer amountOfReceivedBans;
    @AmountReference(name = "assignedTasks")
    private Integer assignedTasks;
    @AmountReference(name = "createdAssignments")
    private Integer amountOfCreatedAssignments;
    @AmountReference(name = "createdVotes")
    private Integer amountOfCreatedVotes;

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
        this.add(linkTo(methodOn(BoardMemberController.class).getBoardMembersByUser(this.id,paginationRequest)).slash(paginationRequest.toString()).withRel("boardMembers").withName("boardMembers"));
        this.add(linkTo(methodOn(TaskLikeController.class).getTaskLikesByUser(this.id,paginationRequest)).slash(paginationRequest.toString()).withRel("likedTasks").withName("likedTasks"));
        this.add(linkTo(methodOn(CommentLikeController.class).getCommentLikesByUser(this.id,paginationRequest)).slash(paginationRequest.toString()).withRel("likedComments").withName("likedComments"));
        this.add(linkTo(methodOn(DiscussionLikeController.class).getDiscussionLikesByUser(this.id,paginationRequest)).slash(paginationRequest.toString()).withRel("likedDiscussions").withName("likedDiscussions"));
        this.add(linkTo(methodOn(PollLikeController.class).getPollLikesByUser(this.id,paginationRequest)).slash(paginationRequest.toString()).withRel("likedPolls").withName("likedPolls"));
        this.add(linkTo(methodOn(ReportController.class).getReportsByReporter(this.id,paginationRequest)).slash(paginationRequest.toString()).withRel("createdReports").withName("createdReports"));
        this.add(linkTo(methodOn(ReportController.class).getReportsByReported(this.id,paginationRequest)).slash(paginationRequest.toString()).withRel("receivedReports").withName("receivedReports"));
        this.add(linkTo(methodOn(BanController.class).getBansByBanner(this.id,paginationRequest)).slash(paginationRequest.toString()).withRel("createdBans").withName("createdBans"));
        this.add(linkTo(methodOn(BanController.class).getBansByBanned(this.id,paginationRequest)).slash(paginationRequest.toString()).withRel("receivedBans").withName("receivedBans"));
    }
}

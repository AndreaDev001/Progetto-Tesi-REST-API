package com.progettotirocinio.restapi.data.dto.output;


import com.progettotirocinio.restapi.controllers.TaskAssignmentController;
import com.progettotirocinio.restapi.controllers.checklist.CheckListController;
import com.progettotirocinio.restapi.controllers.comments.CommentTaskController;
import com.progettotirocinio.restapi.controllers.images.TaskImageController;
import com.progettotirocinio.restapi.controllers.likes.TaskLikeController;
import com.progettotirocinio.restapi.controllers.tags.TagAssignmentController;
import com.progettotirocinio.restapi.data.dto.annotations.AmountReference;
import com.progettotirocinio.restapi.data.dto.input.PaginationRequest;
import com.progettotirocinio.restapi.data.dto.output.refs.UserRef;
import com.progettotirocinio.restapi.data.entities.enums.Priority;
import com.progettotirocinio.restapi.data.entities.enums.TaskStatus;
import com.progettotirocinio.restapi.data.entities.tags.TagAssignment;
import lombok.*;
import org.springframework.hateoas.server.core.Relation;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Relation(collectionRelation = "content")
public class TaskDto extends GenericOutput<TaskDto>
{
    private String title;
    private String name;
    private String description;
    private Priority priority;
    private TaskStatus status;
    private UserRef publisher;
    private Integer currentOrder;
    @AmountReference(name = "receivedLikes")
    private Integer amountOfReceivedLikes;
    @AmountReference(name = "assignments")
    private Integer amountOfAssignments;
    @AmountReference(name = "receivedTags")
    private Integer amountOfTags;
    @AmountReference(name = "receivedComments")
    private Integer amountOfReceivedComments;
    @AmountReference(name = "taskImages")
    private Integer amountOfImages;
    @AmountReference(name = "checkLists")
    private Integer amountOfCheckLists;
    @AmountReference(name = "taskURLS")
    private Integer amountOfURLs;

    @Override
    public void addLinks(Object... params) {
        PaginationRequest paginationRequest = new PaginationRequest(0,20);
        this.add(linkTo(methodOn(TaskLikeController.class).getTaskLikesByTask(this.id,paginationRequest)).slash(paginationRequest.toString()).withRel("receivedLikes").withName("receivedLikes"));
        this.add(linkTo(methodOn(TaskAssignmentController.class).getTaskAssignmentsByTask(this.id)).withRel("assignments").withName("assignments"));
        this.add(linkTo(methodOn(TagAssignmentController.class).getTagAssignmentsByTag(this.id)).withRel("tags").withName("tags"));
        this.add(linkTo(methodOn(TaskImageController.class).getTaskImages(this.id)).withRel("images").withName("images"));
        this.add(linkTo(methodOn(CommentTaskController.class).getCommentsByTask(this.id)).withRel("receivedComments").withName("receivedComments"));
        this.add(linkTo(methodOn(CheckListController.class).getCheckListsByTask(this.id)).withRel("checklists").withName("checklists"));
    }
}

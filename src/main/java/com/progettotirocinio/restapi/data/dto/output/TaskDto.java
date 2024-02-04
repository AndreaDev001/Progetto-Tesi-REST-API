package com.progettotirocinio.restapi.data.dto.output;


import com.progettotirocinio.restapi.controllers.likes.TaskLikeController;
import com.progettotirocinio.restapi.data.dto.annotations.AmountReference;
import com.progettotirocinio.restapi.data.dto.input.PaginationRequest;
import com.progettotirocinio.restapi.data.dto.output.refs.TaskGroupRef;
import com.progettotirocinio.restapi.data.dto.output.refs.UserRef;
import com.progettotirocinio.restapi.data.entities.Task;
import com.progettotirocinio.restapi.data.entities.enums.Priority;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.server.core.Relation;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Relation(collectionRelation = "content")
public class TaskDto extends GenericOutput<TaskDto>
{
    private String title;
    private String name;
    private String description;
    private Priority priority;
    private UserRef publisher;
    private TaskGroupRef taskGroup;
    @AmountReference(name = "receivedLikes")
    private Integer amountOfLikes;
    @AmountReference(name = "assignments")
    private Integer amountOfAssignments;

    @Override
    public void addLinks(Object... params) {
        PaginationRequest paginationRequest = new PaginationRequest(0,20);
        this.add(linkTo(methodOn(TaskLikeController.class).getTaskLikesByTask(this.id,paginationRequest)).slash(paginationRequest.toString()).withRel("receivedLikes").withName("receivedLikes"));
    }
}

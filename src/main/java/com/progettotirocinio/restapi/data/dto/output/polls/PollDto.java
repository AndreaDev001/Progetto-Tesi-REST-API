package com.progettotirocinio.restapi.data.dto.output.polls;


import com.progettotirocinio.restapi.controllers.comments.CommentPollController;
import com.progettotirocinio.restapi.controllers.likes.PollLikeController;
import com.progettotirocinio.restapi.data.dto.annotations.AmountReference;
import com.progettotirocinio.restapi.data.dto.input.PaginationRequest;
import com.progettotirocinio.restapi.data.dto.output.GenericOutput;
import com.progettotirocinio.restapi.data.entities.enums.PollStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDate;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Relation(collectionRelation = "content")
public class PollDto extends GenericOutput<PollDto>
{
    private String title;
    private String description;
    private Integer minimumVotes;
    private Integer maximumVotes;
    private LocalDate expirationDate;
    private PollStatus status;
    @AmountReference(name = "receivedLikes")
    private Integer amountOfReceivedLikes;
    @AmountReference(name = "options")
    private Integer amountOfOptions;
    @AmountReference(name = "receivedComments")
    private Integer amountOfReceivedComments;

    @Override
    public void addLinks(Object... params) {
        PaginationRequest paginationRequest = new PaginationRequest(0,20);
        this.add(linkTo(methodOn(PollLikeController.class).getPollLikesByPoll(this.id,paginationRequest)).slash(paginationRequest.toString()).withRel("receivedLikes").withName("receivedLikes"));
        this.add(linkTo(methodOn(CommentPollController.class).getCommentsByPoll(this.id)).withRel("receivedComments").withName("receivedComments"));
    }
}

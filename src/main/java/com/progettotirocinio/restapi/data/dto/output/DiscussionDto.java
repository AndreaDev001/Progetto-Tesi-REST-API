package com.progettotirocinio.restapi.data.dto.output;


import com.progettotirocinio.restapi.controllers.comments.CommentController;
import com.progettotirocinio.restapi.controllers.comments.CommentDiscussionController;
import com.progettotirocinio.restapi.controllers.likes.DiscussionLikeController;
import com.progettotirocinio.restapi.data.dto.annotations.AmountReference;
import com.progettotirocinio.restapi.data.dto.input.PaginationRequest;
import com.progettotirocinio.restapi.data.dto.output.refs.UserRef;
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
public class DiscussionDto extends GenericOutput<DiscussionDto>
{
    private String title;
    private String topic;
    private String text;
    private UserRef publisher;
    private LocalDate expirationDate;
    @AmountReference(name = "receivedLikes")
    private Integer amountOfReceivedLikes;
    @AmountReference(name = "receivedComments")
    private Integer amountOfReceivedComments;

    @Override
    public void addLinks(Object... params) {
        PaginationRequest paginationRequest = new PaginationRequest(0,20);
        this.add(linkTo(methodOn(CommentDiscussionController.class).getCommentsByDiscussion(this.id)).withRel("comments").withName("comments"));
        this.add(linkTo(methodOn(DiscussionLikeController.class).getDiscussionLikesByDiscussion(this.id,paginationRequest)).withRel("receivedLikes").withName("receivedLikes"));
    }
}

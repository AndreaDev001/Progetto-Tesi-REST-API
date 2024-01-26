package com.progettotirocinio.restapi.data.dto.output;


import com.progettotirocinio.restapi.controllers.CommentController;
import com.progettotirocinio.restapi.data.dto.annotations.AmountReference;
import com.progettotirocinio.restapi.data.dto.input.PaginationRequest;
import com.progettotirocinio.restapi.data.dto.output.refs.UserRef;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.server.core.Relation;
import org.springframework.security.core.parameters.P;

import java.time.LocalDate;
import java.util.UUID;

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
    private UserRef publisher;
    private LocalDate expirationDate;
    @AmountReference(name = "comments")
    private Integer amountOfComments;
    @AmountReference(name = "receivedLikes")
    private Integer amountOfLikes;

    @Override
    public void addLinks(Object... params) {
        PaginationRequest paginationRequest = new PaginationRequest(0,20);
        this.add(linkTo(methodOn(CommentController.class).getCommentsByDiscussion(this.id,paginationRequest)).withRel("comments").withName("comments"));
    }
}

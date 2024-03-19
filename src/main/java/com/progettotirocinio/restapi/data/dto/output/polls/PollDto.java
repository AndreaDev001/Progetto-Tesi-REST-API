package com.progettotirocinio.restapi.data.dto.output.polls;


import com.progettotirocinio.restapi.controllers.comments.CommentPollController;
import com.progettotirocinio.restapi.controllers.likes.PollLikeController;
import com.progettotirocinio.restapi.data.dto.annotations.AmountReference;
import com.progettotirocinio.restapi.data.dto.input.PaginationRequest;
import com.progettotirocinio.restapi.data.dto.output.GenericOutput;
import com.progettotirocinio.restapi.data.dto.output.refs.UserRef;
import com.progettotirocinio.restapi.data.entities.enums.PollStatus;
import lombok.*;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDate;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Relation(collectionRelation = "content")
public class PollDto extends GenericOutput<PollDto>
{
    private String title;
    private String description;
    private Integer minimumVotes;
    private Integer maximumVotes;
    private LocalDate expirationDate;
    private UserRef publisher;
    private PollStatus status;
    @AmountReference(name = "receivedLikes")
    private Integer amountOfReceivedLikes;
    @AmountReference(name = "options")
    private Integer amountOfOptions;
    @AmountReference(name = "receivedComments")
    private Integer amountOfReceivedComments;

    @Override
    public void addLinks(Object... params) {

    }
}

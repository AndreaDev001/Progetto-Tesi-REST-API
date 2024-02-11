package com.progettotirocinio.restapi.data.dto.output;


import com.progettotirocinio.restapi.controllers.likes.PollLikeController;
import com.progettotirocinio.restapi.data.dto.annotations.AmountReference;
import com.progettotirocinio.restapi.data.dto.input.PaginationRequest;
import com.progettotirocinio.restapi.data.entities.enums.PollStatus;
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

    @Override
    public void addLinks(Object... params) {
        PaginationRequest paginationRequest = new PaginationRequest(0,20);
        this.add(linkTo(methodOn(PollLikeController.class).getPollLikesByPoll(this.id,paginationRequest)).slash(paginationRequest.toString()).withRel("receivedLikes").withName("receivedLikes"));
    }
}

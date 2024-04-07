package com.progettotirocinio.restapi.services.interfaces.comments;

import com.progettotirocinio.restapi.data.dto.input.create.CreateCommentDto;
import com.progettotirocinio.restapi.data.dto.output.comments.CommentPollDto;
import com.progettotirocinio.restapi.data.entities.comments.CommentPoll;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;

import java.util.UUID;

public interface CommentPollService
{
    PagedModel<CommentPollDto> getComments(Pageable pageable);
    CollectionModel<CommentPollDto> getCommentsByPoll(UUID pollID);
    CommentPollDto createCommentPoll(UUID pollID, CreateCommentDto createCommentDto);
    CommentPollDto getCommentPoll(UUID commentID);
    CommentPollDto getCommentByPollAndUser(UUID pollID,UUID userID);
    void deleteCommentPoll(UUID pollID);
}

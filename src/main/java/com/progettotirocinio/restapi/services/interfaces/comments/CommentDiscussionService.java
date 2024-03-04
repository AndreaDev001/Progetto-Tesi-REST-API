package com.progettotirocinio.restapi.services.interfaces.comments;

import com.progettotirocinio.restapi.data.dto.input.create.CreateCommentDto;
import com.progettotirocinio.restapi.data.dto.output.comments.CommentDiscussionDto;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;

import java.util.UUID;

public interface CommentDiscussionService
{
    PagedModel<CommentDiscussionDto> getComments(Pageable pageable);
    CollectionModel<CommentDiscussionDto> getCommentsByDiscussion(UUID discussionID);
    CommentDiscussionDto getCommentDiscussion(UUID commentID);
    CommentDiscussionDto createCommentDiscussion(UUID discussionID, CreateCommentDto createCommentDto);
    CommentDiscussionDto getCommentByDiscussionAndUser(UUID discussionID,UUID commentID);
    void deleteCommentDiscussion(UUID commentID);
}

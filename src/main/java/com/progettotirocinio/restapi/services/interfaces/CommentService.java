package com.progettotirocinio.restapi.services.interfaces;

import com.progettotirocinio.restapi.data.dto.input.create.CreateCommentDto;
import com.progettotirocinio.restapi.data.dto.input.update.UpdateCommentDto;
import com.progettotirocinio.restapi.data.dto.output.CommentDto;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import java.util.UUID;

public interface CommentService {
    PagedModel<CommentDto> getComments(Pageable pageable);
    PagedModel<CommentDto> getCommentsByPublisher(UUID publisherID,Pageable pageable);
    PagedModel<CommentDto> getCommentsByDiscussion(UUID discussionID,Pageable pageable);
    CommentDto getComment(UUID id);
    CommentDto createComment(CreateCommentDto createCommentDto);
    CommentDto updateComment(UpdateCommentDto updateCommentDto);
    void deleteComment(UUID id);
}

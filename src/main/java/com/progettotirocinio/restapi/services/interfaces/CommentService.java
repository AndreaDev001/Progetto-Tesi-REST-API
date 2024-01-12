package com.progettotirocinio.restapi.services.interfaces;

import com.progettotirocinio.restapi.data.dto.output.CommentDto;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import java.util.UUID;

public interface CommentService {
    PagedModel<CommentDto> getComments(Pageable pageable);
    PagedModel<CommentDto> getCommentsByPublisher(UUID publisherID,Pageable pageable);
    PagedModel<CommentDto> getCommentsByDiscussion(UUID discussionID,Pageable pageable);
    CommentDto getComment(UUID id);
    void deleteComment(UUID id);
}

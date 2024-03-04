package com.progettotirocinio.restapi.services.interfaces.comments;

import com.progettotirocinio.restapi.data.dto.output.comments.CommentDto;
import com.progettotirocinio.restapi.data.entities.enums.CommentType;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;

import java.util.UUID;

public interface CommentService
{
    PagedModel<CommentDto> getComments(Pageable pageable);
    PagedModel<CommentDto> getCommentsByTitle(String title,Pageable pageable);
    PagedModel<CommentDto> getCommentsByText(String text,Pageable pageable);
    PagedModel<CommentDto> getCommentsByPublisher(UUID publisherID,Pageable pageable);
    PagedModel<CommentDto> getCommentsByType(CommentType type,Pageable pageable);
    CommentDto getCommentByID(UUID commentID);
    CollectionModel<CommentType> getTypes();

    void deleteComment(UUID commentID);
}

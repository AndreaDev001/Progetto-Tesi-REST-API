package com.progettotirocinio.restapi.services.interfaces.likes;

import com.progettotirocinio.restapi.data.dto.output.likes.CommentLikeDto;
import com.progettotirocinio.restapi.data.entities.likes.CommentLike;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import java.util.UUID;

public interface CommentLikeService
{
    PagedModel<CommentLikeDto> getCommentLikes(Pageable pageable);
    PagedModel<CommentLikeDto> getCommentsLikesByUser(UUID userID, Pageable pageable);
    PagedModel<CommentLikeDto> getCommentsLikesByComment(UUID commentID,Pageable pageable);
    CommentLikeDto getCommentLike(UUID commentLikeID);
    CommentLikeDto hasLike(UUID userID,UUID likeID);
    void deleteCommentLike(UUID commentLikeID);
}

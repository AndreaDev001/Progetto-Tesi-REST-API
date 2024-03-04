package com.progettotirocinio.restapi.data.dao.likes;

import com.progettotirocinio.restapi.data.entities.likes.CommentLike;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CommentLikeDao extends JpaRepository<CommentLike, UUID>
{
    @Query("select c from CommentLike c where c.user.id = :requiredID")
    Page<CommentLike> getCommentLikesByUser(@Param("requiredID") UUID userID, Pageable pageable);
    @Query("select c from CommentLike c where c.comment.id = :requiredID")
    Page<CommentLike> getCommentLikesByComment(@Param("requiredID") UUID commentID,Pageable pageable);
    @Query("select c from CommentLike c where c.user.id = :requiredUserID and c.comment.id = :requiredCommentID")
    Optional<CommentLike> hasLike(@Param("requiredUserID") UUID userID, @Param("requiredCommentID") UUID commentID);
}

package com.progettotirocinio.restapi.data.dao.likes;

import com.progettotirocinio.restapi.data.entities.Comment;
import com.progettotirocinio.restapi.data.entities.likes.CommentLike;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CommentLikeDao extends JpaRepository<CommentLike, UUID> {
    @Query("select l from CommentLike l where l.comment.id = :requiredID")
    Page<CommentLike> getCommentLikes(@Param("requiredID") UUID commentID, Pageable pageable);
}

package com.progettotirocinio.restapi.data.dao.comments;

import com.progettotirocinio.restapi.data.entities.comments.Comment;
import com.progettotirocinio.restapi.data.entities.enums.CommentType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CommentDao extends JpaRepository<Comment, UUID>
{
    @Query("select c from Comment c where c.text = :requiredText")
    Page<Comment> getCommentsByText(@Param("requiredText") String text, Pageable pageable);
    @Query("select c from Comment c where c.title = :requiredTitle")
    Page<Comment> getCommentsByTitle(@Param("requiredTitle") String title,Pageable pageable);
    @Query("select c from Comment c where c.publisher.id = :requiredPublisherID")
    Page<Comment> getCommentsByPublisher(@Param("requiredPublisherID") UUID publisherID,Pageable pageable);
    @Query("select c from Comment c where c.type = :requiredType")
    Page<Comment> getCommentsByType(@Param("requiredType")CommentType type,Pageable pageable);
}

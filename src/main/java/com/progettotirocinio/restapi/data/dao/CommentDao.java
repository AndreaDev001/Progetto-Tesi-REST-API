package com.progettotirocinio.restapi.data.dao;

import com.progettotirocinio.restapi.data.entities.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CommentDao extends JpaRepository<Comment, UUID> {
    @Query("select c from Comment c where c.publisher.id = :requiredID")
    Page<Comment> getComments(@Param("requiredID") UUID userID, Pageable pageable);
    @Query("select c from Comment c where c.title = :requiredTitle")
    Page<Comment> getCommentsByTitle(@Param("requiredTitle") String title,Pageable pageable);
    @Query("select c from Comment c where c.publisher.id = :requiredID")
    Page<Comment> getCommentsByPublisher(@Param("requiredID") UUID publisherID,Pageable pageable);
    @Query("select c from Comment c where c.text = :requiredText")
    Page<Comment> getCommentsByText(@Param("requiredText") String text,Pageable pageable);
}

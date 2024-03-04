package com.progettotirocinio.restapi.data.dao.comments;

import com.progettotirocinio.restapi.data.entities.comments.CommentDiscussion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CommentDiscussionDao extends JpaRepository<CommentDiscussion, UUID>
{
    @Query("select c from CommentDiscussion c where c.discussion.id = :requiredID")
    List<CommentDiscussion> getCommentsByDiscussion(@Param("requiredID") UUID discussionID);
    @Query("select c from CommentDiscussion c where c.discussion.id = :requiredDiscussionID and c.publisher.id = :requiredPublisherID")
    Optional<CommentDiscussion> getCommentByDiscussionAndPublisher(@Param("requiredDiscussionID") UUID discussionID, @Param("requiredPublisherID") UUID publisherID);
}

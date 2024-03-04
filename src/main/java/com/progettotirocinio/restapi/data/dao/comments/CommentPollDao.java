package com.progettotirocinio.restapi.data.dao.comments;

import com.progettotirocinio.restapi.data.entities.comments.CommentPoll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CommentPollDao extends JpaRepository<CommentPoll, UUID>
{
    @Query("select c from CommentPoll c where c.poll.id = :requiredID")
    List<CommentPoll> getCommentsByPoll(@Param("requiredID") UUID pollID);
    @Query("select c from CommentPoll c where c.poll.id = :requiredPollID and c.publisher.id = :requiredPublisherID")
    Optional<CommentPoll> getCommentPollBy(@Param("requiredPollID") UUID pollID,@Param("requiredPublisherID") UUID publisherID);
}

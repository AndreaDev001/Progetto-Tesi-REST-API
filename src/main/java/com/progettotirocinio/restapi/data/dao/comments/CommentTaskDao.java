package com.progettotirocinio.restapi.data.dao.comments;

import com.progettotirocinio.restapi.data.entities.Task;
import com.progettotirocinio.restapi.data.entities.comments.CommentTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CommentTaskDao extends JpaRepository<CommentTask, UUID>
{
    @Query("select c from CommentTask c where c.task.id = :requiredTaskID")
    List<CommentTask> getCommentsByTask(@Param("requiredTaskID") UUID taskID);
    @Query("select c from CommentTask c where c.task.id = :requiredTaskID and c.publisher.id = :requiredPublisherID")
    Optional<CommentTask> getCommentByTaskAndPublisher(@Param("requiredTaskID") UUID taskID,@Param("requiredPublisherID") UUID publisherID);
}

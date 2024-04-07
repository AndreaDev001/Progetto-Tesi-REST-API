package com.progettotirocinio.restapi.data.dao.likes;

import com.progettotirocinio.restapi.data.entities.likes.TaskLike;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.Optional;

@Repository
public interface TaskLikeDao extends JpaRepository<TaskLike, UUID>
{
    @Query("select t from TaskLike t where t.user.id = :requiredID")
    Page<TaskLike> getTaskLikesByUser(@Param("requiredID") UUID userID, Pageable pageable);
    @Query("select t from TaskLike t where t.task.id = :requiredID")
    Page<TaskLike> getTaskLikesByTask(@Param("requiredID") UUID taskID,Pageable pageable);
    @Query("select t from TaskLike t where t.task.id = :requiredTaskID and t.user.id = :requiredUserID")
    Optional<TaskLike> hasLike(@Param("requiredTaskID") UUID taskID,@Param("requiredUserID") UUID userID);

}

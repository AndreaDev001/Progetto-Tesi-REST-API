package com.progettotirocinio.restapi.data.dao.likes;

import com.progettotirocinio.restapi.data.entities.Task;
import com.progettotirocinio.restapi.data.entities.likes.TaskLike;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TaskLikeDao extends JpaRepository<TaskLike, UUID>
{
    @Query("select l from TaskLike l where l.task.id = :requiredID")
    Page<TaskLike> getTaskLikes(@Param("requiredID") UUID taskID, Pageable pageable);
}

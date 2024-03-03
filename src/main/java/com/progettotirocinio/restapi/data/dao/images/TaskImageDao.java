package com.progettotirocinio.restapi.data.dao.images;


import com.progettotirocinio.restapi.data.entities.Task;
import com.progettotirocinio.restapi.data.entities.images.TaskImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TaskImageDao extends JpaRepository<TaskImage, UUID>
{
    @Query("select t from TaskImage t where t.task.id = :requiredID")
    List<TaskImage> getTaskImages(@Param("requiredID") UUID taskID);
}

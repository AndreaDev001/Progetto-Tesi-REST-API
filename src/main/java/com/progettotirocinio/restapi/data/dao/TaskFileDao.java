package com.progettotirocinio.restapi.data.dao;

import com.progettotirocinio.restapi.data.entities.TaskFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TaskFileDao extends JpaRepository<TaskFile, UUID>
{
    @Query("select t from TaskFile t where t.publisher.id = :requiredID")
    Page<TaskFile> getTaskFilesByPublisher(@Param("requiredID") UUID publisherID, Pageable pageable);
    @Query("select t from TaskFile t where t.name = :requiredName")
    Page<TaskFile> getTaskFilesByName(@Param("requiredName") String name,Pageable pageable);
    @Query("select t from TaskFile t where t.task.id = :requiredID")
    List<TaskFile> getTaskFilesByTask(@Param("requiredID") UUID taskID);
}

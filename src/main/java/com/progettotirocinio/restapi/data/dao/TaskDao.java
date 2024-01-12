package com.progettotirocinio.restapi.data.dao;


import com.progettotirocinio.restapi.data.entities.Task;
import com.progettotirocinio.restapi.data.entities.enums.Priority;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TaskDao extends JpaRepository<Task, UUID> {
    @Query("select t from Task t where t.publisher.id = :requiredPublisherID and t.group.id = :requiredGroupID")
    Page<Task> getTasksByPublisherAndGroup(@Param("requiredPublisherID") UUID publisherID, @Param("requiredGroupID") UUID groupID, Pageable pageable);
    @Query("select t from Task t where t.name = :requiredName")
    Page<Task> getTasksByName(@Param("requiredName") String name,Pageable pageable);
    @Query("select t from Task t where t.title = :requiredTitle")
    Page<Task> getTasksByTitle(@Param("requiredTitle") String title,Pageable pageable);
    @Query("select t from Task t where t.description = :requiredDescription")
    Page<Task> getTasksByDescription(@Param("requiredDescription") String description,Pageable pageable);
    @Query("select t from Task t where t.publisher.id = :requiredPublisherID")
    Page<Task> getTasksByPublisher(@Param("requiredPublisherID") UUID publisherID,Pageable pageable);
    @Query("select  t from Task t where t.group.id = :requiredGroupID")
    Page<Task> getTasksByGroup(@Param("requiredGroupID") UUID groupID,Pageable pageable);
    @Query("select t from Task t where t.priority = :requiredPriority")
    Page<Task> getTasksByPriority(@Param("requiredPriority") Priority priority,Pageable pageable);
}

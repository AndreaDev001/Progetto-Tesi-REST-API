package com.progettotirocinio.restapi.data.dao;


import com.progettotirocinio.restapi.data.entities.Task;
import com.progettotirocinio.restapi.data.entities.enums.Priority;
import com.progettotirocinio.restapi.data.entities.enums.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface TaskDao extends JpaRepository<Task, UUID>, JpaSpecificationExecutor<Task> {
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
    @Query("select  t from Task t where t.group.id = :requiredGroupID order by t.currentOrder asc")
    List<Task> getTasksByGroup(@Param("requiredGroupID") UUID groupID);
    @Query("select t from Task t where t.priority = :requiredPriority")
    Page<Task> getTasksByPriority(@Param("requiredPriority") Priority priority,Pageable pageable);
    @Query("select t from Task t where :requiredDate > t.expirationDate")
    List<Task> getExpiredTasks(@Param("requiredDate") LocalDate date);
    @Query("select t from Task t where t.status = :requiredStatus")
    List<Task> getTasksByStatus(@Param("requiredStatus") TaskStatus status);
    @Query("select t from Task t where t.status = :requiredStatus")
    Page<Task> getTasksByStatus(@Param("requiredStatus")TaskStatus status,Pageable pageable);

    @Query("select max(t.currentOrder) from Task t where t.group.id = :requiredGroupID")
    Integer getMaxOrderInGroup(@Param("requiredGroupID") UUID groupID);
}

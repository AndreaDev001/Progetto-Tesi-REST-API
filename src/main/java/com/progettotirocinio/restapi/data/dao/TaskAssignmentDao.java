package com.progettotirocinio.restapi.data.dao;

import com.progettotirocinio.restapi.data.entities.TaskAssignment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TaskAssignmentDao extends JpaRepository<TaskAssignment, UUID> {
    @Query("select t from TaskAssigment t where t.publisher.id = :requiredID")
    Page<TaskAssignment> getTaskAssigmentsByPublisher(@Param("requiredID") UUID publisherID, Pageable pageable);
    @Query("select t from TaskAssigment t where t.user.id = :requiredID")
    Page<TaskAssignment> getTaskAssigmentsByUser(@Param("requiredID") UUID userID, Pageable pageable);
    @Query("select t from TaskAssigment t where t.task.id = :requiredID")
    Page<TaskAssignment> getTaskAssigmentsByTask(@Param("requiredID") UUID taskID, Pageable pageable);
    @Query("select t from TaskAssigment t where t.user.id = :requiredUserID and t.task.id = :requiredTaskID")
    Optional<TaskAssignment> getTaskAssigment(@Param("requiredUserID") UUID userID, @Param("requiredTaskID") UUID taskID);
}

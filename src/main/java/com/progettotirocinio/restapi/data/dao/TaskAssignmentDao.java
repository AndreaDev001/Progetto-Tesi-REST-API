package com.progettotirocinio.restapi.data.dao;

import com.progettotirocinio.restapi.data.entities.TaskAssignment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TaskAssignmentDao extends JpaRepository<TaskAssignment, UUID> {
    @Query("select t from TaskAssignment t where t.publisher.id = :requiredID")
    Page<TaskAssignment> getTaskAssignmentsByPublisher(@Param("requiredID") UUID publisherID, Pageable pageable);
    @Query("select t from TaskAssignment t where t.member.user.id = :requiredID")
    Page<TaskAssignment> getTaskAssignmentsByMember(@Param("requiredID") UUID memberID, Pageable pageable);
    @Query("select t from TaskAssignment t where t.task.id = :requiredID")
    List<TaskAssignment> getTaskAssignmentsByTask(@Param("requiredID") UUID taskID);
    @Query("select t from TaskAssignment t where t.member.user.id = :requiredMemberID and t.task.id = :requiredTaskID")
    Optional<TaskAssignment> getTaskAssignment(@Param("requiredMemberID") UUID memberID, @Param("requiredTaskID") UUID taskID);
}

package com.progettotirocinio.restapi.data.dao;

import com.progettotirocinio.restapi.data.entities.TaskGroup;
import com.progettotirocinio.restapi.data.entities.enums.TaskGroupStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;




@Repository
public interface TaskGroupDao extends JpaRepository<TaskGroup, UUID> {
    @Query("select t from TaskGroup t where t.publisher.id = :requiredPublisherID")
    Page<TaskGroup> getTaskGroupsByPublisher(@Param("requiredPublisherID") UUID publisherID, Pageable pageable);
    @Query("select t from TaskGroup t where t.name = :requiredName")
    Page<TaskGroup> getTaskGroupsByName(@Param("requiredName") String name,Pageable pageable);
    @Query("select t from TaskGroup t where t.board.id = :requiredBoardID")
    Page<TaskGroup> getTaskGroupsByBoard(@Param("requiredBoardID") UUID boardID,Pageable pageable);
    @Query("select t from TaskGroup t where t.status = :requiredStatus")
    Page<TaskGroup> getTaskGroupsByStatus(@Param("requiredStatus")TaskGroupStatus status,Pageable pageable);
    @Query("select t from TaskGroup t where t.status = :requiredStatus")
    List<TaskGroup> getTaskGroupsByStatus(@Param("requiredStatus") TaskGroupStatus status);
    @Query("select t from TaskGroup t where t.expirationDate > :requiredDate")
    List<TaskGroup> getTaskGroupsByDate(@Param("requiredDate")LocalDate date);
}

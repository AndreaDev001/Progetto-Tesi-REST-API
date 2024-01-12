package com.progettotirocinio.restapi.data.dao;

import com.progettotirocinio.restapi.data.entities.TaskGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;




@Repository
public interface TaskGroupDao extends JpaRepository<TaskGroup, UUID> {
    @Query("select t from TaskGroup t where t.publisher.id = :requiredPublisherID")
    Page<TaskGroup> getTaskGroupsByPublisher(@Param("requiredPublisherID") UUID publisherID, Pageable pageable);
    @Query("select t from TaskGroup t where t.name = :requiredName")
    Page<TaskGroup> getTaskGroupsByName(@Param("requiredName") String name,Pageable pageable);
    @Query("select t from TaskGroup t where t.board.id = :requiredBoardID")
    Page<TaskGroup> getTaskGroupsByBoard(@Param("requiredBoardID") UUID boardID,Pageable pageable);
}

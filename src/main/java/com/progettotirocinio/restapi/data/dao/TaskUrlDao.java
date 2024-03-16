package com.progettotirocinio.restapi.data.dao;

import com.progettotirocinio.restapi.data.entities.TaskURL;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TaskUrlDao extends JpaRepository<TaskURL, UUID>
{
    @Query("select t from TaskURL t where t.publisher.id = :requiredID")
    Page<TaskURL> getTaskURLsFromPublisher(@Param("requiredID") UUID publisherID, Pageable pageable);
    @Query("select t from TaskURL t where t.task.id = :requiredID")
    List<TaskURL> getTaskURLsFromTask(@Param("requiredID") UUID taskID);
    @Query("select t from TaskURL t where t.url = :requiredURL")
    Page<TaskURL> getTaskURLsFromURL(@Param("requiredURL") String url,Pageable pageable);

}

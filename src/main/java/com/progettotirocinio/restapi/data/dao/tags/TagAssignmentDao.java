package com.progettotirocinio.restapi.data.dao.tags;

import com.progettotirocinio.restapi.data.entities.tags.TagAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TagAssignmentDao extends JpaRepository<TagAssignment, UUID>
{
    @Query("select t from TagAssignment t where t.task.id = :requiredID")
    List<TagAssignment> getTagAssignmentsByTask(@Param("requiredID") UUID taskID);
    @Query("select t from TagAssignment t where t.tag.id = :requiredID")
    List<TagAssignment> getTagAssignmentsByTag(@Param("requiredID") UUID tagID);
}

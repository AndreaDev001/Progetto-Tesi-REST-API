package com.progettotirocinio.restapi.data.dao.reports;

import com.progettotirocinio.restapi.data.entities.reports.TaskReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TaskReportDao extends JpaRepository<TaskReport, UUID>
{
    @Query("select t from TaskReport t where t.reporter.id = :requiredID")
    Page<TaskReport> getTaskReportsByReporter(@Param("requiredID") UUID reporterID, Pageable pageable);
    @Query("select t from TaskReport t where t.reported.id = :requiredID")
    Page<TaskReport> getTaskReportsByReported(@Param("requiredID") UUID reportedID,Pageable pageable);
    @Query("select t from TaskReport t where t.task.id = :requiredID")
    Page<TaskReport> getTaskReportsByTask(@Param("requiredID") UUID taskID,Pageable pageable);
}

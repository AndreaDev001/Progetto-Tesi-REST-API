package com.progettotirocinio.restapi.data.dao.reports;

import com.progettotirocinio.restapi.data.entities.enums.ReportReason;
import com.progettotirocinio.restapi.data.entities.enums.ReportType;
import com.progettotirocinio.restapi.data.entities.reports.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReportDao extends JpaRepository<Report, UUID>, JpaSpecificationExecutor<Report>
{
    @Query("select r from Report r where r.reporter.id = :requiredID")
    Page<Report> getReportsByReporter(@Param("requiredID") UUID reporterID, Pageable pageable);
    @Query("select r from Report r where r.reported.id = :requiredID")
    Page<Report> getReportsByReported(@Param("requiredID") UUID reportedID,Pageable pageable);
    @Query("select r from Report r where r.type = :requiredType")
    Page<Report> getReportsByType(@Param("requiredType") ReportType type,Pageable pageable);
    @Query("select r from Report r where r.reason = :requiredReason")
    Page<Report> getReportsByReason(@Param("requiredReason") ReportReason reason,Pageable pageable);
    @Query("select r from Report r where r.reporter.id = :requiredReporterID and r.reported.id = :requiredReportedID")
    Optional<Report> getReportBetween(@Param("requiredReporterID") UUID reporterID,@Param("requiredReportedID") UUID reportedID);
}

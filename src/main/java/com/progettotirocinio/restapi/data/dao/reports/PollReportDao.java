package com.progettotirocinio.restapi.data.dao.reports;

import com.progettotirocinio.restapi.data.entities.reports.PollReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PollReportDao extends JpaRepository<PollReport, UUID>
{
    @Query("select p from PollReport p where p.reporter.id = :requiredID")
    Page<PollReport> getPollReportsByReporter(@Param("requiredID") UUID reporterID, Pageable pageable);
    @Query("select p from PollReport p where p.reported.id = :requiredID")
    Page<PollReport> getPollReportsByReported(@Param("requiredID") UUID reportedID,Pageable pageable);
    @Query("select p from PollReport p where p.poll.id = :requiredID")
    Page<PollReport> getPollReportsByPoll(@Param("requiredID") UUID pollID,Pageable pageable);
    @Query("select p from PollReport p where p.reporter.id = :requiredReporterID and p.poll.id = :requiredPollID")
    Optional<PollReport> getReportBetween(@Param("requiredReporterID") UUID reporterID,@Param("requiredPollID") UUID pollID);
}

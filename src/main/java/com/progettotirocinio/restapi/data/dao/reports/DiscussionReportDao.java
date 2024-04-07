package com.progettotirocinio.restapi.data.dao.reports;

import com.progettotirocinio.restapi.data.entities.reports.DiscussionReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DiscussionReportDao extends JpaRepository<DiscussionReport, UUID>
{
    @Query("select d from DiscussionReport d where d.reporter.id = :requiredID")
    Page<DiscussionReport> getDiscussionReportsByReporter(@Param("requiredID") UUID reporterID, Pageable pageable);
    @Query("select d from DiscussionReport d where d.reported.id = :requiredID")
    Page<DiscussionReport> getDiscussionReportsByReported(@Param("requiredID") UUID reportedID,Pageable pageable);
    @Query("select d from DiscussionReport d where d.discussion.id = :requiredID")
    Page<DiscussionReport> getDiscussionReportsByDiscussion(@Param("requiredID") UUID discussionID,Pageable pageable);
    @Query("select d from DiscussionReport d where d.discussion.id = :requiredDiscussionID and d.reporter.id = :requiredReporterID")
    Optional<DiscussionReport> getReportBetween(@Param("requiredDiscussionID") UUID discussionID,@Param("requiredReporterID") UUID reporterID);
}

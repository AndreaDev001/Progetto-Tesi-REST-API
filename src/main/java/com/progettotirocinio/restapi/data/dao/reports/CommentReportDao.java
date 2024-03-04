package com.progettotirocinio.restapi.data.dao.reports;

import com.progettotirocinio.restapi.data.entities.reports.CommentReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CommentReportDao extends JpaRepository<CommentReport, UUID>
{
    @Query("select c from CommentReport c where c.reporter.id = :requiredID")
    Page<CommentReport> getCommentReportsByReporter(@Param("requiredID") UUID reporterID, Pageable pageable);
    @Query("select c from CommentReport c where c.reported.id = :requiredID")
    Page<CommentReport> getCommentReportsByReported(@Param("requiredID") UUID reportedID,Pageable pageable);
    @Query("select c from CommentReport c where c.comment.id = :requiredID")
    Page<CommentReport> getCommentReportsByComment(@Param("requiredID") UUID commentID,Pageable pageable);
}

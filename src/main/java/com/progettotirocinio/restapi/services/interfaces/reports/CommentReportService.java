package com.progettotirocinio.restapi.services.interfaces.reports;

import com.progettotirocinio.restapi.data.dto.input.create.CreateReportDto;
import com.progettotirocinio.restapi.data.dto.input.update.UpdateCommentDto;
import com.progettotirocinio.restapi.data.dto.input.update.UpdateReportDto;
import com.progettotirocinio.restapi.data.dto.output.reports.CommentReportDto;
import com.progettotirocinio.restapi.data.entities.reports.CommentReport;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import java.util.UUID;

public interface CommentReportService
{
    PagedModel<CommentReportDto> getCommentReports(Pageable pageable);
    PagedModel<CommentReportDto> getCommentReportsByReporter(UUID reporterID,Pageable pageable);
    PagedModel<CommentReportDto> getCommentReportsByReported(UUID reportedID,Pageable pageable);
    PagedModel<CommentReportDto> getCommentReportsByComment(UUID commentID,Pageable pageable);
    CommentReportDto getCommentReport(UUID commentReportID);
    CommentReportDto getReportBetween(UUID userID,UUID reportID);
    CommentReportDto createCommentReport(CreateReportDto createReportDto,UUID commentID);
    void deleteCommentReport(UUID commentReportID);
}

package com.progettotirocinio.restapi.services.interfaces.reports;

import com.progettotirocinio.restapi.data.dto.input.create.CreateReportDto;
import com.progettotirocinio.restapi.data.dto.input.update.UpdateDiscussionDto;
import com.progettotirocinio.restapi.data.dto.input.update.UpdateReportDto;
import com.progettotirocinio.restapi.data.dto.output.reports.DiscussionReportDto;
import com.progettotirocinio.restapi.data.entities.Discussion;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import java.util.UUID;

public interface DiscussionReportService
{
    PagedModel<DiscussionReportDto> getDiscussionReports(Pageable pageable);
    PagedModel<DiscussionReportDto> getDiscussionReportsByReporter(UUID reporterID,Pageable pageable);
    PagedModel<DiscussionReportDto> getDiscussionReportsByReported(UUID reportedID,Pageable pageable);
    PagedModel<DiscussionReportDto> getDiscussionReportsByDiscussion(UUID discussionID,Pageable pageable);
    DiscussionReportDto getDiscussionReport(UUID discussionReportID);
    DiscussionReportDto createDiscussionReport(CreateReportDto createReportDto,UUID discussionID);
    void deleteDiscussionReport(UUID discussionReportID);
}

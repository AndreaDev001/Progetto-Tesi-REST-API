package com.progettotirocinio.restapi.services.interfaces.reports;

import com.progettotirocinio.restapi.data.dto.input.create.CreateReportDto;
import com.progettotirocinio.restapi.data.dto.output.reports.PollReportDto;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import java.util.UUID;

public interface PollReportService
{
    PagedModel<PollReportDto> getPollReports(Pageable pageable);
    PagedModel<PollReportDto> getPollReportsByReporter(UUID reporterID,Pageable pageable);
    PagedModel<PollReportDto> getPollReportsByReported(UUID reportedID,Pageable pageable);
    PagedModel<PollReportDto> getPollReportsByPoll(UUID pollID,Pageable pageable);
    PollReportDto getPollReport(UUID pollReportID);
    PollReportDto getPollReportBetween(UUID userID,UUID pollID);
    PollReportDto createPollReport(CreateReportDto createReportDto,UUID pollReportID);
    void deletePollReport(UUID pollReportID);
}

package com.progettotirocinio.restapi.services.interfaces.reports;

import com.progettotirocinio.restapi.data.dto.input.create.CreateReportDto;
import com.progettotirocinio.restapi.data.dto.input.update.UpdateReportDto;
import com.progettotirocinio.restapi.data.dto.output.reports.ReportDto;
import com.progettotirocinio.restapi.data.entities.enums.ReportReason;
import com.progettotirocinio.restapi.data.entities.enums.ReportType;
import com.progettotirocinio.restapi.data.entities.reports.Report;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;

import java.util.UUID;

public interface ReportService
{
    PagedModel<ReportDto> getReports(Pageable pageable);
    PagedModel<ReportDto> getReportsByReporter(UUID reporterID,Pageable pageable);
    PagedModel<ReportDto> getReportsByReported(UUID reportedID,Pageable pageable);
    PagedModel<ReportDto> getReportsByType(ReportType type,Pageable pageable);
    PagedModel<ReportDto> getReportsByReason(ReportReason reason,Pageable pageable);
    PagedModel<ReportDto> getReportsBySpec(Specification<Report> specification,Pageable pageable);
    PagedModel<ReportDto> getSimilarReports(UUID reportID,Pageable pageable);
    CollectionModel<ReportReason> getReasons();
    CollectionModel<ReportType> getTypes();
    CollectionModel<String> getOrderTypes();
    ReportDto getReport(UUID reportID);
    ReportDto createReport(CreateReportDto createReportDto,UUID reportedID);
    ReportDto updateReport(UpdateReportDto updateReportDto);
    ReportDto getReportBetween(UUID reporterID,UUID reportedID);
    void deleteReport(UUID reportID);
}

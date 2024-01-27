package com.progettotirocinio.restapi.services.interfaces.reports;

import com.progettotirocinio.restapi.data.dto.output.reports.ReportDto;
import com.progettotirocinio.restapi.data.entities.enums.ReportReason;
import com.progettotirocinio.restapi.data.entities.enums.ReportType;
import com.progettotirocinio.restapi.data.entities.reports.Report;
import org.springframework.data.domain.Pageable;
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
    CollectionModel<ReportReason> getReasons();
    CollectionModel<ReportType> getTypes();
    CollectionModel<String> getOrderTypes();
    ReportDto getReport(UUID reportID);
    void deleteReport(UUID reportID);
}

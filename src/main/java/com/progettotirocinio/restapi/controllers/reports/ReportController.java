package com.progettotirocinio.restapi.controllers.reports;

import com.progettotirocinio.restapi.data.dao.specifications.SpecificationsUtils;
import com.progettotirocinio.restapi.data.dto.input.PaginationRequest;
import com.progettotirocinio.restapi.data.dto.output.reports.ReportDto;
import com.progettotirocinio.restapi.data.entities.enums.ReportReason;
import com.progettotirocinio.restapi.data.entities.enums.ReportType;
import com.progettotirocinio.restapi.data.entities.reports.Report;
import com.progettotirocinio.restapi.services.interfaces.reports.ReportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController
{
    private final ReportService reportService;

    @GetMapping("/private")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<ReportDto>> getReports(@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<ReportDto> reports = this.reportService.getReports(paginationRequest.toPageRequest());
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/private/{reportID}")
    @PreAuthorize("@permissionHandler.hasAccess(@reportDao,#reportID)")
    public ResponseEntity<ReportDto> getReport(@PathVariable("reportID")UUID reportID) {
        ReportDto report = this.reportService.getReport(reportID);
        return ResponseEntity.ok(report);
    }

    @GetMapping("/private/reporter/{reporterID}")
    @PreAuthorize("@permissionHandler.hasAccess(#reporterID)")
    public ResponseEntity<PagedModel<ReportDto>> getReportsByReporter(@PathVariable("reporterID") UUID reporterID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<ReportDto> reports = this.reportService.getReportsByReporter(reporterID,paginationRequest.toPageRequest());
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/private/reported/{reportedID}")
    @PreAuthorize("@permissionHandler.hasAccess(#reportedID)")
    public ResponseEntity<PagedModel<ReportDto>> getReportsByReported(@PathVariable("reportedID") UUID reportedID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<ReportDto> reports = this.reportService.getReportsByReported(reportedID,paginationRequest.toPageRequest());
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/private/type/{type}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<ReportDto>> getReportsByType(@PathVariable("type")ReportType type,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<ReportDto> reports = this.reportService.getReportsByType(type,paginationRequest.toPageRequest());
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/private/reason/{reason}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<ReportDto>> getReportsByReason(@PathVariable("reason")ReportReason reason,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<ReportDto> reports = this.reportService.getReportsByReason(reason, paginationRequest.toPageRequest());
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/public/reasons")
    public ResponseEntity<CollectionModel<ReportReason>> getReasons() {
        return ResponseEntity.ok(this.reportService.getReasons());
    }

    @GetMapping("/public/types")
    public ResponseEntity<CollectionModel<ReportType>> getTypes() {
        return ResponseEntity.ok(this.reportService.getTypes());
    }

    @GetMapping("/public/orderTypes")
    public ResponseEntity<CollectionModel<String>> getOrderTypes() {
        return ResponseEntity.ok(this.reportService.getOrderTypes());
    }

    @DeleteMapping("/private/{reportID}")
    @PreAuthorize("@permissionHandler.hasAccess(@reportDao,#reportID)")
    public ResponseEntity<Void> deleteReport(@PathVariable("reportID") UUID reportID) {
        this.reportService.deleteReport(reportID);
        return ResponseEntity.noContent().build();
    }

}

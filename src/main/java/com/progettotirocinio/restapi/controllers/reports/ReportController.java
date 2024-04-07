package com.progettotirocinio.restapi.controllers.reports;

import com.progettotirocinio.restapi.data.dao.specifications.ReportSpecifications;
import com.progettotirocinio.restapi.data.dao.specifications.SpecificationsUtils;
import com.progettotirocinio.restapi.data.dto.input.PaginationRequest;
import com.progettotirocinio.restapi.data.dto.input.create.CreateReportDto;
import com.progettotirocinio.restapi.data.dto.input.update.UpdateReportDto;
import com.progettotirocinio.restapi.data.dto.output.reports.ReportDto;
import com.progettotirocinio.restapi.data.entities.enums.ReportReason;
import com.progettotirocinio.restapi.data.entities.enums.ReportType;
import com.progettotirocinio.restapi.data.entities.reports.Report;
import com.progettotirocinio.restapi.services.interfaces.reports.ReportService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@SecurityRequirement(name = "Authorization")
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

    @GetMapping("/private/reporter/{reporterID}/reported/{reportedID}")
    @PreAuthorize("@permissionHandler.hasAccess(#reporterID)")
    public ResponseEntity<ReportDto> getReportBetween(@PathVariable("reporterID") UUID reporterID,@PathVariable("reportedID") UUID reportedID) {
        ReportDto reportDto = this.reportService.getReportBetween(reporterID,reportedID);
        return ResponseEntity.ok(reportDto);
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

    @GetMapping("/private/spec")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<ReportDto>> getReportsBySpec(@ParameterObject @Valid ReportSpecifications.Filter filter,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<ReportDto> reports = this.reportService.getReportsBySpec(ReportSpecifications.withFilter(filter),paginationRequest.toPageRequest());
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/private/similar/{reportID}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<ReportDto>> getSimilarReports(@PathVariable("reportID") UUID reportID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<ReportDto> reports = this.reportService.getSimilarReports(reportID,paginationRequest.toPageRequest());
        return ResponseEntity.ok(reports);
    }

    @PostMapping("/private/{reportedID}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_MEMBER')")
    public ResponseEntity<ReportDto> createReport(@RequestBody @Valid CreateReportDto createReportDto,@PathVariable("reportedID") UUID reportedID) {
        return ResponseEntity.status(201).body(this.reportService.createReport(createReportDto,reportedID));
    }

    @PutMapping("/private")
    @PreAuthorize("@permissionHandler.hasAccess(@reportDao,#updateReportDto.reporterID)")
    public ResponseEntity<ReportDto> updateReport(@RequestBody @Valid UpdateReportDto updateReportDto) {
        return ResponseEntity.ok(this.reportService.updateReport(updateReportDto));
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

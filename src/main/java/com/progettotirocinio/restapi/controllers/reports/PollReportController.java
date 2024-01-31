package com.progettotirocinio.restapi.controllers.reports;

import com.progettotirocinio.restapi.data.dao.reports.PollReportDao;
import com.progettotirocinio.restapi.data.dao.reports.ReportDao;
import com.progettotirocinio.restapi.data.dto.input.PaginationRequest;
import com.progettotirocinio.restapi.data.dto.input.create.CreateReportDto;
import com.progettotirocinio.restapi.data.dto.output.reports.PollReportDto;
import com.progettotirocinio.restapi.services.interfaces.reports.PollReportService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.naming.spi.ResolveResult;
import java.util.UUID;

@RestController
@RequestMapping("/pollReports")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
public class PollReportController
{
    private final PollReportService pollReportService;

    @GetMapping("/private")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<PollReportDto>> getPollReports(@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<PollReportDto> pollReports = this.pollReportService.getPollReports(paginationRequest.toPageRequest());
        return ResponseEntity.ok(pollReports);
    }

    @GetMapping("/private/{pollReportID}")
    @PreAuthorize("@permissionHandler.hasAccess(@pollReportDao,#pollReportID)")
    public ResponseEntity<PollReportDto> getPollReport(@PathVariable("pollReportID")UUID pollReportID) {
        PollReportDto pollReportDto = this.pollReportService.getPollReport(pollReportID);
        return ResponseEntity.ok(pollReportDto);
    }

    @GetMapping("/private/reporter/{reporterID}")
    @PreAuthorize("@permissionHandler.hasAccess(#reporterID)")
    public ResponseEntity<PagedModel<PollReportDto>> getPollReportsByReporter(@PathVariable("reporterID") UUID reporterID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<PollReportDto> pollReports = this.pollReportService.getPollReportsByReporter(reporterID,paginationRequest.toPageRequest());
        return ResponseEntity.ok(pollReports);
    }

    @GetMapping("/private/reported/{reportedID}")
    @PreAuthorize("@permissionHandler.hasAccess(#reportedID)")
    public ResponseEntity<PagedModel<PollReportDto>> getPollReportsByReported(@PathVariable("reportedID") UUID reportedID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<PollReportDto> pollReports = this.pollReportService.getPollReportsByReported(reportedID,paginationRequest.toPageRequest());
        return ResponseEntity.ok(pollReports);
    }

    @GetMapping("/private/poll/{pollID}")
    @PreAuthorize("@permissionHandler.hasAccess(@pollDao,#pollID)")
    public ResponseEntity<PagedModel<PollReportDto>> getPollReportsByPoll(@PathVariable("pollID") UUID pollID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<PollReportDto> pollReports = this.pollReportService.getPollReportsByPoll(pollID,paginationRequest.toPageRequest());
        return ResponseEntity.ok(pollReports);
    }

    @PostMapping("/private/{pollID}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_MEMBER')")
    public ResponseEntity<PollReportDto> createPoll(@RequestBody @Valid CreateReportDto createReportDto,@PathVariable("pollID") UUID pollID) {
        PollReportDto pollReportDto = this.pollReportService.createPollReport(createReportDto,pollID);
        return ResponseEntity.status(201).body(pollReportDto);
    }

    @DeleteMapping("/private/{pollID}")
    @PreAuthorize("@permissionHandler.hasAccess(@pollDao,#pollID)")
    public ResponseEntity<Void> deletePollReport(@PathVariable("pollID") UUID pollID) {
        this.pollReportService.deletePollReport(pollID);
        return ResponseEntity.noContent().build();
    }
}

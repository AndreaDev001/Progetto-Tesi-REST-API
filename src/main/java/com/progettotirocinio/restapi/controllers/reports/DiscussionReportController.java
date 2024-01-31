package com.progettotirocinio.restapi.controllers.reports;

import com.progettotirocinio.restapi.data.dto.input.PaginationRequest;
import com.progettotirocinio.restapi.data.dto.input.create.CreateReportDto;
import com.progettotirocinio.restapi.data.dto.output.reports.DiscussionReportDto;
import com.progettotirocinio.restapi.data.entities.reports.DiscussionReport;
import com.progettotirocinio.restapi.services.interfaces.reports.DiscussionReportService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/discussionsReports")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
public class DiscussionReportController
{
    private final DiscussionReportService discussionReportService;

    @GetMapping("/private")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<DiscussionReportDto>> getDiscussions(@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<DiscussionReportDto> discussions = this.discussionReportService.getDiscussionReports(paginationRequest.toPageRequest());
        return ResponseEntity.ok(discussions);
    }

    @GetMapping("/private/{discussionReportID}")
    @PreAuthorize("@permissionHandler.hasAccess(@discussionReportDao,#discussionReportID)")
    public ResponseEntity<DiscussionReportDto> getDiscussionReport(@PathVariable("discussionReportID")UUID discussionReportID) {
        DiscussionReportDto discussionReportDto = this.discussionReportService.getDiscussionReport(discussionReportID);
        return ResponseEntity.ok(discussionReportDto);
    }

    @GetMapping("/private/reporter/{reporterID}")
    @PreAuthorize("@permissionHandler.hasAccess(#reporterID)")
    public ResponseEntity<PagedModel<DiscussionReportDto>> getDiscussionReportsByReporter(@PathVariable("reporterID") UUID reporterID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<DiscussionReportDto> discussionReports = this.discussionReportService.getDiscussionReportsByReported(reporterID,paginationRequest.toPageRequest());
        return ResponseEntity.ok(discussionReports);
    }

    @GetMapping("/private/reported/{reportedID}")
    @PreAuthorize("@permissionHandler.hasAccess(#reportedID)")
    public ResponseEntity<PagedModel<DiscussionReportDto>> getDiscussionReportsByReported(@PathVariable("reportedID") UUID reportedID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<DiscussionReportDto> discussionReports = this.discussionReportService.getDiscussionReportsByReported(reportedID,paginationRequest.toPageRequest());
        return ResponseEntity.ok(discussionReports);
    }

    @GetMapping("/private/discussion/{discussionID}")
    @PreAuthorize("@permissionHandler.hasAccess(@discussionDao,#discussionID)")
    public ResponseEntity<PagedModel<DiscussionReportDto>> getDiscussionReportsByDiscussion(@PathVariable("discussionID") UUID discussionID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<DiscussionReportDto> discussionReports = this.discussionReportService.getDiscussionReportsByDiscussion(discussionID,paginationRequest.toPageRequest());
        return ResponseEntity.ok(discussionReports);
    }

    @PostMapping("/private/{discussionID}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_MEMBER')")
    public ResponseEntity<DiscussionReportDto> createDiscussionReport(@RequestBody @Valid CreateReportDto createReportDto, @PathVariable("discussionID") UUID discussionID) {
        DiscussionReportDto discussionReportDto = this.discussionReportService.createDiscussionReport(createReportDto,discussionID);
        return ResponseEntity.status(201).body(discussionReportDto);
    }

    @DeleteMapping("/private/{discussionReportID}")
    @PreAuthorize("@permissionHandler.hasAccess(@discussionReportDao,#discussionReportID)")
    public ResponseEntity<Void> deleteDiscussionReport(@PathVariable("discussionReportID") UUID discussionReportID) {
        this.discussionReportService.deleteDiscussionReport(discussionReportID);
        return ResponseEntity.noContent().build();
    }
}

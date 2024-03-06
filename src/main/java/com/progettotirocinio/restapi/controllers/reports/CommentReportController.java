package com.progettotirocinio.restapi.controllers.reports;

import com.progettotirocinio.restapi.data.dto.input.PaginationRequest;
import com.progettotirocinio.restapi.data.dto.input.create.CreateReportDto;
import com.progettotirocinio.restapi.data.dto.output.reports.CommentReportDto;
import com.progettotirocinio.restapi.data.entities.reports.CommentReport;
import com.progettotirocinio.restapi.services.interfaces.reports.CommentReportService;
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
@RequestMapping("/commentReports")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
public class CommentReportController {

    private final CommentReportService commentReportService;

    @GetMapping("/private")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<CommentReportDto>> getCommentReports(@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<CommentReportDto> commentReports = this.commentReportService.getCommentReports(paginationRequest.toPageRequest());
        return ResponseEntity.ok(commentReports);
    }

    @GetMapping("/private/{commentReportID}")
    @PreAuthorize("@permissionHandler.hasAccess(@commentReportDao,#commentReportID)")
    public ResponseEntity<CommentReportDto> getCommentReport(@PathVariable("commentReportID")UUID commentReportID) {
        CommentReportDto commentReportDto = this.commentReportService.getCommentReport(commentReportID);
        return ResponseEntity.ok(commentReportDto);
    }

    @GetMapping("/private/reporter/{reporterID}")
    @PreAuthorize("@permissionHandler.hasAccess(#reporterID)")
    public ResponseEntity<PagedModel<CommentReportDto>> getCommentReportsByReporter(@PathVariable("reporterID") UUID reporterID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<CommentReportDto> commentReports = this.commentReportService.getCommentReportsByReporter(reporterID,paginationRequest.toPageRequest());
        return ResponseEntity.ok(commentReports);
    }

    @GetMapping("/private/reported/{reportedID}")
    @PreAuthorize("@permissionHandler.hasAccess(#reportedID)")
    public ResponseEntity<PagedModel<CommentReportDto>> getCommentReportsByReported(@PathVariable("reportedID") UUID reportedID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<CommentReportDto> commentReports = this.commentReportService.getCommentReportsByReported(reportedID,paginationRequest.toPageRequest());
        return ResponseEntity.ok(commentReports);
    }

    @GetMapping("/private/comment/{commentID}")
    @PreAuthorize("@permissionHandler.hasAccess(@commentDao,#commentID)")
    public ResponseEntity<PagedModel<CommentReportDto>> getCommentReportsByComment(@PathVariable("commentID") UUID commentID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<CommentReportDto> commentReports = this.commentReportService.getCommentReportsByComment(commentID,paginationRequest.toPageRequest());
        return ResponseEntity.ok(commentReports);
    }

    @GetMapping("/private/comment/{commentID}/reporter/{reporterID}")
    @PreAuthorize("@permissionHandler.hasAccess(#reporterID)")
    public ResponseEntity<CommentReportDto> getReportBetween(@PathVariable("commentID") UUID commentID,@PathVariable("reporterID") UUID reporterID) {
        CommentReportDto commentReportDto = this.commentReportService.getReportBetween(reporterID,commentID);
        return ResponseEntity.ok(commentReportDto);
    }

    @PostMapping("/private/{commentID}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_MEMBER')")
    public ResponseEntity<CommentReportDto> createCommentReport(@RequestBody @Valid CreateReportDto createReportDto, @PathVariable("commentID") UUID commentID) {
        CommentReportDto commentReportDto = this.commentReportService.createCommentReport(createReportDto,commentID);
        return ResponseEntity.status(201).body(commentReportDto);
    }

    @DeleteMapping("/private/{commentReportID}")
    @PreAuthorize("@permissionHandler.hasAccess(@commentReportDao,#commentReportID)")
    public ResponseEntity<Void> deleteCommentReport(@PathVariable("commentReportID") UUID commentReportID) {
        this.commentReportService.deleteCommentReport(commentReportID);
        return ResponseEntity.noContent().build();
    }
}

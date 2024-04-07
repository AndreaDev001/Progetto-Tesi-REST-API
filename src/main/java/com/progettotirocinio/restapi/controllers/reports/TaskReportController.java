package com.progettotirocinio.restapi.controllers.reports;

import com.progettotirocinio.restapi.data.dao.reports.ReportDao;
import com.progettotirocinio.restapi.data.dto.input.PaginationRequest;
import com.progettotirocinio.restapi.data.dto.input.create.CreateReportDto;
import com.progettotirocinio.restapi.data.dto.input.create.CreateTaskDto;
import com.progettotirocinio.restapi.data.dto.output.reports.ReportDto;
import com.progettotirocinio.restapi.data.dto.output.reports.TaskReportDto;
import com.progettotirocinio.restapi.data.entities.reports.TaskReport;
import com.progettotirocinio.restapi.services.interfaces.reports.TaskReportService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/taskReports")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
public class TaskReportController
{
    private final TaskReportService taskReportService;

    @GetMapping("/private")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<TaskReportDto>> getTaskReports(@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<TaskReportDto> reports = this.taskReportService.getTaskReports(paginationRequest.toPageRequest());
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/private/{taskReportID}")
    @PreAuthorize("@permissionHandler.hasAccess(@taskReportDao,#taskReportID)")
    public ResponseEntity<TaskReportDto> getTask(@PathVariable("taskReportID")UUID taskReportID) {
        TaskReportDto taskReportDto = this.taskReportService.getTaskReport(taskReportID);
        return ResponseEntity.ok(taskReportDto);
    }

    @GetMapping("/private/reporter/{reporterID}")
    @PreAuthorize("@permissionHandler.hasAccess(#reporterID)")
    public ResponseEntity<PagedModel<TaskReportDto>> getTaskReportsByReporter(@PathVariable("reporterID") UUID reporterID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<TaskReportDto> taskReports = this.taskReportService.getTaskReportsByReporter(reporterID,paginationRequest.toPageRequest());
        return ResponseEntity.ok(taskReports);
    }

    @GetMapping("/private/reported/{reportedID}")
    @PreAuthorize("@permissionHandler.hasAccess(#reportedID)")
    public ResponseEntity<PagedModel<TaskReportDto>> getTaskReportsByReported(@PathVariable("reportedID") UUID reportedID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<TaskReportDto> taskReports = this.taskReportService.getTaskReportsByReported(reportedID,paginationRequest.toPageRequest());
        return ResponseEntity.ok(taskReports);
    }

    @GetMapping("/private/task/{taskID}")
    @PreAuthorize("@permissionHandler.hasAccess(@taskDao,#taskID)")
    public ResponseEntity<PagedModel<TaskReportDto>> getTaskReportsByTask(@PathVariable("taskID") UUID taskID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<TaskReportDto> taskReports = this.taskReportService.getTaskReportsByTask(taskID,paginationRequest.toPageRequest());
        return ResponseEntity.ok(taskReports);
    }

    @GetMapping("/private/task/{taskID}/reporter/{reporterID}")
    @PreAuthorize("@permissionHandler.hasAccess(#reporterID)")
    public ResponseEntity<TaskReportDto> getReportBetween(@PathVariable("taskID") UUID taskID,@PathVariable("reporterID") UUID reporterID) {
        TaskReportDto taskReportDto = this.taskReportService.getTaskReportBetween(reporterID,taskID);
        return ResponseEntity.ok(taskReportDto);
    }

    @PostMapping("/private/{taskID}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_MEMBER')")
    public ResponseEntity<TaskReportDto> createTaskReport(@RequestBody @Valid CreateReportDto createReportDto, @PathVariable("taskID") UUID taskID) {
        TaskReportDto taskReportDto = this.taskReportService.createTaskReport(createReportDto,taskID);
        return ResponseEntity.ok(taskReportDto);
    }

    @DeleteMapping("/private/{taskReportID}")
    @PreAuthorize("@permissionHandler.hasAccess(@taskReportDao,#taskReportID)")
    public ResponseEntity<Void> deleteTaskReport(@PathVariable("taskReportID") UUID taskReportID) {
        this.taskReportService.deleteTaskReport(taskReportID);
        return ResponseEntity.noContent().build();
    }
}

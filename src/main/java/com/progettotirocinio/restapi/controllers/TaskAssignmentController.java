package com.progettotirocinio.restapi.controllers;

import com.progettotirocinio.restapi.data.dto.input.PaginationRequest;
import com.progettotirocinio.restapi.data.dto.input.create.CreateTaskAssignmentDto;
import com.progettotirocinio.restapi.data.dto.output.TaskAssignmentDto;
import com.progettotirocinio.restapi.services.interfaces.TaskAssignmentService;
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
@RequiredArgsConstructor
@RequestMapping("/taskAssignments")
public class TaskAssignmentController
{
    private final TaskAssignmentService taskAssignmentService;

    @GetMapping("/private")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<TaskAssignmentDto>> getTaskAssignments(@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<TaskAssignmentDto> taskAssignments = this.taskAssignmentService.getTaskAssignments(paginationRequest.toPageRequest());
        return ResponseEntity.ok(taskAssignments);
    }

    @GetMapping("/private/{taskAssignmentID}")
    @PreAuthorize("@permissionHandler.hasAccess(@taskAssignmentDao,#taskAssignmentID)")
    public ResponseEntity<TaskAssignmentDto> getTaskAssignment(@PathVariable("taskAssignmentID")UUID taskAssignmentID) {
        TaskAssignmentDto taskAssignmentDto = this.taskAssignmentService.getTaskAssignment(taskAssignmentID);
        return ResponseEntity.ok(taskAssignmentDto);
    }

    @GetMapping("/private/publisher/{publisherID}")
    @PreAuthorize("@permissionHandler.hasAccess(#publisherID)")
    public ResponseEntity<PagedModel<TaskAssignmentDto>> getTaskAssignmentsByPublisher(@PathVariable("publisherID") UUID publisherID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<TaskAssignmentDto> taskAssignments = this.taskAssignmentService.getTaskAssignmentsByPublisher(publisherID,paginationRequest.toPageRequest());
        return ResponseEntity.ok(taskAssignments);
    }

    @GetMapping("/private/task/{taskID}")
    @PreAuthorize("@permissionHandler.hasAccess(@taskDao,#taskID)")
    public ResponseEntity<PagedModel<TaskAssignmentDto>> getTaskAssignmentsByTask(@PathVariable("taskID") UUID taskID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<TaskAssignmentDto> taskAssignments = this.taskAssignmentService.getTaskAssignmentsByTask(taskID,paginationRequest.toPageRequest());
        return ResponseEntity.ok(taskAssignments);
    }

    @GetMapping("/private/user/{userID}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_MEMBER')")
    public ResponseEntity<PagedModel<TaskAssignmentDto>> getTaskAssignmentsByUser(@PathVariable("userID") UUID userID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<TaskAssignmentDto> taskAssignments = this.taskAssignmentService.getTaskAssignmentsByUser(userID,paginationRequest.toPageRequest());
        return ResponseEntity.ok(taskAssignments);
    }

    @GetMapping("/private/user/{userID}/task/{taskID}")
    @PreAuthorize("@permissionHandler.hasAccess(#userID)")
    public ResponseEntity<TaskAssignmentDto> getTaskAssignment(@PathVariable("userID") UUID userID,@PathVariable("taskAssignmentID") UUID taskAssignmentID) {
        TaskAssignmentDto taskAssignmentDto = this.taskAssignmentService.getTaskAssignment(userID,taskAssignmentID);
        return ResponseEntity.ok(taskAssignmentDto);
    }

    @PostMapping("/private")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_MEMBER')")
    public ResponseEntity<TaskAssignmentDto> createTaskAssignment(@RequestBody @Valid CreateTaskAssignmentDto createTaskAssignmentDto) {
        TaskAssignmentDto taskAssignmentDto = this.taskAssignmentService.createTaskAssignment(createTaskAssignmentDto);
        return ResponseEntity.status(201).body(taskAssignmentDto);
    }

    @DeleteMapping("/private/{taskAssignmentID}")
    @PreAuthorize("@permissionHandler.hasAccess(@taskAssignmentDao,#taskAssignmentID)")
    public ResponseEntity<Void> deleteTaskAssignment(@PathVariable("taskAssignmentID") UUID taskAssignmentID) {
        this.taskAssignmentService.deleteTaskAssignment(taskAssignmentID);
        return ResponseEntity.noContent().build();
    }
}

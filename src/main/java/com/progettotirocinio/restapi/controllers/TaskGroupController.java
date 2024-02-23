package com.progettotirocinio.restapi.controllers;


import com.progettotirocinio.restapi.data.dto.input.PaginationRequest;
import com.progettotirocinio.restapi.data.dto.input.create.CreateTaskGroupDto;
import com.progettotirocinio.restapi.data.dto.input.update.UpdateTaskDto;
import com.progettotirocinio.restapi.data.dto.input.update.UpdateTaskGroupDto;
import com.progettotirocinio.restapi.data.dto.output.TaskGroupDto;
import com.progettotirocinio.restapi.data.entities.Task;
import com.progettotirocinio.restapi.data.entities.enums.TaskGroupStatus;
import com.progettotirocinio.restapi.services.interfaces.TaskGroupService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.repository.query.Param;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
@RequestMapping("/taskGroups")
public class TaskGroupController
{
    private final TaskGroupService taskGroupService;

    @GetMapping("/private")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<TaskGroupDto>> getTaskGroups(@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<TaskGroupDto> pagedModel = this.taskGroupService.getTasks(paginationRequest.toPageRequest());
        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/private/publisher/{publisherID}")
    @PreAuthorize("@permissionHandler.hasAccess(#publisherID)")
    public ResponseEntity<PagedModel<TaskGroupDto>> getTaskGroupsByPublisher(@PathVariable("publisherID") UUID publisherID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<TaskGroupDto> taskGroups = this.taskGroupService.getTaskGroupsByPublisher(publisherID,paginationRequest.toPageRequest());
        return ResponseEntity.ok(taskGroups);
    }

    @PostMapping("/private")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_MEMBER')")
    public ResponseEntity<TaskGroupDto> createTaskGroup(@RequestBody @Valid CreateTaskGroupDto createTaskGroupDto) {
        TaskGroupDto taskGroupDto = this.taskGroupService.createTaskGroup(createTaskGroupDto);
        return ResponseEntity.status(201).body(taskGroupDto);
    }

    @PutMapping("/private")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_MEMBER')")
    public ResponseEntity<TaskGroupDto> updateTaskGroup(@RequestBody @Valid UpdateTaskGroupDto updateTaskGroupDto) {
        TaskGroupDto taskGroupDto = this.taskGroupService.updateTaskGroup(updateTaskGroupDto);
        return ResponseEntity.ok(taskGroupDto);
    }

    @GetMapping("/private/board/{boardID}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_MEMBER')")
    public ResponseEntity<CollectionModel<TaskGroupDto>> getTaskGroupsByBoard(@PathVariable("boardID") UUID boardID) {
        CollectionModel<TaskGroupDto> collectionModel = this.taskGroupService.getTaskGroupsByBoard(boardID);
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/private/name/{name}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_MEMBER')")
    public ResponseEntity<PagedModel<TaskGroupDto>> getTaskGroupsByName(@PathVariable("name") String name, @ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<TaskGroupDto> taskGroups = this.taskGroupService.getTaskGroupsByName(name,paginationRequest.toPageRequest());
        return ResponseEntity.ok(taskGroups);
    }

    @GetMapping("/public/statues")
    public ResponseEntity<CollectionModel<TaskGroupStatus>> getStatues() {
        return ResponseEntity.ok(this.taskGroupService.getStatues());
    }

    @GetMapping("/private/status/{status}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<TaskGroupDto>> getTaskGroups(@PathVariable("status") TaskGroupStatus status,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<TaskGroupDto> taskGroups = this.taskGroupService.getTaskGroupsByStatus(status,paginationRequest.toPageRequest());
        return ResponseEntity.ok(taskGroups);
    }

    @GetMapping("/private/{taskGroupID}")
    @PreAuthorize("@permissionHandler.hasRole(@taskGroupDao,#taskGroupID)")
    public ResponseEntity<TaskGroupDto> getTaskGroup(@PathVariable("taskGroupID") UUID taskGroupID) {
        TaskGroupDto taskGroup = this.taskGroupService.getTaskGroup(taskGroupID);
        return ResponseEntity.ok(taskGroup);
    }

    @DeleteMapping("/private/{taskGroupID}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteTaskGroup(@PathVariable("taskGroupID") UUID taskGroupID) {
        this.taskGroupService.deleteTaskGroup(taskGroupID);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/private/clear/{taskGroupID}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<TaskGroupDto> clearTaskGroup(@PathVariable("taskGroupID") UUID taskGroupID) {
        TaskGroupDto taskGroupDto =  this.taskGroupService.clearTaskGroup(taskGroupID);
        return ResponseEntity.ok(taskGroupDto);
    }
}

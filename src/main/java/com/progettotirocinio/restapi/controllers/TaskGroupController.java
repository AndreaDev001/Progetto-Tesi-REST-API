package com.progettotirocinio.restapi.controllers;


import com.progettotirocinio.restapi.data.dto.input.PaginationRequest;
import com.progettotirocinio.restapi.data.dto.output.TaskGroupDto;
import com.progettotirocinio.restapi.services.interfaces.TaskGroupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.repository.query.Param;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/taskGroups")
public class TaskGroupController
{
    private final TaskGroupService taskGroupService;

    @GetMapping("/private")
    public ResponseEntity<PagedModel<TaskGroupDto>> getTaskGroups(@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<TaskGroupDto> pagedModel = this.taskGroupService.getTasks(paginationRequest.toPageRequest());
        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/private/publisher/{publisherID}")
    public ResponseEntity<PagedModel<TaskGroupDto>> getTaskGroupsByPublisher(@PathVariable("publisherID") UUID publisherID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<TaskGroupDto> taskGroups = this.taskGroupService.getTaskGroupsByPublisher(publisherID,paginationRequest.toPageRequest());
        return ResponseEntity.ok(taskGroups);
    }

    @GetMapping("/private/board/{boardID}")
    public ResponseEntity<PagedModel<TaskGroupDto>> getTaskGroupsByBoard(@PathVariable("boardID") UUID boardID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<TaskGroupDto> taskGroups = this.taskGroupService.getTaskGroupsByBoard(boardID,paginationRequest.toPageRequest());
        return ResponseEntity.ok(taskGroups);
    }

    @GetMapping("/private/name/{name}")
    public ResponseEntity<PagedModel<TaskGroupDto>> getTaskGroupsByName(@PathVariable("name") String name, @ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<TaskGroupDto> taskGroups = this.taskGroupService.getTaskGroupsByName(name,paginationRequest.toPageRequest());
        return ResponseEntity.ok(taskGroups);
    }

    @GetMapping("/private/{taskGroupID}")
    public ResponseEntity<TaskGroupDto> getTaskGroup(@PathVariable("taskGroupID") UUID taskGroupID) {
        TaskGroupDto taskGroup = this.taskGroupService.getTaskGroup(taskGroupID);
        return ResponseEntity.ok(taskGroup);
    }

    @DeleteMapping("/private/{taskGroupID}")
    public ResponseEntity<TaskGroupDto> deleteTaskGroup(@PathVariable("taskGroupID") UUID taskGroupID) {
        this.taskGroupService.deleteTaskGroup(taskGroupID);
        return ResponseEntity.noContent().build();
    }
}

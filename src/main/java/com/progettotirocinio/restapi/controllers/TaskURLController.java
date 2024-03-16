package com.progettotirocinio.restapi.controllers;

import com.progettotirocinio.restapi.data.dto.input.PaginationRequest;
import com.progettotirocinio.restapi.data.dto.input.create.CreateTaskURLDto;
import com.progettotirocinio.restapi.data.dto.output.TaskURLDto;
import com.progettotirocinio.restapi.services.interfaces.TaskURLService;
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
@RequestMapping("/taskURLS")
@RequiredArgsConstructor
public class TaskURLController
{
    private final TaskURLService taskURLService;
    @GetMapping("/private")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<TaskURLDto>> getTaskURLS(@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<TaskURLDto> pagedModel = this.taskURLService.getTaskURLs(paginationRequest.toPageRequest());
        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/private/publisher/{publisherID}")
    @PreAuthorize("@permissionHandler.hasAccess(#publisherID,@boardMemberDao)")
    public ResponseEntity<PagedModel<TaskURLDto>> getTaskURLSFromPublisher(@PathVariable("publisherID")UUID publisherID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<TaskURLDto> pagedModel = this.taskURLService.getTaskURLsByPublisher(publisherID,paginationRequest.toPageRequest());
        return ResponseEntity.ok(pagedModel);
    }



    @GetMapping("/private/task/{taskID}")
    @PreAuthorize("@permissionHandler.hasBoardRole('MEMBER',#taskID,@taskDao)")
    public ResponseEntity<CollectionModel<TaskURLDto>> getTaskURLSByTask(@PathVariable("taskID") UUID taskID) {
        CollectionModel<TaskURLDto> collectionModel = this.taskURLService.getTaskURLsByTask(taskID);
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/private/url/{url}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<TaskURLDto>> getTaskURLSByURL(@PathVariable("url") String url,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<TaskURLDto> taskURLS = this.taskURLService.getTaskURLsByURL(url,paginationRequest.toPageRequest());
        return ResponseEntity.ok(taskURLS);
    }

    @GetMapping("/private/name/{name}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<TaskURLDto>> getTaskURLSByName(@PathVariable("name") String name,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<TaskURLDto> taskURLS = this.taskURLService.getTaskURLsByName(name,paginationRequest.toPageRequest());
        return ResponseEntity.ok(taskURLS);
    }

    @PostMapping("/private")
    @PreAuthorize("@permissionHandler.isAssigned(#createTaskURLDto.taskID)")
    public ResponseEntity<TaskURLDto> createTaskURL(@RequestBody @Valid CreateTaskURLDto createTaskURLDto) {
        TaskURLDto taskURLDto = this.taskURLService.createTaskURL(createTaskURLDto);
        return ResponseEntity.status(201).body(taskURLDto);
    }

    @DeleteMapping("/private/{taskURLId}")
    @PreAuthorize("@permissionHandler.hasBoardRole('MEMBER',#taskURLId,@taskURLDao)")
    public ResponseEntity<TaskURLDto> deleteTaskURL(@PathVariable("taskURLId") UUID taskURLId) {
        this.taskURLService.deleteTaskURL(taskURLId);
        return ResponseEntity.noContent().build();
    }
}

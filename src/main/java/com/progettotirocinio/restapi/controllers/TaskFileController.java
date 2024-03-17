package com.progettotirocinio.restapi.controllers;

import com.progettotirocinio.restapi.data.dto.input.PaginationRequest;
import com.progettotirocinio.restapi.data.dto.input.create.CreateTaskFileDto;
import com.progettotirocinio.restapi.data.dto.output.TaskFileDto;
import com.progettotirocinio.restapi.data.entities.enums.NotAllowedExtension;
import com.progettotirocinio.restapi.services.interfaces.TaskFileService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/taskFiles")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
public class TaskFileController
{
    private final TaskFileService taskFileService;

    @GetMapping("/private")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<TaskFileDto>> getTaskFiles(@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<TaskFileDto> pagedModel = this.taskFileService.getTaskFiles(paginationRequest.toPageRequest());
        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/private/publisher/{publisherID}")
    @PreAuthorize("@permissionHandler.hasAccess(#publisherID,@boardMemberDao)")
    public ResponseEntity<PagedModel<TaskFileDto>> getTaskFilesByPublisher(@PathVariable("publisherID")UUID publisherID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<TaskFileDto> pagedModel = this.taskFileService.getTaskFilesByPublisher(publisherID,paginationRequest.toPageRequest());
        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/private/name/{name}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<TaskFileDto>> getTaskFilesByName(@PathVariable("name") String name,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<TaskFileDto> pagedModel = this.taskFileService.getTaskFilesByName(name,paginationRequest.toPageRequest());
        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/private/{taskFileID}")
    @PreAuthorize("@permissionHandler.hasAccess(#taskFileID,@taskFileDao)")
    public ResponseEntity<TaskFileDto> getTaskFile(@PathVariable("taskFileID") UUID taskFileID) {
        TaskFileDto taskFileDto = this.taskFileService.getTaskFile(taskFileID);
        return ResponseEntity.ok(taskFileDto);
    }

    @GetMapping("/private/task/{taskID}")
    @PreAuthorize("@permissionHandler.hasBoardRole('MEMBER',#taskID,@taskDao)")
    public ResponseEntity<CollectionModel<TaskFileDto>> getTaskFilesByTask(@PathVariable("taskID") UUID taskID) {
        CollectionModel<TaskFileDto> collectionModel = this.taskFileService.getTaskFilesByTask(taskID);
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/private/{taskFileID}/file")
    public ResponseEntity<byte[]> getFileAsBytes(@PathVariable("taskFileID") UUID taskFileID) {
        TaskFileDto taskFileDto = this.taskFileService.getTaskFile(taskFileID);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + taskFileDto.getFileName()+ "\"").body(this.taskFileService.getFileByID(taskFileID));
    }

    @GetMapping("/public/extensions")
    public ResponseEntity<CollectionModel<NotAllowedExtension>> getNotAllowedExtensions() {
        CollectionModel<NotAllowedExtension> collectionModel = this.taskFileService.getNotAllowedExtensions();
        return ResponseEntity.ok(collectionModel);
    }

    @PostMapping(value = "/private",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<TaskFileDto> createTaskFile(@ModelAttribute @Valid CreateTaskFileDto createTaskFileDto) {
        TaskFileDto taskFileDto = this.taskFileService.createTaskFile(createTaskFileDto);
        return ResponseEntity.status(201).body(taskFileDto);
    }

    @DeleteMapping("/private/{taskFileID}")
    public ResponseEntity<Void> deleteTaskFile(@PathVariable("taskFileID") UUID taskFileID) {
        this.taskFileService.deleteTaskFile(taskFileID);
        return ResponseEntity.noContent().build();
    }
}

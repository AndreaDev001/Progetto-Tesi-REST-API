package com.progettotirocinio.restapi.controllers.images;


import com.progettotirocinio.restapi.data.dto.input.PaginationRequest;
import com.progettotirocinio.restapi.data.dto.input.create.images.CreateTaskImageDto;
import com.progettotirocinio.restapi.data.dto.output.images.TaskImageDto;
import com.progettotirocinio.restapi.data.entities.images.TaskImage;
import com.progettotirocinio.restapi.services.interfaces.images.TaskImageService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/taskImages")
@SecurityRequirement(name = "Authorization")
public class TaskImageController
{
    private final TaskImageService taskImageService;
    @GetMapping("/private")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<TaskImageDto>> getTaskImages(@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<TaskImageDto> taskImages = taskImageService.getTaskImages(paginationRequest.toPageRequest());
        return ResponseEntity.ok(taskImages);
    }

    @GetMapping("/private/{taskImageID}")
    @PreAuthorize("@permissionHandler.isMember(#taskImageID,@taskImageDao)")
    public ResponseEntity<TaskImageDto> getTaskImage(@PathVariable("taskImageID") UUID taskImageID) {
        TaskImageDto taskImage = this.taskImageService.getTaskImage(taskImageID);
        return ResponseEntity.ok(taskImage);
    }

    @PostMapping(value = "/private/{taskID}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("@permissionHandler.isAssigned(#createTaskImageDto.taskID,@taskDao)")
    public ResponseEntity<TaskImageDto> uploadTaskImage(@PathVariable("taskID") UUID taskID,@ModelAttribute @Valid CreateTaskImageDto createTaskImageDto) {
        TaskImageDto taskImageDto = this.taskImageService.uploadImage(taskID,createTaskImageDto);
        return ResponseEntity.ok(taskImageDto);
    }

    @GetMapping("/private/task/{taskID}")
    @PreAuthorize("@permissionHandler.isMember(#taskID,@taskDao)")
    public ResponseEntity<TaskImageDto> getTaskImageByTask(@PathVariable("taskID") UUID taskID) {
        TaskImageDto taskImage = this.taskImageService.getTaskImageByTask(taskID);
        return ResponseEntity.ok(taskImage);
    }
}

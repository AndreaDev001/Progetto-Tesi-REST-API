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
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
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

    @GetMapping("/private/task/{taskID}")
    @PreAuthorize("@permissionHandler.hasBoardRole('MEMBER',#taskID,@taskDao)")
    public ResponseEntity<CollectionModel<TaskImageDto>> getTaskImages(@PathVariable("taskID") UUID taskID) {
        CollectionModel<TaskImageDto> taskImages = this.taskImageService.getTaskImages(taskID);
        return ResponseEntity.ok(taskImages);
    }

    @PostMapping(value = "/private/{taskID}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("@permissionHandler.isAssigned(#taskID,@taskDao)")
    public ResponseEntity<CollectionModel<TaskImageDto>> uploadTaskImage(@PathVariable("taskID") UUID taskID,@ModelAttribute @Valid CreateTaskImageDto createTaskImageDto) {
        CollectionModel<TaskImageDto> taskImages = this.taskImageService.uploadImage(taskID,createTaskImageDto);
        return ResponseEntity.ok(taskImages);
    }

    @GetMapping("/private/task/{taskID}/amount")
    @PreAuthorize("@permissionHandler.isAssigned(#taskID,@taskDao)")
    public ResponseEntity<Integer> getAmountOfImages(@PathVariable("taskID") UUID taskID) {
        Integer amountOfImages = this.taskImageService.getAmountOfImages(taskID);
        return ResponseEntity.ok(amountOfImages);
    }

    @GetMapping("/private/task/{taskID}/index/{index}")
    @PreAuthorize("@permissionHandler.hasBoardRole('MEMBER',#taskID,@taskDao)")
    public ResponseEntity<TaskImageDto> getImageByIndex(@PathVariable("taskID") UUID taskID,@PathVariable("index") Integer index) {
        TaskImageDto taskImageDto = this.taskImageService.getCurrentImage(taskID,index);
        return ResponseEntity.ok(taskImageDto);
    }

    @GetMapping("/private/task/{taskID}/index/{index}/image")
    @PreAuthorize("@permissionHandler.hasBoardRole('MEMBER',#taskID,@taskDao)")
    public ResponseEntity<byte[]> getImageByIndexAsBytes(@PathVariable("taskID") UUID taskID,@PathVariable("index") Integer index) {
        TaskImageDto taskImageDto = this.taskImageService.getCurrentImage(taskID,index);
        return ResponseEntity.ok().contentType(MediaType.valueOf(taskImageDto.getType().getName())).body(taskImageDto.getImage());
    }

    @GetMapping("/private/task/{taskID}/first/image")
    @PreAuthorize("@permissionHandler.hasBoardRole('MEMBER',#taskID,@taskDao)")
    public ResponseEntity<byte[]> getFirstImageAsBytes(@PathVariable("taskID") UUID taskID) {
        TaskImageDto taskImageDto = this.taskImageService.getFirstImage(taskID);
        return ResponseEntity.ok().contentType(MediaType.valueOf(taskImageDto.getType().getName())).body(taskImageDto.getImage());
    }

    @GetMapping("/private/task/{taskID}/last/image")
    @PreAuthorize("@permissionHandler.hasBoardRole('MEMBER',#taskID,@taskDao)")
    public ResponseEntity<byte[]> getLastImageAsBytes(@PathVariable("taskID") UUID taskID) {
        TaskImageDto taskImageDto = this.taskImageService.getLastImage(taskID);
        return ResponseEntity.ok().contentType(MediaType.valueOf(taskImageDto.getType().getName())).body(taskImageDto.getImage());
    }

    @GetMapping("/private/task/{taskID}/first")
    @PreAuthorize("@permissionHandler.hasBoardRole('MEMBER',#taskID,@taskDao)")
    public ResponseEntity<TaskImageDto> getFirstImage(@PathVariable("taskID") UUID taskID) {
        TaskImageDto taskImageDto = this.taskImageService.getFirstImage(taskID);
        return ResponseEntity.ok(taskImageDto);
    }

    @GetMapping("/private/task/{taskID}/last")
    @PreAuthorize("@permissionHandler.hasBoardRole('MEMBER',#taskID,@taskDao)")
    public ResponseEntity<TaskImageDto> getLastImage(@PathVariable("taskID") UUID taskID) {
        TaskImageDto taskImageDto = this.taskImageService.getLastImage(taskID);
        return ResponseEntity.ok(taskImageDto);
    }
}

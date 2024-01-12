package com.progettotirocinio.restapi.controllers.images;


import com.progettotirocinio.restapi.data.dto.input.PaginationRequest;
import com.progettotirocinio.restapi.data.dto.output.images.TaskImageDto;
import com.progettotirocinio.restapi.data.entities.images.TaskImage;
import com.progettotirocinio.restapi.services.interfaces.images.TaskImageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/taskImages")
public class TaskImageController
{
    private final TaskImageService taskImageService;
    @GetMapping("/private")
    public ResponseEntity<PagedModel<TaskImageDto>> getTaskImages(@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<TaskImageDto> taskImages = taskImageService.getTaskImages(paginationRequest.toPageRequest());
        return ResponseEntity.ok(taskImages);
    }

    @GetMapping("/private/{taskImageID}")
    public ResponseEntity<TaskImageDto> getTaskImage(@PathVariable("taskImageID") UUID taskImageID) {
        TaskImageDto taskImage = this.taskImageService.getTaskImage(taskImageID);
        return ResponseEntity.ok(taskImage);
    }

    @GetMapping("/private/task/{taskID}")
    public ResponseEntity<TaskImageDto> getTaskImageByTask(@PathVariable("taskID") UUID taskID) {
        TaskImageDto taskImage = this.taskImageService.getTaskImageByTask(taskID);
        return ResponseEntity.ok(taskImage);
    }
}

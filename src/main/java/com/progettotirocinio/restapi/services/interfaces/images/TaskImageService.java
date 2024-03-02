package com.progettotirocinio.restapi.services.interfaces.images;

import com.progettotirocinio.restapi.data.dto.input.create.images.CreateTaskImageDto;
import com.progettotirocinio.restapi.data.dto.output.images.TaskImageDto;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import java.util.UUID;

public interface TaskImageService
{
    PagedModel<TaskImageDto> getTaskImages(Pageable pageable);
    TaskImageDto getTaskImageByTask(UUID taskID);
    TaskImageDto getTaskImage(UUID taskImageID);
    TaskImageDto uploadImage(UUID taskID,CreateTaskImageDto createTaskImageDto);
}

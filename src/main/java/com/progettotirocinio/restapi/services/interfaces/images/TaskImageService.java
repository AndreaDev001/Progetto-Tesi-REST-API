package com.progettotirocinio.restapi.services.interfaces.images;

import com.progettotirocinio.restapi.data.dto.input.create.images.CreateTaskImageDto;
import com.progettotirocinio.restapi.data.dto.output.images.TaskImageDto;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;

import java.util.UUID;

public interface TaskImageService
{
    PagedModel<TaskImageDto> getTaskImages(Pageable pageable);
    CollectionModel<TaskImageDto> getTaskImages(UUID taskID);
    Integer getAmountOfImages(UUID taskID);
    TaskImageDto getCurrentImage(UUID taskID,Integer currentIndex);
    TaskImageDto getLastImage(UUID taskID);
    TaskImageDto getFirstImage(UUID taskID);
    TaskImageDto getTaskImage(UUID taskImageID);
    void deleteTaskImage(UUID taskImageID);
    CollectionModel<TaskImageDto> uploadImage(UUID taskID,CreateTaskImageDto createTaskImageDto);
}

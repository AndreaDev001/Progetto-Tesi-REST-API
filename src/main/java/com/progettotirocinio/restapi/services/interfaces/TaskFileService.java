package com.progettotirocinio.restapi.services.interfaces;

import com.progettotirocinio.restapi.data.dto.input.create.CreateTaskFileDto;
import com.progettotirocinio.restapi.data.dto.output.TaskFileDto;
import com.progettotirocinio.restapi.data.entities.enums.NotAllowedExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;

import java.util.UUID;

public interface TaskFileService
{
    PagedModel<TaskFileDto> getTaskFiles(Pageable pageable);
    PagedModel<TaskFileDto> getTaskFilesByPublisher(UUID publisherID,Pageable pageable);
    PagedModel<TaskFileDto> getTaskFilesByName(String name,Pageable pageable);
    CollectionModel<TaskFileDto> getTaskFilesByTask(UUID taskID);
    TaskFileDto getTaskFile(UUID taskFileID);
    TaskFileDto createTaskFile(CreateTaskFileDto createTaskFileDto);
    CollectionModel<NotAllowedExtension> getNotAllowedExtensions();
    void deleteTaskFile(UUID taskFileID);
}

package com.progettotirocinio.restapi.services.interfaces;

import com.progettotirocinio.restapi.data.dto.input.create.CreateTaskURLDto;
import com.progettotirocinio.restapi.data.dto.output.TaskURLDto;
import com.progettotirocinio.restapi.data.entities.TaskURL;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;

import java.util.UUID;

public interface TaskURLService
{
    PagedModel<TaskURLDto> getTaskURLs(Pageable pageable);
    PagedModel<TaskURLDto> getTaskURLsByPublisher(UUID publisherID,Pageable pageable);
    PagedModel<TaskURLDto> getTaskURLsByURL(String url,Pageable pageable);
    CollectionModel<TaskURLDto> getTaskURLsByTask(UUID taskID);
    TaskURLDto getTaskURL(UUID taskURLId);
    TaskURLDto createTaskURL(CreateTaskURLDto createTaskURLDto);
    void deleteTaskURL(UUID taskURLId);
}

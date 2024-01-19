package com.progettotirocinio.restapi.services.interfaces;

import com.progettotirocinio.restapi.data.dto.input.create.CreateTaskGroupDto;
import com.progettotirocinio.restapi.data.dto.output.TaskGroupDto;
import com.progettotirocinio.restapi.data.entities.Task;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import java.util.UUID;

public interface TaskGroupService
{
    PagedModel<TaskGroupDto> getTasks(Pageable pageable);
    PagedModel<TaskGroupDto> getTaskGroupsByPublisher(UUID publisherID,Pageable pageable);
    PagedModel<TaskGroupDto> getTaskGroupsByBoard(UUID boardID,Pageable pageable);
    PagedModel<TaskGroupDto> getTaskGroupsByName(String name,Pageable pageable);
    TaskGroupDto getTaskGroup(UUID id);
    TaskGroupDto createTaskGroup(CreateTaskGroupDto createTaskGroupDto);
    void deleteTaskGroup(UUID id);
}

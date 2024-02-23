package com.progettotirocinio.restapi.services.interfaces;

import com.progettotirocinio.restapi.data.dto.input.create.CreateTaskGroupDto;
import com.progettotirocinio.restapi.data.dto.input.update.UpdateTaskGroupDto;
import com.progettotirocinio.restapi.data.dto.output.TaskGroupDto;
import com.progettotirocinio.restapi.data.entities.Task;
import com.progettotirocinio.restapi.data.entities.enums.TaskGroupStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;

import java.util.UUID;

public interface TaskGroupService
{
    PagedModel<TaskGroupDto> getTasks(Pageable pageable);
    PagedModel<TaskGroupDto> getTaskGroupsByPublisher(UUID publisherID,Pageable pageable);
    CollectionModel<TaskGroupDto> getTaskGroupsByBoard(UUID boardID);
    PagedModel<TaskGroupDto> getTaskGroupsByName(String name,Pageable pageable);
    CollectionModel<TaskGroupStatus> getStatues();
    PagedModel<TaskGroupDto> getTaskGroupsByStatus(TaskGroupStatus taskGroupStatus,Pageable pageable);
    TaskGroupDto getTaskGroup(UUID id);
    TaskGroupDto createTaskGroup(CreateTaskGroupDto createTaskGroupDto);
    TaskGroupDto updateTaskGroup(UpdateTaskGroupDto updateTaskGroupDto);
    void handleExpiredTaskGroups();
    void deleteExpiredTaskGroups();
    void deleteTaskGroup(UUID id);
    TaskGroupDto clearTaskGroup(UUID id);
}

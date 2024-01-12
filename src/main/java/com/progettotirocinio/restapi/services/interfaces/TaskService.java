package com.progettotirocinio.restapi.services.interfaces;

import com.progettotirocinio.restapi.data.dto.output.TaskDto;
import com.progettotirocinio.restapi.data.entities.enums.Priority;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import java.util.UUID;

public interface TaskService {
    PagedModel<TaskDto> getTasks(Pageable pageable);
    PagedModel<TaskDto> getTasksByName(String name,Pageable pageable);
    PagedModel<TaskDto> getTasksByTitle(String title,Pageable pageable);
    PagedModel<TaskDto> getTasksByDescription(String description,Pageable pageable);
    PagedModel<TaskDto> getTasksByPublisher(UUID publisherID,Pageable pageable);
    PagedModel<TaskDto> getTasksByGroup(UUID groupID,Pageable pageable);
    PagedModel<TaskDto> getTasksByPriority(Priority priority,Pageable pageable);
    TaskDto getTask(UUID id);
    void deleteTask(UUID id);
}

package com.progettotirocinio.restapi.services.interfaces;

import com.progettotirocinio.restapi.data.dto.input.create.CreateTaskDto;
import com.progettotirocinio.restapi.data.dto.input.update.UpdateTaskDto;
import com.progettotirocinio.restapi.data.dto.output.TaskDto;
import com.progettotirocinio.restapi.data.entities.Task;
import com.progettotirocinio.restapi.data.entities.enums.Priority;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.hateoas.CollectionModel;
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
    PagedModel<TaskDto> getTasksBySpec(Specification<Task> specification,Pageable pageable);
    PagedModel<TaskDto> getSimilarTasks(UUID taskID,Pageable pageable);
    CollectionModel<Priority> getPriorities();
    CollectionModel<String> getOrderTypes();
    TaskDto getTask(UUID id);
    TaskDto createTask(CreateTaskDto createTaskDto);
    TaskDto updateTask(UpdateTaskDto updateTaskDto);
    void deleteTask(UUID id);
}

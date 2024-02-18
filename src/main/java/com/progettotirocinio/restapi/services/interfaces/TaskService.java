package com.progettotirocinio.restapi.services.interfaces;

import com.progettotirocinio.restapi.data.dto.input.create.CreateTaskDto;
import com.progettotirocinio.restapi.data.dto.input.update.UpdateTaskDto;
import com.progettotirocinio.restapi.data.dto.output.TaskDto;
import com.progettotirocinio.restapi.data.entities.Task;
import com.progettotirocinio.restapi.data.entities.enums.Priority;
import com.progettotirocinio.restapi.data.entities.enums.TaskStatus;
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
    CollectionModel<TaskDto> getTasksByGroup(UUID groupID);
    PagedModel<TaskDto> getTasksByPriority(Priority priority,Pageable pageable);
    PagedModel<TaskDto> getTasksBySpec(Specification<Task> specification,Pageable pageable);
    PagedModel<TaskDto> getSimilarTasks(UUID taskID,Pageable pageable);
    PagedModel<TaskDto> getTasksByStatus(TaskStatus status,Pageable pageable);
    CollectionModel<Priority> getPriorities();
    CollectionModel<String> getOrderTypes();
    CollectionModel<TaskStatus> getStatues();
    TaskDto getTask(UUID id);
    TaskDto createTask(CreateTaskDto createTaskDto);
    TaskDto updateTask(UpdateTaskDto updateTaskDto);
    void handleExpiredTasks();
    void deleteExpiredTasks();
    void deleteTask(UUID id);
}

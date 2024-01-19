package com.progettotirocinio.restapi.controllers;


import com.progettotirocinio.restapi.data.dao.specifications.TaskSpecifications;
import com.progettotirocinio.restapi.data.dto.input.PaginationRequest;
import com.progettotirocinio.restapi.data.dto.input.create.CreateTaskDto;
import com.progettotirocinio.restapi.data.dto.output.TaskDto;
import com.progettotirocinio.restapi.data.entities.Task;
import com.progettotirocinio.restapi.data.entities.enums.Priority;
import com.progettotirocinio.restapi.services.interfaces.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController
{
    private final TaskService taskService;

    @GetMapping("/private")
    public ResponseEntity<PagedModel<TaskDto>> getTasks(@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<TaskDto> tasks = this.taskService.getTasks(paginationRequest.toPageRequest());
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/private/{taskID}")
    public ResponseEntity<TaskDto> getTask(@PathVariable("taskID") UUID taskID) {
        TaskDto task = this.taskService.getTask(taskID);
        return ResponseEntity.ok(task);
    }

    @GetMapping("/private/name/{name}")
    public ResponseEntity<PagedModel<TaskDto>> getTasksByName(@PathVariable("name") String name,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<TaskDto> tasks = this.taskService.getTasksByName(name,paginationRequest.toPageRequest());
        return ResponseEntity.ok(tasks);
    }

    @PostMapping("/private")
    public ResponseEntity<TaskDto> createTask(@RequestBody @Valid CreateTaskDto createTaskDto) {
        TaskDto taskDto = this.taskService.createTask(createTaskDto);
        return ResponseEntity.status(201).body(taskDto);
    }

    @GetMapping("/private/spec")
    public ResponseEntity<PagedModel<TaskDto>> getTasksBySpec(@ParameterObject @Valid TaskSpecifications.Filter filter,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<TaskDto> tasks = this.taskService.getTasksBySpec(TaskSpecifications.withFilter(filter),paginationRequest.toPageRequest());
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/private/similar/{taskID}")
    public ResponseEntity<PagedModel<TaskDto>> getSimilarTasks(@PathVariable("taskID") UUID taskID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<TaskDto> tasks = this.taskService.getSimilarTasks(taskID,paginationRequest.toPageRequest());
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/public/priorities")
    public ResponseEntity<CollectionModel<Priority>> getPriorities() {
        CollectionModel<Priority> priorities = this.taskService.getPriorities();
        return ResponseEntity.ok(priorities);
    }

    @GetMapping("/public/orderTypes")
    public ResponseEntity<CollectionModel<String>> getOrderTypes() {
        CollectionModel<String> orderTypes = this.taskService.getOrderTypes();
        return ResponseEntity.ok(orderTypes);
    }

    @GetMapping("/private/publisher/{publisherID}")
    public ResponseEntity<PagedModel<TaskDto>> getTasksByPublisher(@PathVariable("publisherID") UUID publisherID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<TaskDto> tasks = this.taskService.getTasksByPublisher(publisherID,paginationRequest.toPageRequest());
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/private/description/{description}")
    public ResponseEntity<PagedModel<TaskDto>> getTasksByDescription(@PathVariable("description") String description,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<TaskDto> tasks = this.taskService.getTasksByDescription(description,paginationRequest.toPageRequest());
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/private/group/{groupID}")
    public ResponseEntity<PagedModel<TaskDto>> getTasksByGroup(@PathVariable("groupID") UUID groupID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<TaskDto> tasks = this.taskService.getTasksByGroup(groupID,paginationRequest.toPageRequest());
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/private/priority/{priority}")
    public ResponseEntity<PagedModel<TaskDto>> getTasksByPriority(@PathVariable("priority")Priority priority,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<TaskDto> tasks = this.taskService.getTasksByPriority(priority,paginationRequest.toPageRequest());
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/private/title/{title}")
    public ResponseEntity<PagedModel<TaskDto>> getTasksByTitle(@PathVariable("title") String title,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<TaskDto> tasks = this.taskService.getTasksByTitle(title,paginationRequest.toPageRequest());
        return ResponseEntity.ok(tasks);
    }

    @DeleteMapping("/private/{taskID}")
    public ResponseEntity<Void> deleteTask(@PathVariable("taskID") UUID taskID) {
        this.taskService.deleteTask(taskID);
        return ResponseEntity.noContent().build();
    }
}

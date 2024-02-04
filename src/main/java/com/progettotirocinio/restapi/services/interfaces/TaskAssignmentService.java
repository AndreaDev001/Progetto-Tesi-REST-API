package com.progettotirocinio.restapi.services.interfaces;

import com.progettotirocinio.restapi.data.dto.input.create.CreateTaskAssignmentDto;
import com.progettotirocinio.restapi.data.dto.output.TaskAssignmentDto;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import java.util.UUID;

public interface TaskAssignmentService
{
    PagedModel<TaskAssignmentDto> getTaskAssignments(Pageable pageable);
    TaskAssignmentDto getTaskAssignment(UUID taskAssignment);
    PagedModel<TaskAssignmentDto> getTaskAssignmentsByPublisher(UUID publisherID,Pageable pageable);
    PagedModel<TaskAssignmentDto> getTaskAssignmentsByTask(UUID taskID,Pageable pageable);
    PagedModel<TaskAssignmentDto> getTaskAssignmentsByUser(UUID userID,Pageable pageable);
    TaskAssignmentDto getTaskAssignment(UUID userID,UUID taskID);
    TaskAssignmentDto createTaskAssignment(CreateTaskAssignmentDto createTaskAssignmentDto);
    void deleteTaskAssignment(UUID taskAssignmentID);
}

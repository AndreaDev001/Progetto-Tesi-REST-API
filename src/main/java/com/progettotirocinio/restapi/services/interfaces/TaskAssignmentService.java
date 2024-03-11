package com.progettotirocinio.restapi.services.interfaces;

import com.progettotirocinio.restapi.data.dto.input.create.CreateTaskAssignmentDto;
import com.progettotirocinio.restapi.data.dto.output.TaskAssignmentDto;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;

import java.util.UUID;

public interface TaskAssignmentService
{
    PagedModel<TaskAssignmentDto> getTaskAssignments(Pageable pageable);
    TaskAssignmentDto getTaskAssignment(UUID taskAssignment);
    PagedModel<TaskAssignmentDto> getTaskAssignmentsByPublisher(UUID publisherID,Pageable pageable);
    CollectionModel<TaskAssignmentDto> getTaskAssignmentsByTask(UUID taskID);
    PagedModel<TaskAssignmentDto> getTaskAssignmentsByMember(UUID memberID,Pageable pageable);
    TaskAssignmentDto getTaskAssignment(UUID userID,UUID taskID);
    TaskAssignmentDto createTaskAssignment(CreateTaskAssignmentDto createTaskAssignmentDto);
    CollectionModel<TaskAssignmentDto> createTaskAssignmentFromTeam(UUID taskID,UUID teamID);
    void deleteTaskAssignment(UUID taskAssignmentID);
}

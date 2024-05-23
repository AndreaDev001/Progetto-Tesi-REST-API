package com.progettotirocinio.restapi.services.implementations;

import com.progettotirocinio.restapi.config.caching.CacheHandler;
import com.progettotirocinio.restapi.config.exceptions.InvalidFormat;
import com.progettotirocinio.restapi.config.mapper.Mapper;
import com.progettotirocinio.restapi.data.dao.*;
import com.progettotirocinio.restapi.data.dto.input.create.CreateTaskAssignmentDto;
import com.progettotirocinio.restapi.data.dto.output.TaskAssignmentDto;
import com.progettotirocinio.restapi.data.entities.*;
import com.progettotirocinio.restapi.services.interfaces.TaskAssignmentService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TaskAssignmentServiceImp extends GenericServiceImp<TaskAssignment, TaskAssignmentDto> implements TaskAssignmentService {

    private final TaskAssignmentDao taskAssignmentDao;
    private final TeamDao teamDao;
    private final TeamMemberDao teamMemberDao;
    private final TaskDao taskDao;
    private final BoardMemberDao boardMemberDao;

    public TaskAssignmentServiceImp(BoardMemberDao boardMember,TeamMemberDao teamMemberDao,TeamDao teamDao, TaskDao taskDao, TaskAssignmentDao taskAssignmentDao, CacheHandler cacheHandler, UserDao userDao, Mapper mapper,PagedResourcesAssembler<TaskAssignment> pagedResourcesAssembler) {
        super(cacheHandler, userDao, mapper,TaskAssignment.class,TaskAssignmentDto.class, pagedResourcesAssembler);
        this.taskAssignmentDao = taskAssignmentDao;
        this.taskDao = taskDao;
        this.boardMemberDao = boardMember;
        this.teamMemberDao = teamMemberDao;
        this.teamDao = teamDao;
    }

    @Override
    public PagedModel<TaskAssignmentDto> getTaskAssignments(Pageable pageable) {
        Page<TaskAssignment> taskAssignments = this.taskAssignmentDao.findAll(pageable);
        return this.pagedResourcesAssembler.toModel(taskAssignments,modelAssembler);
    }

    @Override
    public TaskAssignmentDto getTaskAssignment(UUID taskAssignmentID) {
        TaskAssignment taskAssignment = this.taskAssignmentDao.findById(taskAssignmentID).orElseThrow();
        return this.modelMapper.map(taskAssignment,TaskAssignmentDto.class);
    }

    @Override
    public PagedModel<TaskAssignmentDto> getTaskAssignmentsByPublisher(UUID publisherID, Pageable pageable) {
        Page<TaskAssignment> taskAssignments = this.taskAssignmentDao.getTaskAssignmentsByPublisher(publisherID,pageable);
        return this.pagedResourcesAssembler.toModel(taskAssignments,modelAssembler);
    }

    @Override
    public CollectionModel<TaskAssignmentDto> getTaskAssignmentsByTask(UUID taskID) {
        List<TaskAssignment> taskAssignments = this.taskAssignmentDao.getTaskAssignmentsByTask(taskID);
        return CollectionModel.of(taskAssignments.stream().map(taskAssignment -> this.modelMapper.map(taskAssignment,TaskAssignmentDto.class)).collect(Collectors.toList()));
    }

    @Override
    public PagedModel<TaskAssignmentDto> getTaskAssignmentsByMember(UUID memberID, Pageable pageable) {
        Page<TaskAssignment> taskAssignments = this.taskAssignmentDao.getTaskAssignmentsByMember(memberID,pageable);
        return this.pagedResourcesAssembler.toModel(taskAssignments,modelAssembler);
    }

    @Override
    @Transactional
    public TaskAssignmentDto createTaskAssignment(CreateTaskAssignmentDto createTaskAssignmentDto) {
        User publisher = this.userDao.findById(UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName())).orElseThrow();
        Task task = this.taskDao.findById(createTaskAssignmentDto.getTaskID()).orElseThrow();
        Optional<BoardMember> boardMemberOptional = this.boardMemberDao.getBoardMember(task.getGroup().getBoard().getId(),createTaskAssignmentDto.getUserID());
        if(boardMemberOptional.isEmpty())
            throw new InvalidFormat("error.taskAssignment.invalidUser");
        TaskAssignment taskAssignment = new TaskAssignment();
        taskAssignment.setMember(boardMemberOptional.get());
        taskAssignment.setPublisher(publisher);
        taskAssignment.setTask(task);
        taskAssignment = this.taskAssignmentDao.save(taskAssignment);
        return this.modelMapper.map(taskAssignment,TaskAssignmentDto.class);
    }

    @Override
    @Transactional
    public CollectionModel<TaskAssignmentDto> createTaskAssignmentFromTeam(UUID taskID, UUID teamID) {
        User authenticatedUser = this.userDao.findById(UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName())).orElseThrow();
        Task task = this.taskDao.findById(taskID).orElseThrow();
        Team team = this.teamDao.findById(teamID).orElseThrow();
        List<TeamMember> teamMembers = this.teamMemberDao.getTeamMembersByTeam(team.getId());
        if(!team.getBoard().getId().equals(task.getGroup().getBoard().getId()))
            throw new InvalidFormat("error.taskAssignment.invalidTeam");
        List<TaskAssignment> taskAssignments = new ArrayList<>();
        for(TeamMember member : teamMembers)
        {
            TaskAssignment taskAssignment = new TaskAssignment();
            if(this.taskAssignmentDao.getTaskAssignment(member.getMember().getUser().getId(),taskID).isPresent())
                continue;
            taskAssignment.setMember(member.getMember());
            taskAssignment.setTask(task);
            taskAssignment.setPublisher(authenticatedUser);
            taskAssignments.add(taskAssignment);
        }
        taskAssignments = this.taskAssignmentDao.saveAll(taskAssignments);
        return CollectionModel.of(taskAssignments.stream().map(assignment -> this.modelMapper.map(assignment,TaskAssignmentDto.class)).collect(Collectors.toList()));
    }

    @Override
    @Transactional
    public void deleteTaskAssignment(UUID taskAssignmentID) {
        this.taskAssignmentDao.findById(taskAssignmentID).orElseThrow();
        this.taskAssignmentDao.deleteById(taskAssignmentID);
    }

    @Override
    public TaskAssignmentDto getTaskAssignment(UUID userID, UUID taskID) {
        TaskAssignment taskAssignment = this.taskAssignmentDao.getTaskAssignment(userID,taskID).orElseThrow();
        return this.modelMapper.map(taskAssignment,TaskAssignmentDto.class);
    }
}

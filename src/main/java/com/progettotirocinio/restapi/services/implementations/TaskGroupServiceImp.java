package com.progettotirocinio.restapi.services.implementations;


import com.progettotirocinio.restapi.config.mapper.Mapper;
import com.progettotirocinio.restapi.data.dao.BoardDao;
import com.progettotirocinio.restapi.data.dao.TaskGroupDao;
import com.progettotirocinio.restapi.data.dao.UserDao;
import com.progettotirocinio.restapi.data.dto.input.create.CreateTaskGroupDto;
import com.progettotirocinio.restapi.data.dto.output.TaskGroupDto;
import com.progettotirocinio.restapi.data.entities.Board;
import com.progettotirocinio.restapi.data.entities.TaskGroup;
import com.progettotirocinio.restapi.data.entities.User;
import com.progettotirocinio.restapi.services.interfaces.TaskGroupService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TaskGroupServiceImp extends GenericServiceImp<TaskGroup, TaskGroupDto> implements TaskGroupService
{

    private final TaskGroupDao taskGroupDao;
    private final BoardDao boardDao;

    public TaskGroupServiceImp(Mapper mapper, UserDao userDao, BoardDao boardDao, TaskGroupDao taskGroupDao, PagedResourcesAssembler<TaskGroup> pagedResourcesAssembler) {
        super(userDao,mapper,TaskGroup.class,TaskGroupDto.class, pagedResourcesAssembler);
        this.taskGroupDao = taskGroupDao;
        this.boardDao = boardDao;
    }

    @Override
    public PagedModel<TaskGroupDto> getTasks(Pageable pageable) {
        Page<TaskGroup> taskGroups = this.taskGroupDao.findAll(pageable);
        return this.pagedResourcesAssembler.toModel(taskGroups,modelAssembler);
    }

    @Override
    public PagedModel<TaskGroupDto> getTaskGroupsByPublisher(UUID publisherID, Pageable pageable) {
        Page<TaskGroup> taskGroups = this.taskGroupDao.getTaskGroupsByPublisher(publisherID,pageable);
        return this.pagedResourcesAssembler.toModel(taskGroups,modelAssembler);
    }

    @Override
    public PagedModel<TaskGroupDto> getTaskGroupsByBoard(UUID boardID, Pageable pageable) {
        Page<TaskGroup> taskGroups = this.taskGroupDao.getTaskGroupsByBoard(boardID,pageable);
        return this.pagedResourcesAssembler.toModel(taskGroups,modelAssembler);
    }

    @Override
    public PagedModel<TaskGroupDto> getTaskGroupsByName(String name, Pageable pageable) {
        Page<TaskGroup> taskGroups = this.taskGroupDao.getTaskGroupsByName(name,pageable);
        return this.pagedResourcesAssembler.toModel(taskGroups,modelAssembler);
    }

    @Override
    public TaskGroupDto getTaskGroup(UUID id) {
        TaskGroup taskGroup = this.taskGroupDao.findById(id).orElseThrow();
        return this.modelMapper.map(taskGroup,TaskGroupDto.class);
    }

    @Override
    public TaskGroupDto createTaskGroup(CreateTaskGroupDto createTaskGroupDto) {
        User publisher = this.userDao.findById(UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName())).orElseThrow();
        Board board = this.boardDao.findById(createTaskGroupDto.getBoardID()).orElseThrow();
        TaskGroup taskGroup = new TaskGroup();
        taskGroup.setBoard(board);
        taskGroup.setPublisher(publisher);
        taskGroup.setName(createTaskGroupDto.getName());
        taskGroup.setExpirationDate(createTaskGroupDto.getExpirationDate());
        taskGroup = this.taskGroupDao.save(taskGroup);
        return this.modelMapper.map(taskGroup,TaskGroupDto.class);
    }

    @Override
    public void deleteTaskGroup(UUID id) {
        this.taskGroupDao.findById(id).orElseThrow();
        this.taskGroupDao.deleteById(id);
    }
}

package com.progettotirocinio.restapi.services.implementations;


import com.progettotirocinio.restapi.config.caching.CacheHandler;
import com.progettotirocinio.restapi.config.caching.RequiresCaching;
import com.progettotirocinio.restapi.config.mapper.Mapper;
import com.progettotirocinio.restapi.data.dao.BoardDao;
import com.progettotirocinio.restapi.data.dao.TaskGroupDao;
import com.progettotirocinio.restapi.data.dao.UserDao;
import com.progettotirocinio.restapi.data.dto.input.create.CreateTaskGroupDto;
import com.progettotirocinio.restapi.data.dto.input.update.UpdateTaskGroupDto;
import com.progettotirocinio.restapi.data.dto.output.TaskGroupDto;
import com.progettotirocinio.restapi.data.entities.Board;
import com.progettotirocinio.restapi.data.entities.TaskGroup;
import com.progettotirocinio.restapi.data.entities.User;
import com.progettotirocinio.restapi.data.entities.enums.TaskGroupStatus;
import com.progettotirocinio.restapi.services.interfaces.TaskGroupService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiresCaching(allCacheName = "ALL_TASK_GROUPS")
public class TaskGroupServiceImp extends GenericServiceImp<TaskGroup, TaskGroupDto> implements TaskGroupService
{

    private final TaskGroupDao taskGroupDao;
    private final BoardDao boardDao;

    public TaskGroupServiceImp(CacheHandler cacheHandler,Mapper mapper, UserDao userDao, BoardDao boardDao, TaskGroupDao taskGroupDao, PagedResourcesAssembler<TaskGroup> pagedResourcesAssembler) {
        super(cacheHandler,userDao,mapper,TaskGroup.class,TaskGroupDto.class, pagedResourcesAssembler);
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
    public CollectionModel<TaskGroupDto> getTaskGroupsByBoard(UUID boardID) {
        List<TaskGroup> taskGroups = this.taskGroupDao.getTaskGroupsByBoard(boardID);
        return CollectionModel.of(taskGroups.stream().map(current -> this.modelMapper.map(current,TaskGroupDto.class)).collect(Collectors.toList()));
    }

    @Override
    public PagedModel<TaskGroupDto> getTaskGroupsByName(String name, Pageable pageable) {
        Page<TaskGroup> taskGroups = this.taskGroupDao.getTaskGroupsByName(name,pageable);
        return this.pagedResourcesAssembler.toModel(taskGroups,modelAssembler);
    }

    @Override
    public CollectionModel<TaskGroupStatus> getStatues() {
        return CollectionModel.of(Arrays.stream(TaskGroupStatus.values()).toList());
    }

    @Override
    public PagedModel<TaskGroupDto> getTaskGroupsByStatus(TaskGroupStatus taskGroupStatus, Pageable pageable) {
        Page<TaskGroup> taskGroups = this.taskGroupDao.getTaskGroupsByStatus(taskGroupStatus,pageable);
        return this.pagedResourcesAssembler.toModel(taskGroups,modelAssembler);
    }

    @Override
    public TaskGroupDto getTaskGroup(UUID id) {
        TaskGroup taskGroup = this.taskGroupDao.findById(id).orElseThrow();
        return this.modelMapper.map(taskGroup,TaskGroupDto.class);
    }

    @Override
    @Transactional
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
    @Transactional
    public TaskGroupDto updateTaskGroup(UpdateTaskGroupDto updateTaskGroupDto) {
        TaskGroup taskGroup = this.taskGroupDao.findById(updateTaskGroupDto.getGroupID()).orElseThrow();
        if(updateTaskGroupDto.getName() != null)
            taskGroup.setName(updateTaskGroupDto.getName());
        if(updateTaskGroupDto.getExpirationDate() != null)
            taskGroup.setExpirationDate(updateTaskGroupDto.getExpirationDate());
        if(updateTaskGroupDto.getOrder() != null)
            taskGroup.setCurrentOrder(updateTaskGroupDto.getOrder());
        taskGroup = this.taskGroupDao.save(taskGroup);
        return this.modelMapper.map(taskGroup,TaskGroupDto.class);
    }

    @Override
    @Transactional
    public void handleExpiredTaskGroups() {
        List<TaskGroup> taskGroups = this.taskGroupDao.getTaskGroupsByDate(LocalDate.now());
        for(TaskGroup current : taskGroups)
            current.setStatus(TaskGroupStatus.EXPIRED);
        this.taskGroupDao.saveAll(taskGroups);
    }

    @Override
    @Transactional
    public void deleteExpiredTaskGroups() {
        List<TaskGroup> taskGroups = this.taskGroupDao.getTaskGroupsByStatus(TaskGroupStatus.EXPIRED);
        this.taskGroupDao.deleteAll(taskGroups);
    }

    @Override
    @Transactional
    public void deleteTaskGroup(UUID id) {
        this.taskGroupDao.findById(id).orElseThrow();
        this.taskGroupDao.deleteById(id);
    }
}

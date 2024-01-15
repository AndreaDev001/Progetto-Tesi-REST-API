package com.progettotirocinio.restapi.services.implementations;


import com.progettotirocinio.restapi.config.mapper.Mapper;
import com.progettotirocinio.restapi.data.dao.TaskGroupDao;
import com.progettotirocinio.restapi.data.dto.output.TaskGroupDto;
import com.progettotirocinio.restapi.data.entities.TaskGroup;
import com.progettotirocinio.restapi.services.interfaces.TaskGroupService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TaskGroupServiceImp extends GenericServiceImp<TaskGroup, TaskGroupDto> implements TaskGroupService
{

    private final TaskGroupDao taskGroupDao;

    public TaskGroupServiceImp(Mapper mapper, TaskGroupDao taskGroupDao, PagedResourcesAssembler<TaskGroup> pagedResourcesAssembler) {
        super(mapper,TaskGroup.class,TaskGroupDto.class, pagedResourcesAssembler);
        this.taskGroupDao = taskGroupDao;
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
    public void deleteTaskGroup(UUID id) {
        this.taskGroupDao.findById(id).orElseThrow();
        this.taskGroupDao.deleteById(id);
    }
}

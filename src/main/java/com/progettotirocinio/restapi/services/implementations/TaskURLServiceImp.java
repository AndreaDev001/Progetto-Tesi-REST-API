package com.progettotirocinio.restapi.services.implementations;

import com.progettotirocinio.restapi.config.caching.CacheHandler;
import com.progettotirocinio.restapi.config.exceptions.InvalidFormat;
import com.progettotirocinio.restapi.config.mapper.Mapper;
import com.progettotirocinio.restapi.data.dao.BoardMemberDao;
import com.progettotirocinio.restapi.data.dao.TaskDao;
import com.progettotirocinio.restapi.data.dao.TaskUrlDao;
import com.progettotirocinio.restapi.data.dao.UserDao;
import com.progettotirocinio.restapi.data.dto.input.create.CreateTaskURLDto;
import com.progettotirocinio.restapi.data.dto.output.TaskURLDto;
import com.progettotirocinio.restapi.data.entities.BoardMember;
import com.progettotirocinio.restapi.data.entities.Task;
import com.progettotirocinio.restapi.data.entities.TaskURL;
import com.progettotirocinio.restapi.services.interfaces.TaskURLService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TaskURLServiceImp extends GenericServiceImp<TaskURL, TaskURLDto> implements TaskURLService
{
    private final TaskUrlDao taskUrlDao;
    private final BoardMemberDao boardMemberDao;
    private final TaskDao taskDao;

    public TaskURLServiceImp(CacheHandler cacheHandler,TaskDao taskDao,TaskUrlDao taskUrlDao,BoardMemberDao boardMemberDao, UserDao userDao, Mapper mapper, PagedResourcesAssembler<TaskURL> pagedResourcesAssembler) {
        super(cacheHandler, userDao, mapper,TaskURL.class,TaskURLDto.class, pagedResourcesAssembler);
        this.taskUrlDao = taskUrlDao;
        this.boardMemberDao = boardMemberDao;
        this.taskDao = taskDao;
    }

    @Override
    public PagedModel<TaskURLDto> getTaskURLs(Pageable pageable) {
        Page<TaskURL> taskURLS = this.taskUrlDao.findAll(pageable);
        return this.pagedResourcesAssembler.toModel(taskURLS,modelAssembler);
    }

    @Override
    public PagedModel<TaskURLDto> getTaskURLsByPublisher(UUID publisherID, Pageable pageable) {
        Page<TaskURL> taskURLS = this.taskUrlDao.getTaskURLsFromPublisher(publisherID,pageable);
        return this.pagedResourcesAssembler.toModel(taskURLS,modelAssembler);
    }

    @Override
    public PagedModel<TaskURLDto> getTaskURLsByName(String name, Pageable pageable) {
        Page<TaskURL> taskURLS = this.taskUrlDao.getTaskURLSFromName(name,pageable);
        return this.pagedResourcesAssembler.toModel(taskURLS,modelAssembler);
    }

    @Override
    public PagedModel<TaskURLDto> getTaskURLsByURL(String url, Pageable pageable) {
        Page<TaskURL> taskURLS = this.taskUrlDao.getTaskURLsFromURL(url,pageable);
        return this.pagedResourcesAssembler.toModel(taskURLS,modelAssembler);
    }

    @Override
    public CollectionModel<TaskURLDto> getTaskURLsByTask(UUID taskID) {
        List<TaskURL> taskURLS = this.taskUrlDao.getTaskURLsFromTask(taskID);
        return CollectionModel.of(taskURLS.stream().map(taskURL -> this.modelMapper.map(taskURL,TaskURLDto.class)).collect(Collectors.toList()));
    }

    @Override
    public TaskURLDto getTaskURL(UUID taskURLId) {
        TaskURL taskURL = this.taskUrlDao.findById(taskURLId).orElseThrow();
        return this.modelMapper.map(taskURL,TaskURLDto.class);
    }

    @Override
    @Transactional
    public TaskURLDto createTaskURL(CreateTaskURLDto createTaskURLDto) {
        Task task = this.taskDao.findById(createTaskURLDto.getTaskID()).orElseThrow();
        Optional<BoardMember> boardMemberOptional = this.boardMemberDao.getBoardMember(task.getBoardID(),UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName()));
        if(boardMemberOptional.isPresent()) {
            TaskURL taskURL = new TaskURL();
            taskURL.setName(createTaskURLDto.getName());
            taskURL.setUrl(createTaskURLDto.getUrl());
            taskURL.setTask(task);
            taskURL.setPublisher(boardMemberOptional.get());
            taskURL = this.taskUrlDao.save(taskURL);
            return this.modelMapper.map(taskURL,TaskURLDto.class);
        }
        else
            throw new InvalidFormat("error.taskURL.invalidPublisher");
    }

    @Override
    @Transactional
    public void deleteTaskURL(UUID taskURLId) {
        this.taskUrlDao.findById(taskURLId).orElseThrow();
        this.taskUrlDao.deleteById(taskURLId);
    }
}

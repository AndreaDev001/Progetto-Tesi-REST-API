package com.progettotirocinio.restapi.services.implementations;

import com.nimbusds.jose.proc.SecurityContext;
import com.progettotirocinio.restapi.config.SecurityConfig;
import com.progettotirocinio.restapi.config.caching.CacheHandler;
import com.progettotirocinio.restapi.config.exceptions.InvalidFormat;
import com.progettotirocinio.restapi.config.mapper.Mapper;
import com.progettotirocinio.restapi.data.dao.BoardMemberDao;
import com.progettotirocinio.restapi.data.dao.TaskDao;
import com.progettotirocinio.restapi.data.dao.TaskFileDao;
import com.progettotirocinio.restapi.data.dao.UserDao;
import com.progettotirocinio.restapi.data.dto.input.create.CreateTaskFileDto;
import com.progettotirocinio.restapi.data.dto.output.TaskFileDto;
import com.progettotirocinio.restapi.data.entities.BoardMember;
import com.progettotirocinio.restapi.data.entities.Task;
import com.progettotirocinio.restapi.data.entities.TaskFile;
import com.progettotirocinio.restapi.data.entities.enums.NotAllowedExtension;
import com.progettotirocinio.restapi.services.interfaces.TaskFileService;
import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TaskFileServiceImp extends GenericServiceImp<TaskFile, TaskFileDto> implements TaskFileService
{
    private final TaskFileDao taskFileDao;
    private final TaskDao taskDao;
    private final BoardMemberDao boardMemberDao;

    public TaskFileServiceImp(CacheHandler cacheHandler,TaskFileDao taskFileDao,BoardMemberDao boardMemberDao,TaskDao taskDao, UserDao userDao, Mapper mapper, PagedResourcesAssembler<TaskFile> pagedResourcesAssembler) {
        super(cacheHandler, userDao, mapper,TaskFile.class,TaskFileDto.class, pagedResourcesAssembler);
        this.taskFileDao = taskFileDao;
        this.taskDao = taskDao;
        this.boardMemberDao = boardMemberDao;
    }

    @Override
    public PagedModel<TaskFileDto> getTaskFiles(Pageable pageable) {
        Page<TaskFile> taskFiles = this.taskFileDao.findAll(pageable);
        return this.pagedResourcesAssembler.toModel(taskFiles,modelAssembler);
    }

    @Override
    public PagedModel<TaskFileDto> getTaskFilesByPublisher(UUID publisherID, Pageable pageable) {
        Page<TaskFile> taskFiles = this.taskFileDao.getTaskFilesByPublisher(publisherID,pageable);
        return this.pagedResourcesAssembler.toModel(taskFiles,modelAssembler);
    }

    @Override
    public PagedModel<TaskFileDto> getTaskFilesByName(String name, Pageable pageable) {
        Page<TaskFile> taskFiles = this.taskFileDao.getTaskFilesByName(name,pageable);
        return this.pagedResourcesAssembler.toModel(taskFiles,modelAssembler);
    }

    @Override
    public CollectionModel<TaskFileDto> getTaskFilesByTask(UUID taskID) {
        List<TaskFile> taskFiles = this.taskFileDao.getTaskFilesByTask(taskID);
        return CollectionModel.of(taskFiles.stream().map(taskFile -> this.modelMapper.map(taskFile,TaskFileDto.class)).collect(Collectors.toList()));
    }

    @Override
    public TaskFileDto getTaskFile(UUID taskFileID) {
        TaskFile taskFile = this.taskFileDao.findById(taskFileID).orElseThrow();
        return this.modelMapper.map(taskFile,TaskFileDto.class);
    }

    @Override
    @Transactional
    @SneakyThrows
    public TaskFileDto createTaskFile(CreateTaskFileDto createTaskFileDto) {
        Task task = this.taskDao.findById(createTaskFileDto.getTaskID()).orElseThrow();
        BoardMember boardMember = this.boardMemberDao.getBoardMember(task.getBoardID(),UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName())).orElseThrow();
        TaskFile taskFile = new TaskFile();
        taskFile.setFileName(createTaskFileDto.getMultipartFile().getOriginalFilename());
        taskFile.setName(createTaskFileDto.getName());
        for(NotAllowedExtension current : NotAllowedExtension.values()) {
            if(Objects.equals(createTaskFileDto.getMultipartFile().getContentType(), current.getName())) {
                throw new InvalidFormat("error.taskFiles.invalidType");
            }
        }
        taskFile.setPublisher(boardMember);
        taskFile.setTask(task);
        taskFile.setType(createTaskFileDto.getMultipartFile().getContentType());
        taskFile.setFile(createTaskFileDto.getMultipartFile().getBytes());
        taskFile = this.taskFileDao.save(taskFile);
        return this.modelMapper.map(taskFile,TaskFileDto.class);
    }

    @Override
    public CollectionModel<NotAllowedExtension> getNotAllowedExtensions() {
        NotAllowedExtension[] notAllowedExtensions = NotAllowedExtension.values();
        return CollectionModel.of(Arrays.stream(notAllowedExtensions).toList());
    }

    @Override
    @Transactional
    public void deleteTaskFile(UUID taskFileID) {
        this.taskFileDao.findById(taskFileID).orElseThrow();
        this.taskFileDao.deleteById(taskFileID);
    }
}

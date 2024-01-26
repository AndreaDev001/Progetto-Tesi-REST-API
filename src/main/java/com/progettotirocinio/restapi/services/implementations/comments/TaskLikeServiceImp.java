package com.progettotirocinio.restapi.services.implementations.comments;

import com.progettotirocinio.restapi.config.mapper.Mapper;
import com.progettotirocinio.restapi.data.dao.UserDao;
import com.progettotirocinio.restapi.data.dao.likes.TaskLikeDao;
import com.progettotirocinio.restapi.data.dto.output.likes.TaskLikeDto;
import com.progettotirocinio.restapi.data.entities.Task;
import com.progettotirocinio.restapi.data.entities.likes.TaskLike;
import com.progettotirocinio.restapi.services.implementations.GenericServiceImp;
import com.progettotirocinio.restapi.services.interfaces.likes.TaskLikeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TaskLikeServiceImp extends GenericServiceImp<TaskLike, TaskLikeDto> implements TaskLikeService
{
    private final TaskLikeDao taskLikeDao;

    public TaskLikeServiceImp(TaskLikeDao taskLikeDao,UserDao userDao, Mapper mapper, PagedResourcesAssembler<TaskLike> pagedResourcesAssembler) {
        super(userDao, mapper, TaskLike.class,TaskLikeDto.class, pagedResourcesAssembler);
        this.taskLikeDao = taskLikeDao;
    }

    @Override
    public PagedModel<TaskLikeDto> getTaskLikes(Pageable pageable) {
        Page<TaskLike> taskLikes = this.taskLikeDao.findAll(pageable);
        return this.pagedResourcesAssembler.toModel(taskLikes,modelAssembler);
    }

    @Override
    public PagedModel<TaskLikeDto> getTaskLikesByUser(UUID userID, Pageable pageable) {
        Page<TaskLike> taskLikes = this.taskLikeDao.getTaskLikesByUser(userID,pageable);
        return this.pagedResourcesAssembler.toModel(taskLikes,modelAssembler);
    }

    @Override
    public PagedModel<TaskLikeDto> getTaskLikesByTask(UUID taskID, Pageable pageable) {
        Page<TaskLike> taskLikes = this.taskLikeDao.getTaskLikesByTask(taskID,pageable);
        return this.pagedResourcesAssembler.toModel(taskLikes,modelAssembler);
    }

    @Override
    public TaskLikeDto getTaskLike(UUID taskLikeID) {
        TaskLike taskLike = this.taskLikeDao.findById(taskLikeID).orElseThrow();
        return this.modelMapper.map(taskLike,TaskLikeDto.class);
    }

    @Override
    public TaskLikeDto hasLike(UUID userID, UUID taskID) {
        TaskLike taskLike = this.taskLikeDao.hasLike(taskID,userID).orElseThrow();
        return this.modelMapper.map(taskLike,TaskLikeDto.class);
    }

    @Override
    public void deleteLike(UUID taskLikeID) {
        this.taskLikeDao.findById(taskLikeID);
        this.taskLikeDao.deleteById(taskLikeID);
    }
}

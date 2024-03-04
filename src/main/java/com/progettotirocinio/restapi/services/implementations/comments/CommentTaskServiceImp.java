package com.progettotirocinio.restapi.services.implementations.comments;

import com.progettotirocinio.restapi.config.caching.CacheHandler;
import com.progettotirocinio.restapi.config.mapper.Mapper;
import com.progettotirocinio.restapi.data.dao.UserDao;
import com.progettotirocinio.restapi.data.dao.comments.CommentTaskDao;
import com.progettotirocinio.restapi.data.dto.input.create.CreateCommentDto;
import com.progettotirocinio.restapi.data.dto.output.comments.CommentTaskDto;
import com.progettotirocinio.restapi.data.entities.comments.CommentTask;
import com.progettotirocinio.restapi.services.implementations.GenericServiceImp;
import com.progettotirocinio.restapi.services.interfaces.comments.CommentTaskService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CommentTaskServiceImp extends GenericServiceImp<CommentTask, CommentTaskDto> implements CommentTaskService
{

    private final CommentTaskDao commentTaskDao;

    public CommentTaskServiceImp(CacheHandler cacheHandler,CommentTaskDao commentTaskDao, UserDao userDao, Mapper mapper, PagedResourcesAssembler<CommentTask> pagedResourcesAssembler) {
        super(cacheHandler, userDao, mapper,CommentTask.class,CommentTaskDto.class, pagedResourcesAssembler);
        this.commentTaskDao = commentTaskDao;
    }

    @Override
    public PagedModel<CommentTaskDto> getComments(Pageable pageable) {
        Page<CommentTask> commentTasks = this.commentTaskDao.findAll(pageable);
        return this.pagedResourcesAssembler.toModel(commentTasks,modelAssembler);
    }

    @Override
    public CollectionModel<CommentTaskDto> getCommentsByTask(UUID taskID) {
        List<CommentTask> commentTasks = this.commentTaskDao.getCommentsByTask(taskID);
        return CollectionModel.of(commentTasks.stream().map(commentTask -> this.modelMapper.map(commentTask,CommentTaskDto.class)).collect(Collectors.toList()));
    }

    @Override
    public CommentTaskDto getCommentTaskByID(UUID commentID) {
        CommentTask commentTask = this.commentTaskDao.findById(commentID).orElseThrow();
        return this.modelMapper.map(commentTask,CommentTaskDto.class);
    }

    @Override
    public CommentTaskDto getCommentByTaskAndPublisher(UUID taskID, UUID publisherID) {
        CommentTask commentTask = this.commentTaskDao.getCommentByTaskAndPublisher(taskID,publisherID).orElseThrow();
        return this.modelMapper.map(commentTask,CommentTaskDto.class);
    }

    @Override
    public CommentTaskDto createTaskComment(UUID taskID, CreateCommentDto createCommentDto) {
        return null;
    }

    @Override
    @Transactional
    public void deleteTaskComment(UUID taskCommentID) {
        this.commentTaskDao.findById(taskCommentID).orElseThrow();
        this.commentTaskDao.deleteById(taskCommentID);
    }
}

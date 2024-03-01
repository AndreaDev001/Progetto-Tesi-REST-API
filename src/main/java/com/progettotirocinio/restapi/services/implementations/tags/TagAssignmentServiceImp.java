package com.progettotirocinio.restapi.services.implementations.tags;

import com.progettotirocinio.restapi.config.caching.CacheHandler;
import com.progettotirocinio.restapi.config.exceptions.InvalidFormat;
import com.progettotirocinio.restapi.config.mapper.Mapper;
import com.progettotirocinio.restapi.data.dao.TaskDao;
import com.progettotirocinio.restapi.data.dao.UserDao;
import com.progettotirocinio.restapi.data.dao.tags.TagAssignmentDao;
import com.progettotirocinio.restapi.data.dao.tags.TagDao;
import com.progettotirocinio.restapi.data.dto.input.create.tags.CreateTagAssignmentDto;
import com.progettotirocinio.restapi.data.dto.output.tags.TagAssignmentDto;
import com.progettotirocinio.restapi.data.entities.Task;
import com.progettotirocinio.restapi.data.entities.User;
import com.progettotirocinio.restapi.data.entities.tags.Tag;
import com.progettotirocinio.restapi.data.entities.tags.TagAssignment;
import com.progettotirocinio.restapi.services.implementations.GenericServiceImp;
import com.progettotirocinio.restapi.services.interfaces.tags.TagAssignmentService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TagAssignmentServiceImp extends GenericServiceImp<TagAssignment, TagAssignmentDto> implements TagAssignmentService
{
    private final TagDao tagDao;
    private final TagAssignmentDao tagAssignmentDao;
    private final TaskDao taskDao;

    public TagAssignmentServiceImp(CacheHandler cacheHandler,TagDao tagDao,TagAssignmentDao tagAssignmentDao,TaskDao taskDao, UserDao userDao, Mapper mapper, PagedResourcesAssembler<TagAssignment> pagedResourcesAssembler) {
        super(cacheHandler, userDao, mapper, TagAssignment.class,TagAssignmentDto.class, pagedResourcesAssembler);
        this.tagDao = tagDao;
        this.tagAssignmentDao = tagAssignmentDao;
        this.taskDao = taskDao;
    }

    @Override
    public PagedModel<TagAssignmentDto> getTags(Pageable pageable) {
        Page<TagAssignment> tagAssignments = this.tagAssignmentDao.findAll(pageable);
        return this.pagedResourcesAssembler.toModel(tagAssignments,modelAssembler);
    }

    @Override
    public CollectionModel<TagAssignmentDto> getTagAssignmentsByTask(UUID taskID) {
        List<TagAssignment> tagAssignments = this.tagAssignmentDao.getTagAssignmentsByTask(taskID);
        return CollectionModel.of(tagAssignments.stream().map(tagAssignment -> this.modelMapper.map(tagAssignment,TagAssignmentDto.class)).collect(Collectors.toList()));
    }

    @Override
    public CollectionModel<TagAssignmentDto> getTagAssignmentsByTag(UUID tagID) {
        List<TagAssignment> tagAssignments = this.tagAssignmentDao.getTagAssignmentsByTag(tagID);
        return CollectionModel.of(tagAssignments.stream().map(tagAssignment -> this.modelMapper.map(tagAssignment,TagAssignmentDto.class)).collect(Collectors.toList()));
    }

    @Override
    public TagAssignmentDto findTagAssignment(UUID tagAssignmentID) {
        TagAssignment tagAssignment = this.tagAssignmentDao.findById(tagAssignmentID).orElseThrow();
        return this.modelMapper.map(tagAssignment,TagAssignmentDto.class);
    }

    @Override
    @Transactional
    public TagAssignmentDto createTagAssignment(CreateTagAssignmentDto createTagAssignmentDto) {
        Tag tag = this.tagDao.findById(createTagAssignmentDto.getTagID()).orElseThrow();
        Task task = this.taskDao.findById(createTagAssignmentDto.getTaskID()).orElseThrow();
        if(!task.getGroup().getBoard().getId().equals(tag.getBoard().getId()))
            throw new InvalidFormat("error.tagAssignment.invalidBoard");
        TagAssignment tagAssignment = new TagAssignment();
        tagAssignment.setTag(tag);
        tagAssignment.setTask(task);
        tagAssignment = this.tagAssignmentDao.save(tagAssignment);
        return this.modelMapper.map(tagAssignment,TagAssignmentDto.class);
    }

    @Override
    @Transactional
    public void deleteTagAssignment(UUID tagAssignmentID) {
        this.tagAssignmentDao.findById(tagAssignmentID).orElseThrow();
        this.tagAssignmentDao.deleteById(tagAssignmentID);
    }
}

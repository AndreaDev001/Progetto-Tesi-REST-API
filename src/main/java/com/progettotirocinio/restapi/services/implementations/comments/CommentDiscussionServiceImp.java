package com.progettotirocinio.restapi.services.implementations.comments;

import com.progettotirocinio.restapi.config.caching.CacheHandler;
import com.progettotirocinio.restapi.config.mapper.Mapper;
import com.progettotirocinio.restapi.data.dao.UserDao;
import com.progettotirocinio.restapi.data.dao.comments.CommentDiscussionDao;
import com.progettotirocinio.restapi.data.dto.input.create.CreateCommentDto;
import com.progettotirocinio.restapi.data.dto.output.comments.CommentDiscussionDto;
import com.progettotirocinio.restapi.data.entities.comments.CommentDiscussion;
import com.progettotirocinio.restapi.services.implementations.GenericServiceImp;
import com.progettotirocinio.restapi.services.interfaces.comments.CommentDiscussionService;
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
public class CommentDiscussionServiceImp extends GenericServiceImp<CommentDiscussion, CommentDiscussionDto> implements CommentDiscussionService
{
    private final CommentDiscussionDao commentDiscussionDao;

    public CommentDiscussionServiceImp(CacheHandler cacheHandler,CommentDiscussionDao commentDiscussionDao, UserDao userDao, Mapper mapper,  PagedResourcesAssembler<CommentDiscussion> pagedResourcesAssembler) {
        super(cacheHandler, userDao, mapper, CommentDiscussion.class,CommentDiscussionDto.class, pagedResourcesAssembler);
        this.commentDiscussionDao = commentDiscussionDao;
    }

    @Override
    public PagedModel<CommentDiscussionDto> getComments(Pageable pageable) {
        Page<CommentDiscussion> commentDiscussions = this.commentDiscussionDao.findAll(pageable);
        return this.pagedResourcesAssembler.toModel(commentDiscussions,modelAssembler);
    }

    @Override
    public CollectionModel<CommentDiscussionDto> getCommentsByDiscussion(UUID discussionID) {
        List<CommentDiscussion> commentDiscussions = this.commentDiscussionDao.getCommentsByDiscussion(discussionID);
        return CollectionModel.of(commentDiscussions.stream().map(commentDiscussion -> this.modelMapper.map(commentDiscussion,CommentDiscussionDto.class)).collect(Collectors.toList()));
    }

    @Override
    public CommentDiscussionDto getCommentDiscussion(UUID commentID) {
        CommentDiscussion commentDiscussion = this.commentDiscussionDao.findById(commentID).orElseThrow();
        return this.modelMapper.map(commentDiscussion,CommentDiscussionDto.class);
    }

    @Override
    @Transactional
    public CommentDiscussionDto createCommentDiscussion(UUID discussionID, CreateCommentDto createCommentDto) {
        return null;
    }

    @Override
    public CommentDiscussionDto getCommentByDiscussionAndUser(UUID discussionID, UUID publisherID) {
        CommentDiscussion commentDiscussion = this.commentDiscussionDao.getCommentByDiscussionAndPublisher(discussionID,publisherID).orElseThrow();
        return this.modelMapper.map(commentDiscussion,CommentDiscussionDto.class);
    }

    @Override
    @Transactional
    public void deleteCommentDiscussion(UUID commentID) {
        this.commentDiscussionDao.findById(commentID).orElseThrow();
        this.commentDiscussionDao.deleteById(commentID);
    }
}

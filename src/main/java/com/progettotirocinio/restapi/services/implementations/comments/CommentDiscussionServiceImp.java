package com.progettotirocinio.restapi.services.implementations.comments;

import com.progettotirocinio.restapi.config.caching.CacheHandler;
import com.progettotirocinio.restapi.config.mapper.Mapper;
import com.progettotirocinio.restapi.data.dao.DiscussionDao;
import com.progettotirocinio.restapi.data.dao.UserDao;
import com.progettotirocinio.restapi.data.dao.comments.CommentDiscussionDao;
import com.progettotirocinio.restapi.data.dto.input.create.CreateCommentDto;
import com.progettotirocinio.restapi.data.dto.output.comments.CommentDiscussionDto;
import com.progettotirocinio.restapi.data.entities.Discussion;
import com.progettotirocinio.restapi.data.entities.User;
import com.progettotirocinio.restapi.data.entities.comments.CommentDiscussion;
import com.progettotirocinio.restapi.data.entities.enums.CommentType;
import com.progettotirocinio.restapi.services.implementations.GenericServiceImp;
import com.progettotirocinio.restapi.services.interfaces.comments.CommentDiscussionService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class CommentDiscussionServiceImp extends GenericServiceImp<CommentDiscussion, CommentDiscussionDto> implements CommentDiscussionService
{
    private final CommentDiscussionDao commentDiscussionDao;
    private final DiscussionDao discussionDao;

    public CommentDiscussionServiceImp(CacheHandler cacheHandler,DiscussionDao discussionDao,CommentDiscussionDao commentDiscussionDao, UserDao userDao, Mapper mapper,  PagedResourcesAssembler<CommentDiscussion> pagedResourcesAssembler) {
        super(cacheHandler, userDao, mapper, CommentDiscussion.class,CommentDiscussionDto.class, pagedResourcesAssembler);
        this.commentDiscussionDao = commentDiscussionDao;
        this.discussionDao = discussionDao;
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
        User authenticatedUser = this.userDao.findById(UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName())).orElseThrow();
        Discussion discussion = this.discussionDao.findById(discussionID).orElseThrow();
        CommentDiscussion commentDiscussion = new CommentDiscussion();
        commentDiscussion.setTitle(createCommentDto.getTitle());
        commentDiscussion.setText(createCommentDto.getText());
        commentDiscussion.setPublisher(authenticatedUser);
        commentDiscussion.setDiscussion(discussion);
        commentDiscussion.setType(CommentType.DISCUSSION);
        commentDiscussion = this.commentDiscussionDao.save(commentDiscussion);
        return this.modelMapper.map(commentDiscussion,CommentDiscussionDto.class);
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

package com.progettotirocinio.restapi.services.implementations.comments;

import com.progettotirocinio.restapi.config.caching.CacheHandler;
import com.progettotirocinio.restapi.config.mapper.Mapper;
import com.progettotirocinio.restapi.data.dao.PollDao;
import com.progettotirocinio.restapi.data.dao.UserDao;
import com.progettotirocinio.restapi.data.dao.comments.CommentPollDao;
import com.progettotirocinio.restapi.data.dto.input.create.CreateCommentDto;
import com.progettotirocinio.restapi.data.dto.output.comments.CommentPollDto;
import com.progettotirocinio.restapi.data.entities.User;
import com.progettotirocinio.restapi.data.entities.comments.CommentPoll;
import com.progettotirocinio.restapi.data.entities.enums.CommentType;
import com.progettotirocinio.restapi.data.entities.polls.Poll;
import com.progettotirocinio.restapi.services.implementations.GenericServiceImp;
import com.progettotirocinio.restapi.services.interfaces.comments.CommentPollService;
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
public class CommentPollServiceImp extends GenericServiceImp<CommentPoll, CommentPollDto> implements CommentPollService
{
    private final CommentPollDao commentPollDao;
    private final PollDao pollDao;

    public CommentPollServiceImp(CacheHandler cacheHandler,PollDao pollDao,CommentPollDao commentPollDao, UserDao userDao, Mapper mapper, PagedResourcesAssembler<CommentPoll> pagedResourcesAssembler) {
        super(cacheHandler, userDao, mapper,CommentPoll.class,CommentPollDto.class, pagedResourcesAssembler);
        this.pollDao = pollDao;
        this.commentPollDao = commentPollDao;

    }

    @Override
    public PagedModel<CommentPollDto> getComments(Pageable pageable) {
        Page<CommentPoll> commentPolls = this.commentPollDao.findAll(pageable);
        return this.pagedResourcesAssembler.toModel(commentPolls,modelAssembler);
    }

    @Override
    public CollectionModel<CommentPollDto> getCommentsByPoll(UUID pollID) {
        List<CommentPoll> commentPolls = this.commentPollDao.getCommentsByPoll(pollID);
        return CollectionModel.of(commentPolls.stream().map(commentPoll -> this.modelMapper.map(commentPoll,CommentPollDto.class)).collect(Collectors.toList()));
    }

    @Override
    @Transactional
    public CommentPollDto createCommentPoll(UUID pollID, CreateCommentDto createCommentDto) {
        User authenticatedUser = this.userDao.findById(UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName())).orElseThrow();
        Poll poll = this.pollDao.findById(pollID).orElseThrow();
        CommentPoll commentPoll = new CommentPoll();
        commentPoll.setTitle(createCommentDto.getTitle());
        commentPoll.setText(createCommentDto.getText());
        commentPoll.setType(CommentType.POLL);
        commentPoll.setPublisher(authenticatedUser);
        commentPoll.setPoll(poll);
        commentPoll = this.commentPollDao.save(commentPoll);
        return this.modelMapper.map(commentPoll,CommentPollDto.class);
    }

    @Override
    public CommentPollDto getCommentPoll(UUID commentID) {
        CommentPoll commentPoll = this.commentPollDao.findById(commentID).orElseThrow();
        return this.modelMapper.map(commentPoll,CommentPollDto.class);
    }

    @Override
    public CommentPollDto getCommentByPollAndUser(UUID pollID, UUID userID) {
        CommentPoll commentPoll = this.commentPollDao.getCommentPollBy(pollID,userID).orElseThrow();
        return this.modelMapper.map(commentPoll,CommentPollDto.class);
    }

    @Override
    @Transactional
    public void deleteCommentPoll(UUID commentID) {
        this.commentPollDao.findById(commentID).orElseThrow();
        this.commentPollDao.deleteById(commentID);
    }
}

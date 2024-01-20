package com.progettotirocinio.restapi.services.implementations;

import com.progettotirocinio.restapi.config.mapper.Mapper;
import com.progettotirocinio.restapi.data.dao.CommentDao;
import com.progettotirocinio.restapi.data.dao.DiscussionDao;
import com.progettotirocinio.restapi.data.dao.UserDao;
import com.progettotirocinio.restapi.data.dto.input.create.CreateCommentDto;
import com.progettotirocinio.restapi.data.dto.input.update.UpdateCommentDto;
import com.progettotirocinio.restapi.data.dto.output.CommentDto;
import com.progettotirocinio.restapi.data.entities.Comment;
import com.progettotirocinio.restapi.data.entities.Discussion;
import com.progettotirocinio.restapi.data.entities.User;
import com.progettotirocinio.restapi.services.interfaces.CommentService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CommentServiceImp extends GenericServiceImp<Comment, CommentDto> implements CommentService  {

    private final CommentDao commentDao;
    private final DiscussionDao discussionDao;

    public CommentServiceImp(UserDao userDao,DiscussionDao discussionDao,Mapper mapper, CommentDao commentDao, PagedResourcesAssembler<Comment> pagedResourcesAssembler) {
        super(userDao,mapper, Comment.class,CommentDto.class, pagedResourcesAssembler);
        this.commentDao = commentDao;
        this.discussionDao = discussionDao;
    }

    @Override
    public PagedModel<CommentDto> getComments(Pageable pageable) {
        Page<Comment> comments = this.commentDao.findAll(pageable);
        return this.pagedResourcesAssembler.toModel(comments,modelAssembler);
    }

    @Override
    public PagedModel<CommentDto> getCommentsByPublisher(UUID publisherID, Pageable pageable) {
        Page<Comment> comments = this.commentDao.getComments(publisherID,pageable);
        return this.pagedResourcesAssembler.toModel(comments,modelAssembler);
    }

    @Override
    public PagedModel<CommentDto> getCommentsByDiscussion(UUID discussionID, Pageable pageable) {
        return null;
    }

    @Override
    public CommentDto getComment(UUID id) {
        Comment comment = this.commentDao.findById(id).orElseThrow();
        return this.modelMapper.map(comment,CommentDto.class);
    }

    @Override
    @Transactional
    public CommentDto createComment(CreateCommentDto createCommentDto) {
        User publisher = this.userDao.findById(UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName())).orElseThrow();
        Discussion discussion = this.discussionDao.findById(createCommentDto.getDiscussionID()).orElseThrow();
        Comment comment = new Comment();
        comment.setTitle(createCommentDto.getTitle());
        comment.setText(createCommentDto.getText());
        comment.setDiscussion(discussion);
        comment.setPublisher(publisher);
        comment = this.commentDao.save(comment);
        return this.modelMapper.map(comment,CommentDto.class);
    }

    @Override
    @Transactional
    public CommentDto updateComment(UpdateCommentDto updateCommentDto) {
        Comment comment = this.commentDao.findById(updateCommentDto.getCommentID()).orElseThrow();
        if(comment.getText() != null)
            comment.setText(updateCommentDto.getText());
        if(comment.getTitle() != null)
            comment.setTitle(updateCommentDto.getTitle());
        comment = this.commentDao.save(comment);
        return this.modelMapper.map(comment,CommentDto.class);
    }

    @Override
    @Transactional
    public void deleteComment(UUID id) {
        this.commentDao.findById(id).orElseThrow();
        this.commentDao.deleteById(id);
    }
}

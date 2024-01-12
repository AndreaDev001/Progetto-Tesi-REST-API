package com.progettotirocinio.restapi.services.implementations;

import com.progettotirocinio.restapi.data.dao.CommentDao;
import com.progettotirocinio.restapi.data.dto.output.CommentDto;
import com.progettotirocinio.restapi.data.entities.Comment;
import com.progettotirocinio.restapi.services.interfaces.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CommentServiceImp extends GenericServiceImp<Comment, CommentDto> implements CommentService  {

    private final CommentDao commentDao;

    public CommentServiceImp(ModelMapper modelMapper,CommentDao commentDao,PagedResourcesAssembler<Comment> pagedResourcesAssembler) {
        super(modelMapper, Comment.class,CommentDto.class, pagedResourcesAssembler);
        this.commentDao = commentDao;
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
    public void deleteComment(UUID id) {
        this.commentDao.findById(id).orElseThrow();
        this.commentDao.deleteById(id);
    }
}

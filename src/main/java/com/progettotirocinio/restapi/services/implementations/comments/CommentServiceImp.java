package com.progettotirocinio.restapi.services.implementations.comments;

import com.progettotirocinio.restapi.config.caching.CacheHandler;
import com.progettotirocinio.restapi.config.mapper.Mapper;
import com.progettotirocinio.restapi.data.dao.UserDao;
import com.progettotirocinio.restapi.data.dao.comments.CommentDao;
import com.progettotirocinio.restapi.data.dto.input.update.UpdateCommentDto;
import com.progettotirocinio.restapi.data.dto.output.comments.CommentDto;
import com.progettotirocinio.restapi.data.entities.comments.Comment;
import com.progettotirocinio.restapi.data.entities.enums.CommentType;
import com.progettotirocinio.restapi.services.implementations.GenericServiceImp;
import com.progettotirocinio.restapi.services.interfaces.comments.CommentService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.UUID;

@Service
public class CommentServiceImp extends GenericServiceImp<Comment, CommentDto> implements CommentService
{
    private final CommentDao commentDao;

    public CommentServiceImp(CacheHandler cacheHandler,CommentDao commentDao, UserDao userDao, Mapper mapper, PagedResourcesAssembler<Comment> pagedResourcesAssembler) {
        super(cacheHandler, userDao, mapper, Comment.class,CommentDto.class,pagedResourcesAssembler);
        this.commentDao = commentDao;
    }


    @Override
    public PagedModel<CommentDto> getComments(Pageable pageable) {
        Page<Comment> comments = this.commentDao.findAll(pageable);
        return this.pagedResourcesAssembler.toModel(comments,modelAssembler);
    }

    @Override
    public PagedModel<CommentDto> getCommentsByTitle(String title, Pageable pageable) {
        Page<Comment> comments = this.commentDao.getCommentsByTitle(title,pageable);
        return this.pagedResourcesAssembler.toModel(comments,modelAssembler);
    }

    @Override
    public PagedModel<CommentDto> getCommentsByText(String text, Pageable pageable) {
        Page<Comment> comments = this.commentDao.getCommentsByText(text,pageable);
        return this.pagedResourcesAssembler.toModel(comments,modelAssembler);
    }

    @Override
    public PagedModel<CommentDto> getCommentsByPublisher(UUID publisherID, Pageable pageable) {
        Page<Comment> comments = this.commentDao.getCommentsByPublisher(publisherID,pageable);
        return this.pagedResourcesAssembler.toModel(comments,modelAssembler);
    }

    @Override
    public PagedModel<CommentDto> getCommentsByType(CommentType type, Pageable pageable) {
        Page<Comment> comments = this.commentDao.getCommentsByType(type,pageable);
        return this.pagedResourcesAssembler.toModel(comments,modelAssembler);
    }

    @Override
    @Transaction
    public CommentDto updateComment(UpdateCommentDto updateCommentDto) {
        Comment comment = this.commentDao.findById(updateCommentDto.getCommentID()).orElseThrow();
        if(updateCommentDto.getTitle() != null)
            comment.setTitle(updateCommentDto.getTitle());
        if(updateCommentDto.getText() != null)
            comment.setTitle(updateCommentDto.getText());
        comment = this.commentDao.save(comment);
        return this.modelMapper.map(comment,CommentDto.class);
    }

    @Override
    public CommentDto getCommentByID(UUID commentID) {
        Comment comment = this.commentDao.findById(commentID).orElseThrow();
        return this.modelMapper.map(comment,CommentDto.class);
    }

    @Override
    public CollectionModel<CommentType> getTypes() {
        return CollectionModel.of(Arrays.stream(CommentType.values()).toList());
    }

    @Override
    @Transactional
    public void deleteComment(UUID commentID) {
        this.commentDao.findById(commentID).orElseThrow();
        this.commentDao.deleteById(commentID);
    }
}

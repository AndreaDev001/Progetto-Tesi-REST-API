package com.progettotirocinio.restapi.services.implementations.likes;

import com.nimbusds.jose.proc.SecurityContext;
import com.progettotirocinio.restapi.config.exceptions.InvalidFormat;
import com.progettotirocinio.restapi.config.mapper.Mapper;
import com.progettotirocinio.restapi.data.dao.CommentDao;
import com.progettotirocinio.restapi.data.dao.UserDao;
import com.progettotirocinio.restapi.data.dao.likes.CommentLikeDao;
import com.progettotirocinio.restapi.data.dto.output.likes.CommentLikeDto;
import com.progettotirocinio.restapi.data.entities.Comment;
import com.progettotirocinio.restapi.data.entities.User;
import com.progettotirocinio.restapi.data.entities.likes.CommentLike;
import com.progettotirocinio.restapi.services.implementations.GenericServiceImp;
import com.progettotirocinio.restapi.services.interfaces.likes.CommentLikeService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CommentLikeServiceImp extends GenericServiceImp<CommentLike, CommentLikeDto> implements CommentLikeService {
    private final CommentLikeDao commentLikeDao;
    private final CommentDao commentDao;

    public CommentLikeServiceImp(CommentDao commentDao,CommentLikeDao commentLikeDao,UserDao userDao, Mapper mapper, PagedResourcesAssembler<CommentLike> pagedResourcesAssembler) {
        super(userDao, mapper, CommentLike.class, CommentLikeDto.class, pagedResourcesAssembler);
        this.commentDao = commentDao;
        this.commentLikeDao = commentLikeDao;
    }

    @Override
    public PagedModel<CommentLikeDto> getCommentLikes(Pageable pageable) {
        Page<CommentLike> comments = this.commentLikeDao.findAll(pageable);
        return this.pagedResourcesAssembler.toModel(comments,modelAssembler);
    }

    @Override
    public PagedModel<CommentLikeDto> getCommentsLikesByUser(UUID userID, Pageable pageable) {
        Page<CommentLike> commentLikes = this.commentLikeDao.getCommentLikesByUser(userID,pageable);
        return this.pagedResourcesAssembler.toModel(commentLikes,modelAssembler);
    }

    @Override
    public PagedModel<CommentLikeDto> getCommentsLikesByComment(UUID commentID, Pageable pageable) {
        Page<CommentLike> commentLikes = this.commentLikeDao.getCommentLikesByComment(commentID,pageable);
        return this.pagedResourcesAssembler.toModel(commentLikes,modelAssembler);
    }

    @Override
    public CommentLikeDto getCommentLike(UUID commentLikeID) {
        CommentLike commentLike = this.commentLikeDao.findById(commentLikeID).orElseThrow();
        return this.modelMapper.map(commentLike,CommentLikeDto.class);
    }

    @Override
    public CommentLikeDto hasLike(UUID userID, UUID likeID) {
        CommentLike commentLike = this.commentLikeDao.hasLike(userID,likeID).orElseThrow();
        return this.modelMapper.map(commentLike,CommentLikeDto.class);
    }

    @Override
    @Transactional
    public CommentLikeDto createLike(UUID commentID) {
        User authenticatedUser = this.userDao.findById(UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName())).orElseThrow();
        Comment comment = this.commentDao.findById(commentID).orElseThrow();
        if(authenticatedUser.getId().equals(comment.getId()))
            throw new InvalidFormat("error.commentLike.invalidUser");
        CommentLike commentLike = new CommentLike();
        commentLike.setUser(authenticatedUser);
        commentLike.setComment(comment);
        commentLike = this.commentLikeDao.save(commentLike);
        return this.modelMapper.map(commentLike,CommentLikeDto.class);
    }

    @Override
    @Transactional
    public void deleteCommentLike(UUID commentLikeID) {
        this.commentLikeDao.findById(commentLikeID).orElseThrow();
        this.commentLikeDao.deleteById(commentLikeID);
    }
}

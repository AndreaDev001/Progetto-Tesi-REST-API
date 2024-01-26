package com.progettotirocinio.restapi.services.implementations.comments;

import com.progettotirocinio.restapi.config.mapper.Mapper;
import com.progettotirocinio.restapi.data.dao.UserDao;
import com.progettotirocinio.restapi.data.dao.likes.DiscussionLikeDao;
import com.progettotirocinio.restapi.data.dto.output.likes.DiscussionLikeDto;
import com.progettotirocinio.restapi.data.entities.Discussion;
import com.progettotirocinio.restapi.data.entities.likes.DiscussionLike;
import com.progettotirocinio.restapi.services.implementations.GenericServiceImp;
import com.progettotirocinio.restapi.services.interfaces.likes.DiscussionLikeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DiscussionLikeServiceImp extends GenericServiceImp<DiscussionLike, DiscussionLikeDto> implements DiscussionLikeService {

    private final DiscussionLikeDao discussionLikeDao;

    public DiscussionLikeServiceImp(DiscussionLikeDao discussionLikeDao,UserDao userDao, Mapper mapper,PagedResourcesAssembler<DiscussionLike> pagedResourcesAssembler) {
        super(userDao, mapper,DiscussionLike.class,DiscussionLikeDto.class, pagedResourcesAssembler);
        this.discussionLikeDao = discussionLikeDao;
    }

    @Override
    public PagedModel<DiscussionLikeDto> getDiscussionLikes(Pageable pageable) {
        Page<DiscussionLike> discussionLikes = this.discussionLikeDao.findAll(pageable);
        return this.pagedResourcesAssembler.toModel(discussionLikes,modelAssembler);
    }

    @Override
    public PagedModel<DiscussionLikeDto> getDiscussionLikesByUser(UUID userID, Pageable pageable) {
        Page<DiscussionLike> discussionLikes = this.discussionLikeDao.getDiscussionLikesByUser(userID,pageable);
        return this.pagedResourcesAssembler.toModel(discussionLikes,modelAssembler);
    }

    @Override
    public PagedModel<DiscussionLikeDto> getDiscussionLikesByDiscussion(UUID discussionID, Pageable pageable) {
        Page<DiscussionLike> discussionLikes = this.discussionLikeDao.getDiscussionLikesByDiscussion(discussionID,pageable);
        return this.pagedResourcesAssembler.toModel(discussionLikes,modelAssembler);
    }

    @Override
    public DiscussionLikeDto getDiscussionLike(UUID discussionLikeID) {
        DiscussionLike discussionLike = this.discussionLikeDao.findById(discussionLikeID).orElseThrow();
        return this.modelMapper.map(discussionLike,DiscussionLikeDto.class);
    }

    @Override
    public DiscussionLikeDto hasLike(UUID userID, UUID discussionID) {
        DiscussionLike discussionLike = this.discussionLikeDao.hasLike(userID,discussionID).orElseThrow();
        return this.modelMapper.map(discussionLike,DiscussionLikeDto.class);
    }

    @Override
    public void deleteLike(UUID discussionID) {
        this.discussionLikeDao.findById(discussionID).orElseThrow();
        this.discussionLikeDao.deleteById(discussionID);
    }
}

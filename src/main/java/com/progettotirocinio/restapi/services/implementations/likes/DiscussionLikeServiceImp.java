package com.progettotirocinio.restapi.services.implementations.likes;

import com.progettotirocinio.restapi.config.caching.CacheHandler;
import com.progettotirocinio.restapi.config.caching.RequiresCaching;
import com.progettotirocinio.restapi.config.exceptions.InvalidFormat;
import com.progettotirocinio.restapi.config.mapper.Mapper;
import com.progettotirocinio.restapi.data.dao.DiscussionDao;
import com.progettotirocinio.restapi.data.dao.UserDao;
import com.progettotirocinio.restapi.data.dao.likes.DiscussionLikeDao;
import com.progettotirocinio.restapi.data.dto.output.likes.DiscussionLikeDto;
import com.progettotirocinio.restapi.data.entities.Discussion;
import com.progettotirocinio.restapi.data.entities.User;
import com.progettotirocinio.restapi.data.entities.likes.DiscussionLike;
import com.progettotirocinio.restapi.services.implementations.GenericServiceImp;
import com.progettotirocinio.restapi.services.interfaces.likes.DiscussionLikeService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
@RequiresCaching(allCacheName = "ALL_DISCUSSION_LIKES")
public class DiscussionLikeServiceImp extends GenericServiceImp<DiscussionLike, DiscussionLikeDto> implements DiscussionLikeService {

    private final DiscussionLikeDao discussionLikeDao;
    private final DiscussionDao discussionDao;

    public DiscussionLikeServiceImp(CacheHandler cacheHandler,DiscussionDao discussionDao, DiscussionLikeDao discussionLikeDao, UserDao userDao, Mapper mapper, PagedResourcesAssembler<DiscussionLike> pagedResourcesAssembler) {
        super(cacheHandler,userDao, mapper,DiscussionLike.class,DiscussionLikeDto.class, pagedResourcesAssembler);
        this.discussionLikeDao = discussionLikeDao;
        this.discussionDao = discussionDao;
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
    @Transactional
    public DiscussionLikeDto createLike(UUID discussionID) {
        User authenticatedUser = this.userDao.findById(UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName())).orElseThrow();
        Discussion discussion = this.discussionDao.findById(discussionID).orElseThrow();
        if(authenticatedUser.getId().equals(discussion.getOwnerID()))
            throw new InvalidFormat("error.discussionLike.invalidUser");
        DiscussionLike discussionLike = new DiscussionLike();
        discussionLike.setUser(authenticatedUser);
        discussionLike.setDiscussion(discussion);
        discussionLike = this.discussionLikeDao.save(discussionLike);
        return this.modelMapper.map(discussionLike,DiscussionLikeDto.class);
    }

    @Override
    @Transactional
    public void deleteLike(UUID discussionID) {
        this.discussionLikeDao.findById(discussionID).orElseThrow();
        this.discussionLikeDao.deleteById(discussionID);
    }

    @Override
    @Transactional
    public void deleteLikeByDiscussion(UUID discussionID) {
        User authenticatedUser = this.userDao.findById(UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName())).orElseThrow();
        DiscussionLike discussionLike = this.discussionLikeDao.hasLike(authenticatedUser.getId(),discussionID).orElseThrow();
        this.discussionLikeDao.deleteById(discussionLike.getId());
    }
}

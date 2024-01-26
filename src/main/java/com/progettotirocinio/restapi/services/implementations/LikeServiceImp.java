package com.progettotirocinio.restapi.services.implementations;

import com.progettotirocinio.restapi.config.mapper.Mapper;
import com.progettotirocinio.restapi.data.dao.LikeDao;
import com.progettotirocinio.restapi.data.dao.UserDao;
import com.progettotirocinio.restapi.data.dto.output.LikeDto;
import com.progettotirocinio.restapi.data.entities.Like;
import com.progettotirocinio.restapi.data.entities.enums.LikeType;
import com.progettotirocinio.restapi.services.interfaces.LikeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.UUID;

@Service
public class LikeServiceImp extends GenericServiceImp<Like, LikeDto> implements LikeService {

    private final LikeDao likeDao;
    public LikeServiceImp(LikeDao likeDao,UserDao userDao, Mapper mapper, PagedResourcesAssembler<Like> pagedResourcesAssembler) {
        super(userDao, mapper, Like.class,LikeDto.class, pagedResourcesAssembler);
        this.likeDao = likeDao;
    }

    @Override
    public PagedModel<LikeDto> getLikes(Pageable pageable) {
        Page<Like> likes = this.likeDao.findAll(pageable);
        return this.pagedResourcesAssembler.toModel(likes,modelAssembler);
    }

    @Override
    public PagedModel<LikeDto> getLikesByType(LikeType type, Pageable pageable) {
        Page<Like> likes = this.likeDao.getLikesByType(type,pageable);
        return this.pagedResourcesAssembler.toModel(likes,modelAssembler);
    }

    @Override
    public PagedModel<LikeDto> getLikesByUserAndType(UUID userID, LikeType type, Pageable pageable) {
        Page<Like> likes = this.likeDao.getLikesByUserAndType(userID,type,pageable);
        return this.pagedResourcesAssembler.toModel(likes,modelAssembler);
    }

    @Override
    public PagedModel<LikeDto> getLikesByUser(UUID userID, Pageable pageable) {
        Page<Like> likes = this.likeDao.getLikesByUser(userID,pageable);
        return this.pagedResourcesAssembler.toModel(likes,modelAssembler);
    }

    @Override
    public PagedModel<LikeDto> getLikesByTask(UUID taskID, Pageable pageable) {
        Page<Like> likes = this.likeDao.getLikesByTask(taskID,pageable);
        return this.pagedResourcesAssembler.toModel(likes,modelAssembler);
    }

    @Override
    public PagedModel<LikeDto> getLikesByDiscussion(UUID discussionID, Pageable pageable) {
        Page<Like> likes = this.likeDao.getLikesByDiscussion(discussionID,pageable);
        return this.pagedResourcesAssembler.toModel(likes,modelAssembler);
    }

    @Override
    public PagedModel<LikeDto> getLikesByComment(UUID commentID, Pageable pageable) {
        Page<Like> likes = this.likeDao.getLikesByComment(commentID,pageable);
        return this.pagedResourcesAssembler.toModel(likes,modelAssembler);
    }

    @Override
    public PagedModel<LikeDto> getLikesByPoll(UUID pollID, Pageable pageable) {
        Page<Like> likes = this.likeDao.getLikesByPoll(pollID,pageable);
        return this.pagedResourcesAssembler.toModel(likes,modelAssembler);
    }

    @Override
    public LikeDto getLike(UUID likeID) {
        Like like = this.likeDao.findById(likeID).orElseThrow();
        return this.modelMapper.map(like,LikeDto.class);
    }

    @Override
    public void deleteLike(UUID likeID) {
        this.likeDao.findById(likeID).orElseThrow();
        this.likeDao.deleteById(likeID);
    }

    @Override
    public CollectionModel<LikeType> getLikeTypes() {
        LikeType[] values = LikeType.values();
        return CollectionModel.of(Arrays.stream(values).toList());
    }
}

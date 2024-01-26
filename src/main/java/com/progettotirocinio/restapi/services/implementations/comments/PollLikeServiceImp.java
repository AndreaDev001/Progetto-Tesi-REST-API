package com.progettotirocinio.restapi.services.implementations.comments;

import com.progettotirocinio.restapi.config.mapper.Mapper;
import com.progettotirocinio.restapi.data.dao.UserDao;
import com.progettotirocinio.restapi.data.dao.likes.PollLikeDao;
import com.progettotirocinio.restapi.data.dto.output.likes.PollLikeDto;
import com.progettotirocinio.restapi.data.entities.Poll;
import com.progettotirocinio.restapi.data.entities.likes.PollLike;
import com.progettotirocinio.restapi.services.implementations.GenericServiceImp;
import com.progettotirocinio.restapi.services.interfaces.likes.PollLikeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PollLikeServiceImp extends GenericServiceImp<PollLike, PollLikeDto> implements PollLikeService
{
    private final PollLikeDao pollLikeDao;

    public PollLikeServiceImp(PollLikeDao pollLikeDao,UserDao userDao, Mapper mapper,PagedResourcesAssembler<PollLike> pagedResourcesAssembler) {
        super(userDao, mapper,PollLike.class,PollLikeDto.class, pagedResourcesAssembler);
        this.pollLikeDao = pollLikeDao;
    }

    @Override
    public PagedModel<PollLikeDto> getPollLikes(Pageable pageable) {
        Page<PollLike> pollLikes = this.pollLikeDao.findAll(pageable);
        return this.pagedResourcesAssembler.toModel(pollLikes,modelAssembler);
    }

    @Override
    public PagedModel<PollLikeDto> getPollLikesByUser(UUID userID, Pageable pageable) {
        Page<PollLike> pollLikes = this.pollLikeDao.getPollLikesByUser(userID,pageable);
        return this.pagedResourcesAssembler.toModel(pollLikes,modelAssembler);
    }

    @Override
    public PagedModel<PollLikeDto> getPollLikesByPoll(UUID pollID, Pageable pageable) {
        Page<PollLike> pollLikes = this.pollLikeDao.getPollLikesByPoll(pollID,pageable);
        return this.pagedResourcesAssembler.toModel(pollLikes,modelAssembler);
    }

    @Override
    public PollLikeDto getPollLike(UUID pollLikeID) {
        PollLike pollLike = this.pollLikeDao.findById(pollLikeID).orElseThrow();
        return this.modelMapper.map(pollLike,PollLikeDto.class);
    }

    @Override
    public PollLikeDto hasLike(UUID userID, UUID pollID) {
        PollLike pollLike = this.pollLikeDao.hasLike(userID,pollID).orElseThrow();
        return this.modelMapper.map(pollLike,PollLikeDto.class);
    }

    @Override
    public void deletePollLike(UUID pollLikeID) {
        this.pollLikeDao.findById(pollLikeID).orElseThrow();
        this.pollLikeDao.deleteById(pollLikeID);
    }
}

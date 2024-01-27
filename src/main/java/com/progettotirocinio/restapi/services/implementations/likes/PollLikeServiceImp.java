package com.progettotirocinio.restapi.services.implementations.likes;

import com.progettotirocinio.restapi.config.exceptions.InvalidFormat;
import com.progettotirocinio.restapi.config.mapper.Mapper;
import com.progettotirocinio.restapi.data.dao.PollDao;
import com.progettotirocinio.restapi.data.dao.UserDao;
import com.progettotirocinio.restapi.data.dao.likes.PollLikeDao;
import com.progettotirocinio.restapi.data.dto.output.likes.PollLikeDto;
import com.progettotirocinio.restapi.data.entities.Poll;
import com.progettotirocinio.restapi.data.entities.User;
import com.progettotirocinio.restapi.data.entities.likes.PollLike;
import com.progettotirocinio.restapi.services.implementations.GenericServiceImp;
import com.progettotirocinio.restapi.services.interfaces.likes.PollLikeService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PollLikeServiceImp extends GenericServiceImp<PollLike, PollLikeDto> implements PollLikeService
{
    private final PollLikeDao pollLikeDao;
    private final PollDao pollDao;

    public PollLikeServiceImp(PollDao pollDao,PollLikeDao pollLikeDao,UserDao userDao, Mapper mapper,PagedResourcesAssembler<PollLike> pagedResourcesAssembler) {
        super(userDao, mapper,PollLike.class,PollLikeDto.class, pagedResourcesAssembler);
        this.pollLikeDao = pollLikeDao;
        this.pollDao = pollDao;
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
    @Transactional
    public PollLikeDto createLike(UUID pollID) {
        User authenticatedUser = this.userDao.findById(UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName())).orElseThrow();
        Poll poll = this.pollDao.findById(pollID).orElseThrow();
        if(authenticatedUser.getId().equals(poll.getOwnerID()))
            throw new InvalidFormat("error.pollLike.invalidUser");
        PollLike pollLike = new PollLike();
        pollLike.setPoll(poll);
        pollLike.setUser(authenticatedUser);
        pollLike = this.pollLikeDao.save(pollLike);
        return this.modelMapper.map(pollLike,PollLikeDto.class);
    }

    @Override
    @Transactional
    public void deletePollLike(UUID pollLikeID) {
        this.pollLikeDao.findById(pollLikeID).orElseThrow();
        this.pollLikeDao.deleteById(pollLikeID);
    }
}

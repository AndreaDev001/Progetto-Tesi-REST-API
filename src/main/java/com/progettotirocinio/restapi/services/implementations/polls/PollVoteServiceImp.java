package com.progettotirocinio.restapi.services.implementations.polls;

import com.progettotirocinio.restapi.config.caching.CacheHandler;
import com.progettotirocinio.restapi.config.mapper.Mapper;
import com.progettotirocinio.restapi.data.dao.PollOptionDao;
import com.progettotirocinio.restapi.data.dao.PollVoteDao;
import com.progettotirocinio.restapi.data.dao.UserDao;
import com.progettotirocinio.restapi.data.dto.output.polls.PollVoteDto;
import com.progettotirocinio.restapi.data.entities.polls.PollOption;
import com.progettotirocinio.restapi.data.entities.polls.PollVote;
import com.progettotirocinio.restapi.data.entities.User;
import com.progettotirocinio.restapi.services.implementations.GenericServiceImp;
import com.progettotirocinio.restapi.services.interfaces.polls.PollVoteService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PollVoteServiceImp extends GenericServiceImp<PollVote, PollVoteDto> implements PollVoteService
{
    private final PollVoteDao pollVoteDao;
    private final PollOptionDao pollOptionDao;


    public PollVoteServiceImp(PollOptionDao pollOptionDao,PollVoteDao pollVoteDao,CacheHandler cacheHandler, UserDao userDao, Mapper mapper,PagedResourcesAssembler<PollVote> pagedResourcesAssembler) {
        super(cacheHandler, userDao, mapper,PollVote.class,PollVoteDto.class, pagedResourcesAssembler);
        this.pollVoteDao = pollVoteDao;
        this.pollOptionDao = pollOptionDao;
    }

    @Override
    public PagedModel<PollVoteDto> getPollVotes(Pageable pageable) {
        Page<PollVote> pollVotes = this.pollVoteDao.findAll(pageable);
        return this.pagedResourcesAssembler.toModel(pollVotes,modelAssembler);
    }

    @Override
    public PagedModel<PollVoteDto> getPollVotesByUser(UUID userID, Pageable pageable) {
        Page<PollVote> pollVotes = this.pollVoteDao.getPollVotesByUser(userID,pageable);
        return this.pagedResourcesAssembler.toModel(pollVotes,modelAssembler);
    }

    @Override
    public PagedModel<PollVoteDto> getPollVotesByOption(UUID optionID, Pageable pageable) {
        Page<PollVote> pollVotes = this.pollVoteDao.getPollVotesByOption(optionID,pageable);
        return this.pagedResourcesAssembler.toModel(pollVotes,modelAssembler);
    }

    @Override
    public PollVoteDto getPollVote(UUID pollVoteID) {
        PollVote pollVote = this.pollVoteDao.findById(pollVoteID).orElseThrow();
        return this.modelMapper.map(pollVote,PollVoteDto.class);
    }

    @Override
    public PollVoteDto getVoteBetween(UUID userID, UUID pollVoteID) {
        PollVote pollVote = this.pollVoteDao.getPollVote(userID,pollVoteID).orElseThrow();
        return this.modelMapper.map(pollVote,PollVoteDto.class);
    }

    @Override
    @Transactional
    public PollVoteDto createVote(UUID optionID) {
        User authenticatedUser = this.userDao.findById(UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName())).orElseThrow();
        PollOption pollOption = this.pollOptionDao.findById(optionID).orElseThrow();
        PollVote pollVote = new PollVote();
        pollVote.setPollOption(pollOption);
        pollVote.setUser(authenticatedUser);
        pollVote = this.pollVoteDao.save(pollVote);
        return this.modelMapper.map(pollVote,PollVoteDto.class);
    }

    @Override
    @Transactional
    public void deletePollVote(UUID pollVoteID) {
        this.pollVoteDao.findById(pollVoteID).orElseThrow();
        this.pollVoteDao.deleteById(pollVoteID);
    }
}

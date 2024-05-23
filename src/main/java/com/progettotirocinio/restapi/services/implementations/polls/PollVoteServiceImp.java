package com.progettotirocinio.restapi.services.implementations.polls;

import com.progettotirocinio.restapi.config.caching.CacheHandler;
import com.progettotirocinio.restapi.config.exceptions.InvalidFormat;
import com.progettotirocinio.restapi.config.mapper.Mapper;
import com.progettotirocinio.restapi.data.dao.PollDao;
import com.progettotirocinio.restapi.data.dao.PollOptionDao;
import com.progettotirocinio.restapi.data.dao.PollVoteDao;
import com.progettotirocinio.restapi.data.dao.UserDao;
import com.progettotirocinio.restapi.data.dto.output.polls.PollVoteDto;
import com.progettotirocinio.restapi.data.entities.polls.Poll;
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

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class PollVoteServiceImp extends GenericServiceImp<PollVote, PollVoteDto> implements PollVoteService
{
    private final PollDao pollDao;
    private final PollVoteDao pollVoteDao;
    private final PollOptionDao pollOptionDao;


    public PollVoteServiceImp(PollDao pollDao,PollOptionDao pollOptionDao,PollVoteDao pollVoteDao,CacheHandler cacheHandler, UserDao userDao, Mapper mapper,PagedResourcesAssembler<PollVote> pagedResourcesAssembler) {
        super(cacheHandler, userDao, mapper,PollVote.class,PollVoteDto.class, pagedResourcesAssembler);
        this.pollDao = pollDao;
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
    public PollVoteDto getCurrentVote(UUID pollID) {
        PollVote pollVote = this.pollVoteDao.getCurrentVote(pollID).orElseThrow();
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
        Poll poll = this.pollDao.findById(pollOption.getPoll().getId()).orElseThrow();
        Optional<PollVote> currentPollVote = this.pollVoteDao.getCurrentVote(pollOption.getPoll().getId());
        currentPollVote.ifPresent(pollVote -> this.pollVoteDao.deleteById(pollVote.getId()));
        List<PollVote> pollVotes = this.pollVoteDao.getPollVotesByPoll(poll.getId());
        if(pollVotes.size() + 1 > poll.getMaximumVotes())
            throw new InvalidFormat("error.polls.maxVotes");
        PollVote pollVote = new PollVote();
        pollVote.setOption(pollOption);
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

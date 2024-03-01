package com.progettotirocinio.restapi.services.implementations;

import com.progettotirocinio.restapi.config.caching.CacheHandler;
import com.progettotirocinio.restapi.config.caching.RequiresCaching;
import com.progettotirocinio.restapi.config.mapper.Mapper;
import com.progettotirocinio.restapi.data.dao.PollDao;
import com.progettotirocinio.restapi.data.dao.UserDao;
import com.progettotirocinio.restapi.data.dao.specifications.PollSpecifications;
import com.progettotirocinio.restapi.data.dao.specifications.SpecificationsUtils;
import com.progettotirocinio.restapi.data.dto.input.create.CreatePollDto;
import com.progettotirocinio.restapi.data.dto.input.update.UpdatePollDto;
import com.progettotirocinio.restapi.data.dto.output.PollDto;
import com.progettotirocinio.restapi.data.entities.polls.Poll;
import com.progettotirocinio.restapi.data.entities.User;
import com.progettotirocinio.restapi.data.entities.enums.PollStatus;
import com.progettotirocinio.restapi.services.interfaces.PollService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiresCaching(allCacheName = "ALL_POLLS")
public class PollServiceImp extends GenericServiceImp<Poll, PollDto> implements PollService {

    private final PollDao pollDao;
    public PollServiceImp(CacheHandler cacheHandler,UserDao userDao, Mapper mapper, PollDao pollDao, PagedResourcesAssembler<Poll> pagedResourcesAssembler) {
        super(cacheHandler,userDao,mapper,Poll.class,PollDto.class, pagedResourcesAssembler);
        this.pollDao = pollDao;
    }

    @Override
    public PagedModel<PollDto> getPolls(Pageable pageable) {
        Page<Poll> polls = this.pollDao.findAll(pageable);
        return this.pagedResourcesAssembler.toModel(polls,modelAssembler);
    }

    @Override
    public PagedModel<PollDto> getPollsByTitle(String title, Pageable pageable) {
        Page<Poll> polls = this.pollDao.getPollsByTitle(title,pageable);
        return this.pagedResourcesAssembler.toModel(polls,modelAssembler);
    }

    @Override
    public PagedModel<PollDto> getPollsByPublisher(UUID publisherID, Pageable pageable) {
        Page<Poll> polls = this.pollDao.getPollsByPublisher(publisherID,pageable);
        return this.pagedResourcesAssembler.toModel(polls,modelAssembler);
    }

    @Override
    public PagedModel<PollDto> getPollsByDescription(String description, Pageable pageable) {
        Page<Poll> polls = this.pollDao.getPollsByDescription(description,pageable);
        return this.pagedResourcesAssembler.toModel(polls,modelAssembler);
    }

    @Override
    public PagedModel<PollDto> getPollsByMinimumVotes(Integer votes, Pageable pageable) {
        Page<Poll> polls = this.pollDao.getPollsByMinimumVotes(votes,pageable);
        return this.pagedResourcesAssembler.toModel(polls,modelAssembler);
    }

    @Override
    public PagedModel<PollDto> getPollsByMaximumVotes(Integer votes, Pageable pageable) {
        Page<Poll> polls = this.pollDao.getPollsByMaximumVotes(votes,pageable);
        return this.pagedResourcesAssembler.toModel(polls,modelAssembler);
    }

    @Override
    public PagedModel<PollDto> getPollsBySpec(Specification<Poll> specification, Pageable pageable) {
        Page<Poll> polls = this.pollDao.findAll(specification,pageable);
        return this.pagedResourcesAssembler.toModel(polls,modelAssembler);
    }

    @Override
    public PagedModel<PollDto> getSimilarPolls(UUID pollID, Pageable pageable) {
        Poll poll = this.pollDao.findById(pollID).orElseThrow();
        PollSpecifications.Filter filter = new PollSpecifications.Filter(poll);
        Page<Poll> polls = this.pollDao.findAll(PollSpecifications.withFilter(filter),pageable);
        return this.pagedResourcesAssembler.toModel(polls,modelAssembler);
    }

    @Override
    public CollectionModel<String> getOrderTypes() {
        return CollectionModel.of(SpecificationsUtils.generateOrderTypes(Poll.class));
    }

    @Override
    public CollectionModel<PollStatus> getStatues() {
        return CollectionModel.of(Arrays.stream(PollStatus.values()).toList());
    }

    @Override
    public PollDto getPoll(UUID pollID) {
        Poll poll = this.pollDao.findById(pollID).orElseThrow();
        return this.modelMapper.map(poll,PollDto.class);
    }

    @Override
    @Transactional
    public PollDto createPoll(CreatePollDto createPollDto) {
        User publisher = this.userDao.findById(UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName())).orElseThrow();
        Poll poll = new Poll();
        poll.setTitle(createPollDto.getTitle());
        poll.setDescription(createPollDto.getDescription());
        poll.setMaximumVotes(createPollDto.getMaximumVotes());
        poll.setMinimumVotes(createPollDto.getMinimumVotes());
        poll.setStatus(PollStatus.OPEN);
        poll.setPublisher(publisher);
        poll.setExpirationDate(createPollDto.getExpirationDate());
        poll = this.pollDao.save(poll);
        return this.modelMapper.map(poll,PollDto.class);
    }

    @Override
    @Transactional
    public PollDto updatePoll(UpdatePollDto updatePollDto) {
        Poll poll = this.pollDao.findById(updatePollDto.getPollID()).orElseThrow();
        if(poll.getTitle() != null)
            poll.setTitle(updatePollDto.getTitle());
        if(poll.getDescription() != null)
            poll.setDescription(updatePollDto.getDescription());
        if(poll.getMaximumVotes() != null)
            poll.setMaximumVotes(updatePollDto.getMaximumVotes());
        if(poll.getMinimumVotes() != null)
            poll.setMinimumVotes(updatePollDto.getMinimumVotes());
        poll = this.pollDao.save(poll);
        return this.modelMapper.map(poll,PollDto.class);
    }

    @Override
    @Transactional
    public void handleExpiredPolls() {
        List<Poll> polls = this.pollDao.getPollsByDate(LocalDate.now());
        for(Poll poll : polls)
            poll.setStatus(PollStatus.EXPIRED);
        this.pollDao.saveAll(polls);
    }

    @Override
    @Transactional
    public void deleteExpiredPolls() {
        List<Poll> polls = this.pollDao.getPollsByStatus(PollStatus.EXPIRED);
        this.pollDao.deleteAll(polls);
    }

    @Override
    @Transactional
    public void deletePoll(UUID pollID) {
        this.pollDao.findById(pollID).orElseThrow();
        this.pollDao.deleteById(pollID);
    }
}

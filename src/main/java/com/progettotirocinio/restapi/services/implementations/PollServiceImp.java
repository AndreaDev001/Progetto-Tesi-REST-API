package com.progettotirocinio.restapi.services.implementations;

import com.progettotirocinio.restapi.data.dao.PollDao;
import com.progettotirocinio.restapi.data.dto.output.PollDto;
import com.progettotirocinio.restapi.data.entities.Poll;
import com.progettotirocinio.restapi.services.interfaces.PollService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PollServiceImp extends GenericServiceImp<Poll, PollDto> implements PollService {

    private final PollDao pollDao;
    public PollServiceImp(ModelMapper modelMapper,PollDao pollDao,PagedResourcesAssembler<Poll> pagedResourcesAssembler) {
        super(modelMapper,Poll.class,PollDto.class, pagedResourcesAssembler);
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
    public PollDto getPoll(UUID pollID) {
        Poll poll = this.pollDao.findById(pollID).orElseThrow();
        return this.modelMapper.map(poll,PollDto.class);
    }

    @Override
    public void deletePoll(UUID pollID) {
        this.pollDao.findById(pollID).orElseThrow();
        this.pollDao.deleteById(pollID);
    }
}

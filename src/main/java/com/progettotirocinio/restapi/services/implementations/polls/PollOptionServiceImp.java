package com.progettotirocinio.restapi.services.implementations.polls;

import com.progettotirocinio.restapi.config.caching.CacheHandler;
import com.progettotirocinio.restapi.config.exceptions.InvalidFormat;
import com.progettotirocinio.restapi.config.mapper.Mapper;
import com.progettotirocinio.restapi.data.dao.PollDao;
import com.progettotirocinio.restapi.data.dao.PollOptionDao;
import com.progettotirocinio.restapi.data.dao.UserDao;
import com.progettotirocinio.restapi.data.dto.input.create.polls.CreatePollOptionDto;
import com.progettotirocinio.restapi.data.dto.input.update.polls.UpdatePollOptionDto;
import com.progettotirocinio.restapi.data.dto.output.polls.PollOptionDto;
import com.progettotirocinio.restapi.data.entities.polls.Poll;
import com.progettotirocinio.restapi.data.entities.polls.PollOption;
import com.progettotirocinio.restapi.data.entities.User;
import com.progettotirocinio.restapi.services.implementations.GenericServiceImp;
import com.progettotirocinio.restapi.services.interfaces.polls.PollOptionService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class PollOptionServiceImp extends GenericServiceImp<PollOption, PollOptionDto> implements PollOptionService
{
    private final PollOptionDao pollOptionDao;
    private final PollDao pollDao;

    public PollOptionServiceImp(PollDao pollDao,PollOptionDao pollOptionDao,CacheHandler cacheHandler, UserDao userDao, Mapper mapper, PagedResourcesAssembler<PollOption> pagedResourcesAssembler) {
        super(cacheHandler, userDao, mapper,PollOption.class,PollOptionDto.class, pagedResourcesAssembler);
        this.pollOptionDao = pollOptionDao;
        this.pollDao = pollDao;
    }

    @Override
    public PagedModel<PollOptionDto> getPollOptions(Pageable pageable) {
        Page<PollOption> pollOptions = this.pollOptionDao.findAll(pageable);
        return this.pagedResourcesAssembler.toModel(pollOptions,modelAssembler);
    }

    @Override
    public CollectionModel<PollOptionDto> getPollOptionsByPoll(UUID pollID) {
        List<PollOption> pollOptions = this.pollOptionDao.getPollOptionsByPoll(pollID);
        return CollectionModel.of(pollOptions.stream().map(pollOption -> this.modelMapper.map(pollOption,PollOptionDto.class)).collect(Collectors.toList()));
    }

    @Override
    public PagedModel<PollOptionDto> getPollOptionsByName(String name, Pageable pageable) {
        Page<PollOption> pollOptions = this.pollOptionDao.getPollOptionsByName(name,pageable);
        return this.pagedResourcesAssembler.toModel(pollOptions,modelAssembler);
    }

    @Override
    public PagedModel<PollOptionDto> getPollOptionsByDescription(String description, Pageable pageable) {
        Page<PollOption> pollOptions = this.pollOptionDao.getPollOptionsByDescription(description,pageable);
        return this.pagedResourcesAssembler.toModel(pollOptions,modelAssembler);
    }

    @Override
    public PollOptionDto getPollOption(UUID optionID) {
        PollOption pollOption = this.pollOptionDao.findById(optionID).orElseThrow();
        return this.modelMapper.map(pollOption,PollOptionDto.class);
    }

    @Override
    @Transactional
    public PollOptionDto createOption(CreatePollOptionDto createPollOptionDto) {
        User authenticatedUser = this.userDao.findById(UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName())).orElseThrow();
        Poll poll = this.pollDao.findById(createPollOptionDto.getPollID()).orElseThrow();
        if(!authenticatedUser.getId().equals(poll.getOwnerID()))
            throw new InvalidFormat("error.pollOptions.invalidUser");
        PollOption pollOption = new PollOption();
        pollOption.setName(createPollOptionDto.getName());
        pollOption.setDescription(createPollOptionDto.getDescription());
        pollOption.setPoll(poll);
        pollOption = this.pollOptionDao.save(pollOption);
        return this.modelMapper.map(pollOption,PollOptionDto.class);
    }

    @Override
    @Transactional
    public PollOptionDto updateOption(UpdatePollOptionDto updatePollOptionDto) {
        PollOption pollOption = this.pollOptionDao.findById(updatePollOptionDto.getOptionID()).orElseThrow();
        if(updatePollOptionDto.getName() != null)
            pollOption.setName(updatePollOptionDto.getName());
        if(updatePollOptionDto.getDescription() != null)
            pollOption.setDescription(updatePollOptionDto.getDescription());
        pollOption = this.pollOptionDao.save(pollOption);
        return this.modelMapper.map(pollOption,PollOptionDto.class);
    }

    @Override
    @Transactional
    public void deletePollOption(UUID optionID) {
        this.pollOptionDao.findById(optionID).orElseThrow();
        this.pollOptionDao.deleteById(optionID);
    }
}

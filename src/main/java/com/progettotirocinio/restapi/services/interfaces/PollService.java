package com.progettotirocinio.restapi.services.interfaces;

import com.progettotirocinio.restapi.data.dto.input.create.CreatePollDto;
import com.progettotirocinio.restapi.data.dto.input.update.UpdatePollDto;
import com.progettotirocinio.restapi.data.dto.output.PollDto;
import com.progettotirocinio.restapi.data.entities.enums.PollStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;

import java.util.Collection;
import java.util.UUID;

public interface PollService {
    PagedModel<PollDto> getPolls(Pageable pageable);
    PagedModel<PollDto> getPollsByTitle(String title,Pageable pageable);
    PagedModel<PollDto> getPollsByPublisher(UUID publisherID,Pageable pageable);
    PagedModel<PollDto> getPollsByDescription(String description,Pageable pageable);
    PagedModel<PollDto> getPollsByMinimumVotes(Integer votes,Pageable pageable);
    PagedModel<PollDto> getPollsByMaximumVotes(Integer votes,Pageable pageable);
    CollectionModel<PollStatus> getStatues();
    PollDto getPoll(UUID pollID);
    PollDto createPoll(CreatePollDto createPollDto);
    PollDto updatePoll(UpdatePollDto updatePollDto);
    void deletePoll(UUID pollID);
}

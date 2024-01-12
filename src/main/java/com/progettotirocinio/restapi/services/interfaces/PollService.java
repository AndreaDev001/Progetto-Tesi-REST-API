package com.progettotirocinio.restapi.services.interfaces;

import com.progettotirocinio.restapi.data.dto.output.PollDto;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import java.util.UUID;

public interface PollService {
    PagedModel<PollDto> getPolls(Pageable pageable);
    PagedModel<PollDto> getPollsByTitle(String title,Pageable pageable);
    PagedModel<PollDto> getPollsByPublisher(UUID publisherID,Pageable pageable);
    PagedModel<PollDto> getPollsByDescription(String description,Pageable pageable);
    PagedModel<PollDto> getPollsByMinimumVotes(Integer votes,Pageable pageable);
    PagedModel<PollDto> getPollsByMaximumVotes(Integer votes,Pageable pageable);
    PollDto getPoll(UUID pollID);
    void deletePoll(UUID pollID);
}

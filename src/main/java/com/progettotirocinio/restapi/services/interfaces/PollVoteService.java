package com.progettotirocinio.restapi.services.interfaces;

import com.progettotirocinio.restapi.data.dto.output.PollVoteDto;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import java.util.UUID;

public interface PollVoteService
{
    PagedModel<PollVoteDto> getPollVotes(Pageable pageable);
    PagedModel<PollVoteDto> getPollVotesByUser(UUID userID,Pageable pageable);
    PagedModel<PollVoteDto> getPollVotesByOption(UUID optionID,Pageable pageable);
    PollVoteDto getPollVote(UUID pollVoteID);
    PollVoteDto getVoteBetween(UUID userID,UUID pollVoteID);
    PollVoteDto createVote(UUID optionID);
    void deletePollVote(UUID pollVoteID);
}

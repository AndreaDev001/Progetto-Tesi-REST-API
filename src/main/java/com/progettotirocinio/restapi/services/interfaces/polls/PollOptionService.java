package com.progettotirocinio.restapi.services.interfaces.polls;

import com.progettotirocinio.restapi.data.dto.input.create.polls.CreatePollOptionDto;
import com.progettotirocinio.restapi.data.dto.input.update.polls.UpdatePollOptionDto;
import com.progettotirocinio.restapi.data.dto.output.polls.PollOptionDto;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;

import java.util.UUID;

public interface PollOptionService
{
    PagedModel<PollOptionDto> getPollOptions(Pageable pageable);
    CollectionModel<PollOptionDto> getPollOptionsByPoll(UUID pollID);
    PagedModel<PollOptionDto> getPollOptionsByName(String name,Pageable pageable);
    PagedModel<PollOptionDto> getPollOptionsByDescription(String description,Pageable pageable);
    PollOptionDto getPollOption(UUID optionID);
    PollOptionDto createOption(CreatePollOptionDto createPollOptionDto);
    PollOptionDto updateOption(UpdatePollOptionDto updatePollOptionDto);

    void deletePollOption(UUID optionID);
}

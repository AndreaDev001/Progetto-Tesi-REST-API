package com.progettotirocinio.restapi.services.interfaces;

import com.progettotirocinio.restapi.data.dto.input.create.CreatePollOptionDto;
import com.progettotirocinio.restapi.data.dto.output.PollOptionDto;
import com.progettotirocinio.restapi.data.entities.PollOption;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import java.util.UUID;

public interface PollOptionService
{
    PagedModel<PollOptionDto> getPollOptions(Pageable pageable);
    PagedModel<PollOptionDto> getPollOptionsByPoll(UUID pollID,Pageable pageable);
    PagedModel<PollOptionDto> getPollOptionsByName(String name,Pageable pageable);
    PollOptionDto getPollOption(UUID optionID);
    PollOptionDto createOption(CreatePollOptionDto createPollOptionDto);
    void deletePollOption(UUID optionID);
}

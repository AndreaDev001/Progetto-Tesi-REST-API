package com.progettotirocinio.restapi.controllers;


import com.progettotirocinio.restapi.data.dto.input.PaginationRequest;
import com.progettotirocinio.restapi.data.dto.input.create.CreatePollDto;
import com.progettotirocinio.restapi.data.dto.output.PollDto;
import com.progettotirocinio.restapi.services.interfaces.PollService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/polls")
@RequiredArgsConstructor
public class PollController
{
    private final PollService pollService;

    @GetMapping("/private")
    public ResponseEntity<PagedModel<PollDto>> getPolls(@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<PollDto> pagedModel = this.pollService.getPolls(paginationRequest.toPageRequest());
        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/private/publisher/{publisherID}")
    public ResponseEntity<PagedModel<PollDto>> getPollsByPublisher(@PathVariable("publisherID") UUID publisherID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<PollDto> polls = this.pollService.getPollsByPublisher(publisherID,paginationRequest.toPageRequest());
        return ResponseEntity.ok(polls);
    }

    @GetMapping("/private/{pollID}")
    public ResponseEntity<PollDto> getPoll(@PathVariable("pollID") UUID pollID) {
        PollDto poll = this.pollService.getPoll(pollID);
        return ResponseEntity.ok(poll);
    }

    @GetMapping("/private/title/{title}")
    public ResponseEntity<PagedModel<PollDto>> getPollsByTitle(@PathVariable("title") String title,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<PollDto> polls = this.pollService.getPollsByTitle(title,paginationRequest.toPageRequest());
        return ResponseEntity.ok(polls);
    }

    @PostMapping("/private")
    public ResponseEntity<PollDto> createPoll(@RequestBody @Valid CreatePollDto createPollDto) {
        PollDto pollDto = this.pollService.createPoll(createPollDto);
        return ResponseEntity.status(201).body(pollDto);
    }

    @GetMapping("/private/description/{description}")
    public ResponseEntity<PagedModel<PollDto>> getPollsByDescription(@PathVariable("description") String description,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<PollDto> polls = this.pollService.getPollsByDescription(description,paginationRequest.toPageRequest());
        return ResponseEntity.ok(polls);
    }

    @GetMapping("/private/votes/minimum/{count}")
    public ResponseEntity<PagedModel<PollDto>> getPollsByMinimumVotes(@PathVariable("count") Integer count,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<PollDto> polls = this.pollService.getPollsByMinimumVotes(count,paginationRequest.toPageRequest());
        return ResponseEntity.ok(polls);
    }

    @GetMapping("/private/votes/maximum/{count}")
    public ResponseEntity<PagedModel<PollDto>> getPollsByMaximumVotes(@PathVariable("count") Integer count,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<PollDto> polls = this.pollService.getPollsByMaximumVotes(count,paginationRequest.toPageRequest());
        return ResponseEntity.ok(polls);
    }

    @DeleteMapping("/private/{pollID}")
    public ResponseEntity<Void> deletePoll(@PathVariable("pollID") UUID pollID) {
        this.pollService.deletePoll(pollID);
        return ResponseEntity.noContent().build();
    }
}

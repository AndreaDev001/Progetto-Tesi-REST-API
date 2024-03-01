package com.progettotirocinio.restapi.controllers;

import com.progettotirocinio.restapi.data.dto.input.PaginationRequest;
import com.progettotirocinio.restapi.data.dto.output.PollVoteDto;
import com.progettotirocinio.restapi.services.interfaces.PollVoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pollVotes")
public class PollVoteController
{
    private final PollVoteService pollVoteService;

    @GetMapping("/private")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<PollVoteDto>> getPollVotes(@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<PollVoteDto> pagedModel = this.pollVoteService.getPollVotes(paginationRequest.toPageRequest());
        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/private/{voteID}")
    @PreAuthorize("@permissionHandler.hasAccess(@pollVoteDao,#pollID)")
    public ResponseEntity<PollVoteDto> getPollVote(@PathVariable("pollID") UUID pollID) {
        PollVoteDto pollVoteDto = this.pollVoteService.getPollVote(pollID);
        return ResponseEntity.ok(pollVoteDto);
    }

    @GetMapping("/private/user/{userID}")
    @PreAuthorize("@permissionHandler.hasAccess(#userID)")
    public ResponseEntity<PagedModel<PollVoteDto>> getPollVotesByUser(@PathVariable("userID") UUID userID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<PollVoteDto> pagedModel = this.pollVoteService.getPollVotesByUser(userID,paginationRequest.toPageRequest());
        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/private/option/{optionID}")
    @PreAuthorize("@permissionHandler.hasAccess(@pollOptionDao,#optionID)")
    public ResponseEntity<PagedModel<PollVoteDto>> getPollVotesByOption(@PathVariable("optionID") UUID optionID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<PollVoteDto> pagedModel = this.pollVoteService.getPollVotesByOption(optionID,paginationRequest.toPageRequest());
        return ResponseEntity.ok(pagedModel);
    }

    @PostMapping("/private/{optionID}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_MEMBER')")
    public ResponseEntity<PollVoteDto> createPollVote(@PathVariable("optionID") UUID optionID) {
        PollVoteDto pollVoteDto = this.pollVoteService.createVote(optionID);
        return ResponseEntity.status(201).body(pollVoteDto);
    }

    @GetMapping("/private/user/{userID}/option/{optionID}")
    @PreAuthorize("@permissionHandler.hasAccess(#userID)")
    public ResponseEntity<PollVoteDto> hasVote(@PathVariable("userID") UUID userID,@PathVariable("optionID") UUID optionID) {
        PollVoteDto pollVoteDto = this.pollVoteService.getVoteBetween(userID,optionID);
        return ResponseEntity.ok(pollVoteDto);
    }

    @DeleteMapping("/private/{voteID}")
    @PreAuthorize("@permissionHandler.hasAccess(@pollVoteDao,#voteID)")
    public ResponseEntity<Void> deleteVote(@PathVariable("voteID") UUID voteID) {
        this.pollVoteService.deletePollVote(voteID);
        return ResponseEntity.noContent().build();
    }

}

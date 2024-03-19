package com.progettotirocinio.restapi.controllers.polls;


import com.progettotirocinio.restapi.data.dao.specifications.PollSpecifications;
import com.progettotirocinio.restapi.data.dto.input.PaginationRequest;
import com.progettotirocinio.restapi.data.dto.input.create.polls.CreatePollDto;
import com.progettotirocinio.restapi.data.dto.input.update.polls.UpdatePollDto;
import com.progettotirocinio.restapi.data.dto.output.polls.PollDto;
import com.progettotirocinio.restapi.data.entities.enums.PollStatus;
import com.progettotirocinio.restapi.services.interfaces.polls.PollService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/polls")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
public class PollController
{
    private final PollService pollService;

    @GetMapping("/private")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<PollDto>> getPolls(@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<PollDto> pagedModel = this.pollService.getPolls(paginationRequest.toPageRequest());
        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/private/publisher/{publisherID}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_MEMBER')")
    public ResponseEntity<PagedModel<PollDto>> getPollsByPublisher(@PathVariable("publisherID") UUID publisherID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<PollDto> polls = this.pollService.getPollsByPublisher(publisherID,paginationRequest.toPageRequest());
        return ResponseEntity.ok(polls);
    }

    @GetMapping("/public/statues")
    public ResponseEntity<CollectionModel<PollStatus>> getStatues() {
        return ResponseEntity.ok(this.pollService.getStatues());
    }

    @GetMapping("/private/{pollID}")
    @PreAuthorize("@permissionHandler.hasAccess(@pollDao,#pollID)")
    public ResponseEntity<PollDto> getPoll(@PathVariable("pollID") UUID pollID) {
        PollDto poll = this.pollService.getPoll(pollID);
        return ResponseEntity.ok(poll);
    }

    @GetMapping("/private/title/{title}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_MEMBER')")
    public ResponseEntity<PagedModel<PollDto>> getPollsByTitle(@PathVariable("title") String title,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<PollDto> polls = this.pollService.getPollsByTitle(title,paginationRequest.toPageRequest());
        return ResponseEntity.ok(polls);
    }

    @GetMapping("/private/spec")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_MEMBER')")
    public ResponseEntity<PagedModel<PollDto>> getPollsBySpec(@ParameterObject @Valid PollSpecifications.Filter filter,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<PollDto> polls = this.pollService.getPollsBySpec(PollSpecifications.withFilter(filter),paginationRequest.toPageRequest());
        return ResponseEntity.ok(polls);
    }

    @GetMapping("/private/similar/{pollID}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_MEMBER')")
    public ResponseEntity<PagedModel<PollDto>> getSimilarPolls(@PathVariable("pollID") UUID pollID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<PollDto> polls = this.pollService.getSimilarPolls(pollID,paginationRequest.toPageRequest());
        return ResponseEntity.ok(polls);
    }

    @GetMapping("/public/orderTypes")
    public ResponseEntity<CollectionModel<String>> getOrderTypes() {
        return ResponseEntity.ok(this.pollService.getOrderTypes());
    }

    @PostMapping("/private")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_MEMBER')")
    public ResponseEntity<PollDto> createPoll(@RequestBody @Valid CreatePollDto createPollDto) {
        PollDto pollDto = this.pollService.createPoll(createPollDto);
        return ResponseEntity.status(201).body(pollDto);
    }

    @PutMapping("/private")
    @PreAuthorize("@permissionHandler.hasAccess(@pollDao,#updatePollDto.pollID)")
    public ResponseEntity<PollDto> updatePoll(@RequestBody @Valid UpdatePollDto updatePollDto) {
        PollDto pollDto = this.pollService.updatePoll(updatePollDto);
        return ResponseEntity.ok(pollDto);
    }

    @GetMapping("/private/description/{description}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_MEMBER')")
    public ResponseEntity<PagedModel<PollDto>> getPollsByDescription(@PathVariable("description") String description,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<PollDto> polls = this.pollService.getPollsByDescription(description,paginationRequest.toPageRequest());
        return ResponseEntity.ok(polls);
    }

    @GetMapping("/private/votes/minimum/{count}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_MEMBER')")
    public ResponseEntity<PagedModel<PollDto>> getPollsByMinimumVotes(@PathVariable("count") Integer count,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<PollDto> polls = this.pollService.getPollsByMinimumVotes(count,paginationRequest.toPageRequest());
        return ResponseEntity.ok(polls);
    }

    @GetMapping("/private/votes/maximum/{count}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_MEMBER')")
    public ResponseEntity<PagedModel<PollDto>> getPollsByMaximumVotes(@PathVariable("count") Integer count,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<PollDto> polls = this.pollService.getPollsByMaximumVotes(count,paginationRequest.toPageRequest());
        return ResponseEntity.ok(polls);
    }

    @DeleteMapping("/private/{pollID}")
    @PreAuthorize("@permissionHandler.hasAccess(@pollDao,#pollID)")
    public ResponseEntity<Void> deletePoll(@PathVariable("pollID") UUID pollID) {
        this.pollService.deletePoll(pollID);
        return ResponseEntity.noContent().build();
    }
}

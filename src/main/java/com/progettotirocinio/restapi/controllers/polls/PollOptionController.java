package com.progettotirocinio.restapi.controllers.polls;

import com.progettotirocinio.restapi.data.dto.input.PaginationRequest;
import com.progettotirocinio.restapi.data.dto.input.create.polls.CreatePollOptionDto;
import com.progettotirocinio.restapi.data.dto.input.update.polls.UpdatePollOptionDto;
import com.progettotirocinio.restapi.data.dto.output.polls.PollOptionDto;
import com.progettotirocinio.restapi.data.entities.polls.Poll;
import com.progettotirocinio.restapi.services.interfaces.polls.PollOptionService;
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
@RequiredArgsConstructor
@RequestMapping("/pollOptions")
public class PollOptionController
{
    private final PollOptionService pollOptionService;

    @GetMapping("/private")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<PollOptionDto>> getPollOptions(@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<PollOptionDto> pagedModel = this.pollOptionService.getPollOptions(paginationRequest.toPageRequest());
        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/private/{optionID}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_MEMBER')")
    public ResponseEntity<PollOptionDto> getPollOption(@PathVariable("optionID") UUID optionID) {
        PollOptionDto pollOptionDto = this.pollOptionService.getPollOption(optionID);
        return ResponseEntity.ok(pollOptionDto);
    }

    @GetMapping("/private/poll/{pollID}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_MEMBER')")
    public ResponseEntity<CollectionModel<PollOptionDto>> getPollOptionsByPoll(@PathVariable("pollID") UUID pollID) {
        CollectionModel<PollOptionDto> collectionModel = this.pollOptionService.getPollOptionsByPoll(pollID);
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/private/description/{description}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_MEMBER')")
    public ResponseEntity<PagedModel<PollOptionDto>> getPollOptionsByDescription(@PathVariable("description") String description,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<PollOptionDto> pollOptions = this.pollOptionService.getPollOptionsByDescription(description,paginationRequest.toPageRequest());
        return ResponseEntity.ok(pollOptions);
    }

    @GetMapping("/private/name/{name}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_MEMBER')")
    public ResponseEntity<PagedModel<PollOptionDto>> getPollOptionsByName(@PathVariable("name") String name,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<PollOptionDto> pagedModel = this.pollOptionService.getPollOptionsByName(name,paginationRequest.toPageRequest());
        return ResponseEntity.ok(pagedModel);
    }

    @PostMapping("/private")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_MEMBER')")
    public ResponseEntity<PollOptionDto> createPollOption(@RequestBody @Valid CreatePollOptionDto createPollOptionDto) {
        PollOptionDto pollOptionDto = this.pollOptionService.createOption(createPollOptionDto);
        return ResponseEntity.status(201).body(pollOptionDto);
    }

    @PutMapping("/private")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_MEMBER')")
    public ResponseEntity<PollOptionDto> updatePollOption(@RequestBody @Valid UpdatePollOptionDto updatePollOptionDto) {
        PollOptionDto pollOptionDto = this.pollOptionService.updateOption(updatePollOptionDto);
        return ResponseEntity.ok(pollOptionDto);
    }

    @DeleteMapping("/private/{optionID}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deletePollOption(@PathVariable("optionID") UUID optionID) {
        this.pollOptionService.deletePollOption(optionID);
        return ResponseEntity.noContent().build();
    }
}

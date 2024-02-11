package com.progettotirocinio.restapi.controllers;

import com.progettotirocinio.restapi.data.dto.input.PaginationRequest;
import com.progettotirocinio.restapi.data.dto.input.create.CreatePollOptionDto;
import com.progettotirocinio.restapi.data.dto.output.PollOptionDto;
import com.progettotirocinio.restapi.services.interfaces.PollOptionService;
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
    public ResponseEntity<PagedModel<PollOptionDto>> getPollOptionsByPoll(@PathVariable("pollID") UUID pollID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<PollOptionDto> pagedModel = this.pollOptionService.getPollOptionsByPoll(pollID,paginationRequest.toPageRequest());
        return ResponseEntity.ok(pagedModel);
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
        return ResponseEntity.ok(pollOptionDto);
    }

    @DeleteMapping("/private/{optionID}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deletePollOption(@PathVariable("optionID") UUID optionID) {
        this.pollOptionService.deletePollOption(optionID);
        return ResponseEntity.noContent().build();
    }
}

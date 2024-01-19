package com.progettotirocinio.restapi.controllers;


import com.progettotirocinio.restapi.data.dto.input.PaginationRequest;
import com.progettotirocinio.restapi.data.dto.input.create.CreateDiscussionDto;
import com.progettotirocinio.restapi.data.dto.output.DiscussionDto;
import com.progettotirocinio.restapi.data.entities.Discussion;
import com.progettotirocinio.restapi.services.interfaces.DiscussionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/discussions")
@RequiredArgsConstructor
public class DiscussionController
{
    private final DiscussionService discussionService;

    @GetMapping("/private")
    public ResponseEntity<PagedModel<DiscussionDto>> getDiscussions(@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<DiscussionDto> pagedModel = this.discussionService.getDiscussion(paginationRequest.toPageRequest());
        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/private/{discussionID}")
    public ResponseEntity<DiscussionDto> getDiscussion(@PathVariable("discussionID") UUID discussionID) {
        DiscussionDto discussion = this.discussionService.getDiscussion(discussionID);
        return ResponseEntity.ok(discussion);
    }

    @PostMapping("/private")
    public ResponseEntity<DiscussionDto> createDiscussion(@RequestBody @Valid CreateDiscussionDto createDiscussionDto) {
        DiscussionDto discussionDto = this.discussionService.createDiscussion(createDiscussionDto);
        return ResponseEntity.status(201).body(discussionDto);
    }

    @GetMapping("/private/publisher/{publisherID}")
    public ResponseEntity<PagedModel<DiscussionDto>> getDiscussionsByPublisher(@PathVariable("publisherID") UUID publisherID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<DiscussionDto> discussions = this.discussionService.getDiscussionsByPublisher(publisherID,paginationRequest.toPageRequest());
        return ResponseEntity.ok(discussions);
    }

    @GetMapping("/private/title/{title}")
    public ResponseEntity<PagedModel<DiscussionDto>> getDiscussionsByTitle(@PathVariable("title") String title,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<DiscussionDto> discussions = this.discussionService.getDiscussionsByTitle(title,paginationRequest.toPageRequest());
        return ResponseEntity.ok(discussions);
    }

    @GetMapping("/private/topic/{topic}")
    public ResponseEntity<PagedModel<DiscussionDto>> getDiscussionsByTopic(@PathVariable("topic") String topic,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<DiscussionDto> discussions = this.discussionService.getDiscussionsByTopic(topic,paginationRequest.toPageRequest());
        return ResponseEntity.ok(discussions);
    }

    @DeleteMapping("/private/{discussionID}")
    public ResponseEntity<Void> deleteDiscussion(@PathVariable("discussionID") UUID discussionID) {
        this.discussionService.deleteDiscussion(discussionID);
        return ResponseEntity.noContent().build();
    }
}

package com.progettotirocinio.restapi.controllers;


import com.progettotirocinio.restapi.data.dao.specifications.DiscussionSpecifications;
import com.progettotirocinio.restapi.data.dto.input.PaginationRequest;
import com.progettotirocinio.restapi.data.dto.input.create.CreateDiscussionDto;
import com.progettotirocinio.restapi.data.dto.input.update.UpdateDiscussionDto;
import com.progettotirocinio.restapi.data.dto.output.DiscussionDto;
import com.progettotirocinio.restapi.data.entities.Discussion;
import com.progettotirocinio.restapi.services.interfaces.DiscussionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/discussions")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
public class DiscussionController
{
    private final DiscussionService discussionService;

    @GetMapping("/private")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<DiscussionDto>> getDiscussions(@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<DiscussionDto> pagedModel = this.discussionService.getDiscussion(paginationRequest.toPageRequest());
        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/private/{discussionID}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_MEMBER')")
    public ResponseEntity<DiscussionDto> getDiscussion(@PathVariable("discussionID") UUID discussionID) {
        DiscussionDto discussion = this.discussionService.getDiscussion(discussionID);
        return ResponseEntity.ok(discussion);
    }

    @GetMapping("/private/spec")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_MEMBER')")
    public ResponseEntity<PagedModel<DiscussionDto>> getDiscussionsBySpec(@ParameterObject @Valid DiscussionSpecifications.Filter filter,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<DiscussionDto> discussions = this.discussionService.getDiscussionsBySpec(DiscussionSpecifications.withFilter(filter),paginationRequest.toPageRequest());
        return ResponseEntity.ok(discussions);
    }

    @GetMapping("/private/similar/{discussionID}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_MEMBER')")
    public ResponseEntity<PagedModel<DiscussionDto>> getSimilarDiscussions(@PathVariable("discussionID") UUID discussionID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<DiscussionDto> discussions = this.discussionService.getSimilarDiscussions(discussionID,paginationRequest.toPageRequest());
        return ResponseEntity.ok(discussions);
    }

    @GetMapping("/private/text/{text}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<DiscussionDto>> getDiscussionsByText(@PathVariable("text") String text,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<DiscussionDto> discussions = this.discussionService.getDiscussionsByText(text,paginationRequest.toPageRequest());
        return ResponseEntity.ok(discussions);
    }

    @GetMapping("/public/orderTypes")
    public ResponseEntity<CollectionModel<String>> getOrderTypes() {
        return ResponseEntity.ok(this.discussionService.getOrderTypes());
    }

    @PostMapping("/private")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_MEMBER')")
    public ResponseEntity<DiscussionDto> createDiscussion(@RequestBody @Valid CreateDiscussionDto createDiscussionDto) {
        DiscussionDto discussionDto = this.discussionService.createDiscussion(createDiscussionDto);
        return ResponseEntity.status(201).body(discussionDto);
    }

    @PutMapping("/private")
    @PreAuthorize("@permissionHandler.hasAccess(@discussionDao,#updateDiscussionDto.discussionID)")
    public ResponseEntity<DiscussionDto> updateDiscussion(@RequestBody @Valid UpdateDiscussionDto updateDiscussionDto) {
        DiscussionDto discussionDto = this.discussionService.updateDiscussion(updateDiscussionDto);
        return ResponseEntity.ok(discussionDto);
    }

    @GetMapping("/private/publisher/{publisherID}")
    public ResponseEntity<PagedModel<DiscussionDto>> getDiscussionsByPublisher(@PathVariable("publisherID") UUID publisherID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<DiscussionDto> discussions = this.discussionService.getDiscussionsByPublisher(publisherID,paginationRequest.toPageRequest());
        return ResponseEntity.ok(discussions);
    }

    @GetMapping("/private/title/{title}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_MEMBER')")
    public ResponseEntity<PagedModel<DiscussionDto>> getDiscussionsByTitle(@PathVariable("title") String title,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<DiscussionDto> discussions = this.discussionService.getDiscussionsByTitle(title,paginationRequest.toPageRequest());
        return ResponseEntity.ok(discussions);
    }

    @GetMapping("/private/topic/{topic}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_MEMBER')")
    public ResponseEntity<PagedModel<DiscussionDto>> getDiscussionsByTopic(@PathVariable("topic") String topic,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<DiscussionDto> discussions = this.discussionService.getDiscussionsByTopic(topic,paginationRequest.toPageRequest());
        return ResponseEntity.ok(discussions);
    }

    @DeleteMapping("/private/{discussionID}")
    @PreAuthorize("@permissionHandler.hasAccess(@discussionDao,#discussionID)")
    public ResponseEntity<Void> deleteDiscussion(@PathVariable("discussionID") UUID discussionID) {
        this.discussionService.deleteDiscussion(discussionID);
        return ResponseEntity.noContent().build();
    }
}

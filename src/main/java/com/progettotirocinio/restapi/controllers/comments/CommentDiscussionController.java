package com.progettotirocinio.restapi.controllers.comments;

import com.progettotirocinio.restapi.data.dto.input.PaginationRequest;
import com.progettotirocinio.restapi.data.dto.input.create.CreateCommentDto;
import com.progettotirocinio.restapi.data.dto.output.comments.CommentDiscussionDto;
import com.progettotirocinio.restapi.services.interfaces.comments.CommentDiscussionService;
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
@RequestMapping("/commentDiscussions")
@RequiredArgsConstructor
public class CommentDiscussionController
{
    private final CommentDiscussionService commentDiscussionService;

    @GetMapping("/private")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<CommentDiscussionDto>> getComments(@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<CommentDiscussionDto> comments = this.commentDiscussionService.getComments(paginationRequest.toPageRequest());
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/private/{commentID}")
    @PreAuthorize("@permissionHandler.hasAccess(#commentID,@commentDao)")
    public ResponseEntity<CommentDiscussionDto> getCommentDiscussion(@PathVariable("commentID")UUID commentID) {
        CommentDiscussionDto commentDiscussionDto = this.commentDiscussionService.getCommentDiscussion(commentID);
        return ResponseEntity.ok(commentDiscussionDto);
    }

    @GetMapping("/private/discussion/{discussionID}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_MEMBER')")
    public ResponseEntity<CollectionModel<CommentDiscussionDto>> getCommentsByDiscussion(@PathVariable("discussionID") UUID discussionID) {
        CollectionModel<CommentDiscussionDto> comments = this.commentDiscussionService.getCommentsByDiscussion(discussionID);
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/private/discussion/{discussionID}/publisher/{publisherID}")
    @PreAuthorize("@permissionHandler.hasAccess(#publisherID) and @permissionHandler.hasRole('ROLE_MEMBER')")
    public ResponseEntity<CommentDiscussionDto> getCommentDiscussionByDiscussionAndPublisher(@PathVariable("discussionID") UUID discussionID,@PathVariable("publisherID") UUID publisherID) {
        CommentDiscussionDto commentDiscussionDto = this.commentDiscussionService.getCommentByDiscussionAndUser(discussionID,publisherID);
        return ResponseEntity.ok(commentDiscussionDto);
    }

    @PostMapping("/private/discussion/{discussionID}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_MEMBER')")
    public ResponseEntity<CommentDiscussionDto> createCommentDiscussion(@PathVariable("discussionID") UUID discussionID, @RequestBody @Valid CreateCommentDto createCommentDto) {
        CommentDiscussionDto commentDiscussionDto = this.commentDiscussionService.createCommentDiscussion(discussionID,createCommentDto);
        return ResponseEntity.status(201).body(commentDiscussionDto);
    }

    @DeleteMapping("/private/{commentID}")
    @PreAuthorize("@permissionHandler.hasAccess(#commentID,@commentDao)")
    public ResponseEntity<Void> deleteComment(@PathVariable("commentID") UUID commentID) {
        this.commentDiscussionService.deleteCommentDiscussion(commentID);
        return ResponseEntity.noContent().build();
    }
}

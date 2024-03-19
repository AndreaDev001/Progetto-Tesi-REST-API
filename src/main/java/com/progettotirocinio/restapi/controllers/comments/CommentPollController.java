package com.progettotirocinio.restapi.controllers.comments;

import com.progettotirocinio.restapi.data.dto.input.PaginationRequest;
import com.progettotirocinio.restapi.data.dto.input.create.CreateCommentDto;
import com.progettotirocinio.restapi.data.dto.output.comments.CommentPollDto;
import com.progettotirocinio.restapi.services.interfaces.comments.CommentPollService;
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
@RequestMapping("/commentPolls")
@RequiredArgsConstructor
public class CommentPollController
{
    private final CommentPollService commentPollService;

    @GetMapping("/private")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<CommentPollDto>> getComments(@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<CommentPollDto> commentPolls = this.commentPollService.getComments(paginationRequest.toPageRequest());
        return ResponseEntity.ok(commentPolls);
    }

    @GetMapping("/private/{commentID}")
    @PreAuthorize("@permissionHandler.hasAccess(#commentID,@commentDao)")
    public ResponseEntity<CommentPollDto> getComment(@PathVariable("commentID")UUID commentID) {
        CommentPollDto commentPollDto = this.commentPollService.getCommentPoll(commentID);
        return ResponseEntity.ok(commentPollDto);
    }

    @GetMapping("/private/poll/{pollID}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_MEMBER')")
    public ResponseEntity<CollectionModel<CommentPollDto>> getCommentsByPoll(@PathVariable("pollID") UUID pollID) {
        CollectionModel<CommentPollDto> collectionModel = this.commentPollService.getCommentsByPoll(pollID);
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/private/poll/{pollID}/publisher/{publisherID}")
    @PreAuthorize("@permissionHandler.hasAccess(#publisherID) and @permissionHandler.hasRole('ROLE_MEMBER')")
    public ResponseEntity<CommentPollDto> getCommentPollByPollAndPublisher(@PathVariable("pollID") UUID pollID,@PathVariable("publisherID") UUID publisherID) {
        CommentPollDto commentPollDto = this.commentPollService.getCommentByPollAndUser(pollID,publisherID);
        return ResponseEntity.ok(commentPollDto);
    }

    @PostMapping("/private/poll/{pollID}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_MEMBER')")
    public ResponseEntity<CommentPollDto> createCommentPoll(@PathVariable("pollID") UUID pollID, @RequestBody @Valid CreateCommentDto createCommentDto) {
        CommentPollDto commentPollDto = this.commentPollService.createCommentPoll(pollID,createCommentDto);
        return ResponseEntity.status(201).body(commentPollDto);
    }

    @DeleteMapping("/private/{commentID}")
    @PreAuthorize("@permissionHandler.hasAccess(#commentID,@commentDao)")
    public ResponseEntity<Void> deleteComment(@PathVariable("commentID") UUID commentID) {
        this.commentPollService.deleteCommentPoll(commentID);
        return ResponseEntity.noContent().build();
    }
}

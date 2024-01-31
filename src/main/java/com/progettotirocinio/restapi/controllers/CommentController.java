package com.progettotirocinio.restapi.controllers;


import com.progettotirocinio.restapi.data.dao.CommentDao;
import com.progettotirocinio.restapi.data.dto.input.PaginationRequest;
import com.progettotirocinio.restapi.data.dto.input.create.CreateCommentDto;
import com.progettotirocinio.restapi.data.dto.input.update.UpdateCommentDto;
import com.progettotirocinio.restapi.data.dto.output.CommentDto;
import com.progettotirocinio.restapi.data.entities.Comment;
import com.progettotirocinio.restapi.services.interfaces.CommentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.sql.Update;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
public class CommentController
{
    private final CommentService commentService;

    @GetMapping("/private")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<CommentDto>>  getComments(@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<CommentDto> pagedModel = this.commentService.getComments(paginationRequest.toPageRequest());
        return ResponseEntity.ok(pagedModel);
    }
    @GetMapping("/private/{commentID}")
    @PreAuthorize("@permissionHandler.hasAccess(#commentID)")
    public ResponseEntity<CommentDto> getComment(@PathVariable("commentID") UUID commentID) {
        CommentDto comment = this.commentService.getComment(commentID);
        return ResponseEntity.ok(comment);
    }
    @GetMapping("/private/publisher/{publisherID}")
    @PreAuthorize("@permissionHandler.hasAccess(#publisherID)")
    public ResponseEntity<PagedModel<CommentDto>> getCommentsByPublisher(@PathVariable("publisherID") UUID publisherID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<CommentDto> comments = this.commentService.getCommentsByPublisher(publisherID,paginationRequest.toPageRequest());
        return ResponseEntity.ok(comments);
    }

    @PostMapping("/private")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_MEMBER')")
    public ResponseEntity<CommentDto> createComment(@RequestBody @Valid CreateCommentDto createCommentDto) {
        CommentDto commentDto = this.commentService.createComment(createCommentDto);
        return ResponseEntity.status(201).body(commentDto);
    }

    @PutMapping("/private")
    @PreAuthorize("@permissionHandler.hasAccess(@commentDao,#updateCommentDto.commentID)")
    public ResponseEntity<CommentDto> updateComment(@RequestBody @Valid UpdateCommentDto updateCommentDto) {
        CommentDto commentDto = this.commentService.updateComment(updateCommentDto);
        return ResponseEntity.ok(commentDto);
    }

    @GetMapping("/private/discussion/{discussionID}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_MEMBER')")
    public ResponseEntity<PagedModel<CommentDto>> getCommentsByDiscussion(@PathVariable("discussionID") UUID discussionID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<CommentDto> comments = this.commentService.getCommentsByDiscussion(discussionID,paginationRequest.toPageRequest());
        return ResponseEntity.ok(comments);
    }

    @DeleteMapping("/private/{commentID}")
    @PreAuthorize("@permissionHandler.hasAccess(@commentDao,#commentID)")
    public ResponseEntity<Void> deleteComment(@PathVariable("commentID") UUID commentID) {
        this.commentService.deleteComment(commentID);
        return ResponseEntity.noContent().build();
    }
}

package com.progettotirocinio.restapi.controllers;


import com.progettotirocinio.restapi.data.dao.CommentDao;
import com.progettotirocinio.restapi.data.dto.input.PaginationRequest;
import com.progettotirocinio.restapi.data.dto.input.create.CreateCommentDto;
import com.progettotirocinio.restapi.data.dto.input.update.UpdateCommentDto;
import com.progettotirocinio.restapi.data.dto.output.CommentDto;
import com.progettotirocinio.restapi.data.entities.Comment;
import com.progettotirocinio.restapi.services.interfaces.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.sql.Update;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController
{
    private final CommentService commentService;

    @GetMapping("/private")
    public ResponseEntity<PagedModel<CommentDto>>  getComments(@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<CommentDto> pagedModel = this.commentService.getComments(paginationRequest.toPageRequest());
        return ResponseEntity.ok(pagedModel);
    }
    @GetMapping("/private/{commentID}")
    public ResponseEntity<CommentDto> getComment(@PathVariable("commentID") UUID commentID) {
        CommentDto comment = this.commentService.getComment(commentID);
        return ResponseEntity.ok(comment);
    }
    @GetMapping("/private/publisher/{publisherID}")
    public ResponseEntity<PagedModel<CommentDto>> getCommentsByPublisher(@PathVariable("publisherID") UUID publisherID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<CommentDto> comments = this.commentService.getCommentsByPublisher(publisherID,paginationRequest.toPageRequest());
        return ResponseEntity.ok(comments);
    }

    @PostMapping("/private")
    public ResponseEntity<CommentDto> createComment(@RequestBody @Valid CreateCommentDto createCommentDto) {
        CommentDto commentDto = this.commentService.createComment(createCommentDto);
        return ResponseEntity.status(201).body(commentDto);
    }

    @PutMapping("/private")
    public ResponseEntity<CommentDto> updateComment(@RequestBody @Valid UpdateCommentDto updateCommentDto) {
        CommentDto commentDto = this.commentService.updateComment(updateCommentDto);
        return ResponseEntity.ok(commentDto);
    }

    @GetMapping("/private/discussion/{discussionID}")
    public ResponseEntity<PagedModel<CommentDto>> getCommentsByDiscussion(@PathVariable("discussionID") UUID discussionID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<CommentDto> comments = this.commentService.getCommentsByDiscussion(discussionID,paginationRequest.toPageRequest());
        return ResponseEntity.ok(comments);
    }

    @DeleteMapping("/private/{commentID}")
    public ResponseEntity<Void> deleteComment(@PathVariable("commentID") UUID commentID) {
        this.commentService.deleteComment(commentID);
        return ResponseEntity.noContent().build();
    }
}

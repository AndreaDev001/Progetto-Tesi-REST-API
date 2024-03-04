package com.progettotirocinio.restapi.controllers.comments;

import com.progettotirocinio.restapi.data.dto.input.PaginationRequest;
import com.progettotirocinio.restapi.data.dto.output.comments.CommentDto;
import com.progettotirocinio.restapi.data.entities.enums.CommentType;
import com.progettotirocinio.restapi.services.interfaces.comments.CommentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public ResponseEntity<PagedModel<CommentDto>> getComments(@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<CommentDto> comments = this.commentService.getComments(paginationRequest.toPageRequest());
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/private/{commentID}")
    @PreAuthorize("@permissionHandler.hasAccess(#commentID,@commentDao)")
    public ResponseEntity<CommentDto> getComment(@PathVariable("commentID") UUID commentID) {
        CommentDto commentDto = this.commentService.getCommentByID(commentID);
        return ResponseEntity.ok(commentDto);
    }

    @GetMapping("/private/publisher/{publisherID}")
    @PreAuthorize("@permissionHandler.hasAccess(#publisherID)")
    public ResponseEntity<PagedModel<CommentDto>> getCommentsByPublisher(@PathVariable("publisherID") UUID publisherID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<CommentDto> comments = this.commentService.getCommentsByPublisher(publisherID,paginationRequest.toPageRequest());
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/private/title/{title}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<CommentDto>> getCommentsByTitle(@PathVariable("title") String title,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<CommentDto> comments = this.commentService.getCommentsByTitle(title,paginationRequest.toPageRequest());
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/private/text/{text}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<CommentDto>> getCommentsByText(@PathVariable("text") String text,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<CommentDto> comments = this.commentService.getCommentsByText(text,paginationRequest.toPageRequest());
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/public/types")
    public ResponseEntity<CollectionModel<CommentType>> getCommentTypes() {
        return ResponseEntity.ok(this.commentService.getTypes());
    }

    @GetMapping("/private/type/{type}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<CommentDto>> getCommentsByTypes(@PathVariable("type") CommentType type,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<CommentDto> comments = this.commentService.getCommentsByType(type,paginationRequest.toPageRequest());
        return ResponseEntity.ok(comments);
    }

    @DeleteMapping("/private/{commentID}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteComment(@PathVariable("commentID") UUID commentID) {
        this.commentService.deleteComment(commentID);
        return ResponseEntity.noContent().build();
    }
}

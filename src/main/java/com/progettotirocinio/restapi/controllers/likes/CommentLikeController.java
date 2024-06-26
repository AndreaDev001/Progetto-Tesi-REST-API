package com.progettotirocinio.restapi.controllers.likes;

import com.progettotirocinio.restapi.data.dto.input.PaginationRequest;
import com.progettotirocinio.restapi.data.dto.output.likes.CommentLikeDto;
import com.progettotirocinio.restapi.services.interfaces.likes.CommentLikeService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/commentLikes")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
public class CommentLikeController
{
    private final CommentLikeService commentLikeService;

    @GetMapping("/private")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<CommentLikeDto>> getCommentLikes(@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<CommentLikeDto> commentLikes = this.commentLikeService.getCommentLikes(paginationRequest.toPageRequest());
        return ResponseEntity.ok(commentLikes);
    }

    @GetMapping("/private/{commentLikeID}")
    @PreAuthorize("@permissionHandler.hasAccess(@commentLikeDao,#commentLikeID)")
    public ResponseEntity<CommentLikeDto> getCommentLike(@PathVariable("commentLikeID") UUID commentLikeID) {
        CommentLikeDto commentLikeDto = this.commentLikeService.getCommentLike(commentLikeID);
        return ResponseEntity.ok(commentLikeDto);
    }

    @GetMapping("/public/user/{userID}")
    public ResponseEntity<PagedModel<CommentLikeDto>> getCommentLikesByUser(@PathVariable("userID") UUID userID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<CommentLikeDto> commentLikes = this.commentLikeService.getCommentsLikesByUser(userID,paginationRequest.toPageRequest());
        return ResponseEntity.ok(commentLikes);
    }

    @PostMapping("/private/comment/{commentID}")
    public ResponseEntity<CommentLikeDto> createLike(@PathVariable("commentID") UUID commentID) {
        CommentLikeDto commentLikeDto = this.commentLikeService.createLike(commentID);
        return ResponseEntity.ok(commentLikeDto);
    }

    @GetMapping("/private/user/{userID}/comment/{commentID}")
    @PreAuthorize("@permissionHandler.hasAccess(#userID)")
    public ResponseEntity<CommentLikeDto> hasCommentLike(@PathVariable("userID") UUID userID,@PathVariable("commentID") UUID commentID) {
        CommentLikeDto commentLikeDto = this.commentLikeService.hasLike(userID,commentID);
        return ResponseEntity.ok(commentLikeDto);
    }

    @GetMapping("/private/comment/{commentID}")
    @PreAuthorize("@permissionHandler.hasAccess(@commentDao,#commentID)")
    public ResponseEntity<PagedModel<CommentLikeDto>> getCommentLikesByComment(@PathVariable("commentID") UUID commentID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<CommentLikeDto> commentLikes = this.commentLikeService.getCommentsLikesByComment(commentID,paginationRequest.toPageRequest());
        return ResponseEntity.ok(commentLikes);
    }

    @DeleteMapping("/private/{commentLikeID}")
    @PreAuthorize("@permissionHandler.hasAccess(@commentLikeDao,#commentlikeID)")
    public ResponseEntity<Void> deleteCommentLike(@PathVariable("commentLikeID") UUID commentLikeID) {
        this.commentLikeService.deleteCommentLike(commentLikeID);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/private/comment/{commentID}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_MEMBER')")
    public ResponseEntity<Void> deleteCommentLikeByComment(@PathVariable("commentID") UUID commentID) {
        this.commentLikeService.deleteCommentLikeByComment(commentID);
        return ResponseEntity.noContent().build();
    }
}

package com.progettotirocinio.restapi.controllers.likes;

import com.progettotirocinio.restapi.data.dto.input.PaginationRequest;
import com.progettotirocinio.restapi.data.dto.output.likes.DiscussionLikeDto;
import com.progettotirocinio.restapi.data.entities.likes.DiscussionLike;
import com.progettotirocinio.restapi.services.interfaces.likes.DiscussionLikeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/discussionLikes")
@RequiredArgsConstructor
public class DiscussionLikeController
{
    private final DiscussionLikeService discussionLikeService;

    @GetMapping("/private")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<DiscussionLikeDto>> getDiscussionLikes(@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<DiscussionLikeDto> discussionLikes = this.discussionLikeService.getDiscussionLikes(paginationRequest.toPageRequest());
        return ResponseEntity.ok(discussionLikes);
    }

    @GetMapping("/private/{discussionLikeID}")
    @PreAuthorize("@permissionHandler.hasAccess(@discussionLikeDao,#discussionLikeID)")
    public ResponseEntity<DiscussionLikeDto> getDiscussionLike(@PathVariable("discussionLikeID")UUID discussionLikeID) {
        DiscussionLikeDto discussionLikeDto = this.discussionLikeService.getDiscussionLike(discussionLikeID);
        return ResponseEntity.ok(discussionLikeDto);
    }

    @GetMapping("/private/user/{userID}")
    @PreAuthorize("@permissionHandler.hasAccess(#userID)")
    public ResponseEntity<PagedModel<DiscussionLikeDto>> getDiscussionLikesByUser(@PathVariable("userID") UUID userID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<DiscussionLikeDto> discussionLikes = this.discussionLikeService.getDiscussionLikesByUser(userID,paginationRequest.toPageRequest());
        return ResponseEntity.ok(discussionLikes);
    }

    @PostMapping("/private/{discussionID}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_MEMBER')")
    public ResponseEntity<DiscussionLikeDto> createDiscussion(@PathVariable("discussionID") UUID discussionID) {
        DiscussionLikeDto discussionLikeDto = this.discussionLikeService.createLike(discussionID);
        return ResponseEntity.ok(discussionLikeDto);
    }

    @GetMapping("/private/discussion/{discussionID}")
    @PreAuthorize("@permissionHandler.hasAccess(@discussionDao,#discussionID)")
    public ResponseEntity<PagedModel<DiscussionLikeDto>> getDiscussionLikesByDiscussion(@PathVariable("discussionID") UUID discussionID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<DiscussionLikeDto> discussionLikes = this.discussionLikeService.getDiscussionLikesByDiscussion(discussionID,paginationRequest.toPageRequest());
        return ResponseEntity.ok(discussionLikes);
    }

    @GetMapping("/private/user/{userID}/discussion/{discussionID}")
    @PreAuthorize("@permissionHandler.hasAccess(#userID)")
    public ResponseEntity<DiscussionLikeDto> hasLike(@PathVariable("userID") UUID userID,@PathVariable("discussionID") UUID discussionID) {
        DiscussionLikeDto discussionLikeDto = this.discussionLikeService.hasLike(userID,discussionID);
        return ResponseEntity.ok(discussionLikeDto);
    }

    @DeleteMapping("/private/{discussionLikeID}")
    @PreAuthorize("@permissionHandler.hasAccess(@discussionLikeDao,#discussionLikeID)")
    public ResponseEntity<Void> deleteDiscussionLike(@PathVariable("discussionLikeID") UUID discussionLikeID) {
        this.discussionLikeService.deleteLike(discussionLikeID);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/private/discussion/{discussionID}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_MEMBER')")
    public ResponseEntity<Void> deleteDiscussionLikeByDiscussion(@PathVariable("discussionID") UUID discussionID) {
        this.discussionLikeService.deleteLikeByDiscussion(discussionID);
        return ResponseEntity.noContent().build();
    }
}

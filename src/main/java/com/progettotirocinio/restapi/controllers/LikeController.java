package com.progettotirocinio.restapi.controllers;

import com.progettotirocinio.restapi.data.dto.input.PaginationRequest;
import com.progettotirocinio.restapi.data.dto.output.LikeDto;
import com.progettotirocinio.restapi.data.entities.enums.LikeType;
import com.progettotirocinio.restapi.services.interfaces.LikeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.hibernate.query.Page;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
public class LikeController
{
    private final LikeService likeService;

    @GetMapping("/private")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<LikeDto>> getLikes(@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<LikeDto> likes = this.likeService.getLikes(paginationRequest.toPageRequest());
        return ResponseEntity.ok(likes);
    }

    @GetMapping("/private/{likeID}")
    @PreAuthorize("@permissionHandler.hasAccess(@likeDao,#likeID)")
    public ResponseEntity<LikeDto> getLike(@PathVariable("likeID") UUID likeID) {
        LikeDto likeDto = this.likeService.getLike(likeID);
        return ResponseEntity.ok(likeDto);
    }

    @GetMapping("/private/user/{userID}")
    @PreAuthorize("@permissionHandler.hasAccess(#userID)")
    public ResponseEntity<PagedModel<LikeDto>> getLikesByUser(@PathVariable("userID") UUID userID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<LikeDto> likes = this.likeService.getLikesByUser(userID,paginationRequest.toPageRequest());
        return ResponseEntity.ok(likes);
    }

    @GetMapping("/private/task/{taskID}")
    @PreAuthorize("@permissionHandler.hasAccess(@taskDao,#taskID)")
    public ResponseEntity<PagedModel<LikeDto>> getLikesByTask(@PathVariable("taskID") UUID taskID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<LikeDto> likes = this.likeService.getLikesByTask(taskID,paginationRequest.toPageRequest());
        return ResponseEntity.ok(likes);
    }

    @GetMapping("/private/poll/{pollID}")
    @PreAuthorize("@permissionHandler.hasAccess(@pollDao,#pollID)")
    public ResponseEntity<PagedModel<LikeDto>> getLikesByPoll(@PathVariable("pollID") UUID pollID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<LikeDto> likes = this.likeService.getLikesByPoll(pollID,paginationRequest.toPageRequest());
        return ResponseEntity.ok(likes);
    }

    @GetMapping("/private/comment/{commentID}")
    @PreAuthorize("@permissionHandler.hasAccess(@commentDao,#commentID)")
    public ResponseEntity<PagedModel<LikeDto>> getLikesByComment(@PathVariable("commentID") UUID commentID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<LikeDto> likes = this.likeService.getLikesByComment(commentID,paginationRequest.toPageRequest());
        return ResponseEntity.ok(likes);
    }

    @GetMapping("/private/discussion/{discussionID}")
    @PreAuthorize("@permissionHandler.hasAccess(@discussionDao,#discussionID)")
    public ResponseEntity<PagedModel<LikeDto>> getLikesByDiscussion(@PathVariable("discussionID") UUID discussionID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<LikeDto> likes = this.likeService.getLikesByDiscussion(discussionID,paginationRequest.toPageRequest());
        return ResponseEntity.ok(likes);
    }

    @GetMapping("/private/type/{type}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<LikeDto>> getLikesByType(@PathVariable("type")LikeType type,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<LikeDto> likes = this.likeService.getLikesByType(type,paginationRequest.toPageRequest());
        return ResponseEntity.ok(likes);
    }

    @GetMapping("/public/types")
    public ResponseEntity<CollectionModel<LikeType>> getTypes() {
        CollectionModel<LikeType> collectionModel = this.likeService.getLikeTypes();
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/private/user/{userID}/type/{type}")
    @PreAuthorize("@permissionHandler.hasAccess(#userID)")
    public ResponseEntity<PagedModel<LikeDto>> getLikesByUserAndType(@PathVariable("userID") UUID userID,@PathVariable("type") LikeType type,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<LikeDto> likes = this.likeService.getLikesByUserAndType(userID,type,paginationRequest.toPageRequest());
        return ResponseEntity.ok(likes);
    }

    @DeleteMapping("/private/{likeID}")
    @PreAuthorize("@permissionHandler.hasAccess(@likeDao,#likeID)")
    public ResponseEntity<Void> deleteLike(@PathVariable("likeID") UUID likeID) {
        this.likeService.deleteLike(likeID);
        return ResponseEntity.noContent().build();
    }
}

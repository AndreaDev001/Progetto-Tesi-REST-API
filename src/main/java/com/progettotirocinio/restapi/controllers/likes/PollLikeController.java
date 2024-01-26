package com.progettotirocinio.restapi.controllers.likes;

import com.progettotirocinio.restapi.data.dto.input.PaginationRequest;
import com.progettotirocinio.restapi.data.dto.output.likes.PollLikeDto;
import com.progettotirocinio.restapi.services.interfaces.likes.PollLikeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.repository.query.Param;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/pollLikes")
@RequiredArgsConstructor
public class PollLikeController
{
    private final PollLikeService pollLikeService;
    @GetMapping("/private")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<PollLikeDto>> getPollLikes(@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<PollLikeDto> pollLikes = this.pollLikeService.getPollLikes(paginationRequest.toPageRequest());
        return ResponseEntity.ok(pollLikes);
    }

    @GetMapping("/private/{pollLikeID}")
    @PreAuthorize("@permissionHandler.hasAccess(@pollLikeDao,#pollLikeID)")
    public ResponseEntity<PollLikeDto> getPollLike(@PathVariable("pollLikeID")UUID pollLikeID) {
        PollLikeDto pollLikeDto = this.pollLikeService.getPollLike(pollLikeID);
        return ResponseEntity.ok(pollLikeDto);
    }

    @GetMapping("/private/user/{userID}")
    @PreAuthorize("@permissionHandler.hasAccess(#userID)")
    public ResponseEntity<PagedModel<PollLikeDto>> getPollLikesByUser(@PathVariable("userID") UUID userID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<PollLikeDto> pollLikes = this.pollLikeService.getPollLikesByUser(userID,paginationRequest.toPageRequest());
        return ResponseEntity.ok(pollLikes);
    }

    @GetMapping("/private/user/{userID}/poll/{pollID}")
    @PreAuthorize("@permissionHandler.hasAccess(#userID)")
    public ResponseEntity<PollLikeDto> hasLike(@PathVariable("userID") UUID userID,@PathVariable("pollID") UUID pollID) {
        PollLikeDto pollLikeDto = this.pollLikeService.hasLike(userID,pollID);
        return ResponseEntity.ok(pollLikeDto);
    }

    @GetMapping("/private/poll/{pollLikeID}")
    @PreAuthorize("@permissionHandler.hasAccess(@pollDao,#pollLikeID)")
    public ResponseEntity<PagedModel<PollLikeDto>> getPollLikesByPoll(@PathVariable("pollID") UUID pollID, @ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<PollLikeDto> pollLikes = this.pollLikeService.getPollLikesByPoll(pollID,paginationRequest.toPageRequest());
        return ResponseEntity.ok(pollLikes);
    }

    @DeleteMapping("/private/{polLLikeID}")
    @PreAuthorize("@permissionHandler.hasAccess(@pollLikeDao,#pollLikeID)")
    public ResponseEntity<Void> deleteLike(@PathVariable("pollLikeID") UUID pollLikeID) {
        this.pollLikeService.deletePollLike(pollLikeID);
        return ResponseEntity.noContent().build();
    }
}

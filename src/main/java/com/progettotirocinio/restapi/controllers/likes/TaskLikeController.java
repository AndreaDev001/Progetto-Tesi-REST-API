package com.progettotirocinio.restapi.controllers.likes;

import com.progettotirocinio.restapi.data.dto.input.PaginationRequest;
import com.progettotirocinio.restapi.data.dto.output.likes.TaskLikeDto;
import com.progettotirocinio.restapi.data.entities.likes.TaskLike;
import com.progettotirocinio.restapi.services.interfaces.likes.TaskLikeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/taskLikes")
@RequiredArgsConstructor
public class TaskLikeController
{
    private final TaskLikeService taskLikeService;

    @GetMapping("/private")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<TaskLikeDto>> getTaskLikes(@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<TaskLikeDto> taskLikes = this.taskLikeService.getTaskLikes(paginationRequest.toPageRequest());
        return ResponseEntity.ok(taskLikes);
    }

    @GetMapping("/private/{taskLikeID}")
    @PreAuthorize("@permissionHandler.hasAccess(@taskLikeDao,#taskLikeID)")
    public ResponseEntity<TaskLikeDto> getTaskLike(@PathVariable("taskLikeID")UUID taskLikeID) {
        TaskLikeDto taskLikeDto = this.taskLikeService.getTaskLike(taskLikeID);
        return ResponseEntity.ok(taskLikeDto);
    }

    @GetMapping("/private/task/{taskID}")
    @PreAuthorize("@permissionHandler.hasAccess(@taskDao,#taskID)")
    public ResponseEntity<PagedModel<TaskLikeDto>> getTaskLikesByTask(@PathVariable("taskID") UUID taskID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<TaskLikeDto> taskLikes = this.taskLikeService.getTaskLikesByTask(taskID,paginationRequest.toPageRequest());
        return ResponseEntity.ok(taskLikes);
    }

    @GetMapping("/private/user/{userID}/task/{taskID}")
    @PreAuthorize("@permissionHandler.hasAccess(#userID)")
    public ResponseEntity<TaskLikeDto> hasLike(@PathVariable("userID") UUID userID,@PathVariable("taskID") UUID taskID) {
        TaskLikeDto taskLikeDto = this.taskLikeService.hasLike(userID,taskID);
        return ResponseEntity.ok(taskLikeDto);
    }

    @PostMapping("/private/{taskID}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_MEMBER')")
    public ResponseEntity<TaskLikeDto> createLike(@PathVariable("taskID") UUID taskID) {
        TaskLikeDto taskLikeDto = this.taskLikeService.createLike(taskID);
        return ResponseEntity.ok(taskLikeDto);
    }

    @GetMapping("/private/user/{userID}")
    @PreAuthorize("@permissionHandler.hasAccess(#userID)")
    public ResponseEntity<PagedModel<TaskLikeDto>> getTaskLikesByUser(@PathVariable("userID") UUID userID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<TaskLikeDto> taskLikes = this.taskLikeService.getTaskLikesByUser(userID,paginationRequest.toPageRequest());
        return ResponseEntity.ok(taskLikes);
    }

    @DeleteMapping("/private/{taskLikeID}")
    @PreAuthorize("@permissionHandler.hasAccess(@taskLikeDao,#taskLikeID)")
    public ResponseEntity<Void> deleteTaskLike(@PathVariable("taskLikeID") UUID taskLikeID) {
        this.taskLikeService.deleteLike(taskLikeID);
        return ResponseEntity.noContent().build();
    }
}

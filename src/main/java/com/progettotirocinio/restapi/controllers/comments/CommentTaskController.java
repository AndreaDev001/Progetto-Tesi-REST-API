package com.progettotirocinio.restapi.controllers.comments;

import com.progettotirocinio.restapi.data.dto.input.PaginationRequest;
import com.progettotirocinio.restapi.data.dto.input.create.CreateCommentDto;
import com.progettotirocinio.restapi.data.dto.output.comments.CommentDto;
import com.progettotirocinio.restapi.data.dto.output.comments.CommentTaskDto;
import com.progettotirocinio.restapi.data.entities.comments.CommentTask;
import com.progettotirocinio.restapi.services.interfaces.comments.CommentTaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/commentTasks")
@RequiredArgsConstructor
public class CommentTaskController
{
    private CommentTaskService commentTaskService;

    @GetMapping("/private")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<CommentTaskDto>> getComments(@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<CommentTaskDto> comments = this.commentTaskService.getComments(paginationRequest.toPageRequest());
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/private/{commentID}")
    @PreAuthorize("@permissionHandler.hasAccess(#commentID,@commentDao)")
    public ResponseEntity<CommentTaskDto> getComment(@PathVariable("commentID") UUID commentID) {
        CommentTaskDto commentTaskDto = this.commentTaskService.getCommentTaskByID(commentID);
        return ResponseEntity.ok(commentTaskDto);
    }

    @GetMapping("/private/task/{taskID}")
    @PreAuthorize("@permissionHandler.hasBoardRole('MEMBER',#taskID,@taskDao)")
    public ResponseEntity<CollectionModel<CommentTaskDto>> getCommentsByTask(@PathVariable("taskID") UUID taskID) {
        CollectionModel<CommentTaskDto> comments = this.commentTaskService.getCommentsByTask(taskID);
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/private/task/{taskID}/publisher/{publisherID}")
    @PreAuthorize("@permissionHandler.hasAccess(#publisherID) and @permissionHandler.hasBoardRole('MEMBER',#taskID,@taskDao)")
    public ResponseEntity<CommentTaskDto> getCommentByTaskAndPublisher(@PathVariable("taskID") UUID taskID,@PathVariable("publisherID") UUID publisherID) {
        CommentTaskDto commentTaskDto = this.commentTaskService.getCommentByTaskAndPublisher(taskID,publisherID);
        return ResponseEntity.ok(commentTaskDto);
    }

    @PostMapping("/private/{taskID}")
    @PreAuthorize("@permissionHandler.isAssigned(#taskID,@taskDao)")
    public ResponseEntity<CommentTaskDto> createCommentTask(@PathVariable("taskID") UUID taskID, @RequestBody @Valid CreateCommentDto createCommentDto) {
        CommentTaskDto commentTaskDto = this.commentTaskService.createTaskComment(taskID,createCommentDto);
        return ResponseEntity.status(201).body(commentTaskDto);
    }

    @DeleteMapping("/private/{commentID}")
    @PreAuthorize("@permissionHandler.hasAccess(#commentID,@commentDao)")
    public ResponseEntity<Void> deleteComment(@PathVariable("commentID") UUID commentID) {
        this.commentTaskService.deleteTaskComment(commentID);
        return ResponseEntity.noContent().build();
    }
}

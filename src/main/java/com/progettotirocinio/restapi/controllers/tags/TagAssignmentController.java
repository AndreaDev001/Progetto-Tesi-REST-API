package com.progettotirocinio.restapi.controllers.tags;


import com.progettotirocinio.restapi.data.dto.input.PaginationRequest;
import com.progettotirocinio.restapi.data.dto.input.create.tags.CreateTagAssignmentDto;
import com.progettotirocinio.restapi.data.dto.output.tags.TagAssignmentDto;
import com.progettotirocinio.restapi.data.dto.output.tags.TagDto;
import com.progettotirocinio.restapi.services.interfaces.tags.TagAssignmentService;
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
@RequestMapping("/tagAssignments")
@RequiredArgsConstructor
public class TagAssignmentController
{
    private final TagAssignmentService tagAssignmentService;

    @GetMapping("/private")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<TagAssignmentDto>> getTagAssignments(@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<TagAssignmentDto> pagedModel = this.tagAssignmentService.getTags(paginationRequest.toPageRequest());
        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/private/{tagAssignmentID}")
    @PreAuthorize("@permissionHandler.hasAccess(#tagAssignmentID,@tagAssignmentDao)")
    public ResponseEntity<TagAssignmentDto> getTagAssignment(@PathVariable("tagAssignmentID")UUID tagAssignmentID) {
        TagAssignmentDto tagAssignmentDto = this.tagAssignmentService.findTagAssignment(tagAssignmentID);
        return ResponseEntity.ok(tagAssignmentDto);
    }

    @GetMapping("/private/tag/{tagID}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<CollectionModel<TagAssignmentDto>> getTagAssignmentsByTag(@PathVariable("tagID") UUID tagID) {
        CollectionModel<TagAssignmentDto> collectionModel = this.tagAssignmentService.getTagAssignmentsByTag(tagID);
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/private/task/{taskID}")
    @PreAuthorize("@permissionHandler.hasBoardRole('MEMBER',#taskID,@taskDao)")
    public ResponseEntity<CollectionModel<TagAssignmentDto>> getTagAssignmentsByTask(@PathVariable("taskID") UUID taskID) {
        CollectionModel<TagAssignmentDto> collectionModel = this.tagAssignmentService.getTagAssignmentsByTask(taskID);
        return ResponseEntity.ok(collectionModel);
    }


    @PostMapping("/private")
    @PreAuthorize("@permissionHandler.hasBoardRole('ADMIN',#createTagAssignment.taskID,@taskDao)")
    public ResponseEntity<TagAssignmentDto> createTagAssignment(@RequestBody @Valid CreateTagAssignmentDto createTagAssignmentDto) {
        TagAssignmentDto tagAssignmentDto = this.tagAssignmentService.createTagAssignment(createTagAssignmentDto);
        return ResponseEntity.ok(tagAssignmentDto);
    }

    @DeleteMapping("/private/{taskAssignmentID}")
    @PreAuthorize("@permissionHandler.hasBoardRole('ADMIN',#taskAssignmentID,@taskAssignmentDao)")
    public ResponseEntity<Void> deleteTaskAssignment(@PathVariable("taskAssignmentID") UUID taskAssignmentID)
    {
        this.tagAssignmentService.deleteTagAssignment(taskAssignmentID);
        return ResponseEntity.noContent().build();
    }
}

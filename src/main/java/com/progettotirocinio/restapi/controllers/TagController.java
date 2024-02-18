package com.progettotirocinio.restapi.controllers;


import com.progettotirocinio.restapi.data.dto.input.PaginationRequest;
import com.progettotirocinio.restapi.data.dto.input.create.CreateTagDto;
import com.progettotirocinio.restapi.data.dto.input.update.UpdateTagDto;
import com.progettotirocinio.restapi.data.dto.output.BoardDto;
import com.progettotirocinio.restapi.data.dto.output.TagDto;
import com.progettotirocinio.restapi.services.interfaces.TagService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
@RequestMapping("/tags")
public class TagController
{
    private final TagService tagService;

    @GetMapping("/private")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<TagDto>> getTags(@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<TagDto> tags = this.tagService.getTags(paginationRequest.toPageRequest());
        return ResponseEntity.ok(tags);
    }

    @GetMapping("/private/{tagID}")
    @PreAuthorize("@permissionHandler.hasAccess(@tagDao,#tagID)")
    public ResponseEntity<TagDto> getTag(@PathVariable("tagID") UUID tagID) {
        TagDto tag = this.tagService.getTag(tagID);
        return ResponseEntity.ok(tag);
    }

    @PostMapping("/private")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_MEMBER')")
    public ResponseEntity<TagDto> createTag(@RequestBody @Valid CreateTagDto createTagDto) {
        TagDto tagDto = this.tagService.createTag(createTagDto);
        return ResponseEntity.status(201).body(tagDto);
    }


    @GetMapping("/private/task/{taskID}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_MEMBER')")
    public ResponseEntity<CollectionModel<TagDto>> getTagsByTask(@PathVariable("taskID") UUID taskID) {
        CollectionModel<TagDto> collectionModel = this.tagService.getTagsByTask(taskID);
        return ResponseEntity.ok(collectionModel);
    }

    @PutMapping("/private")
    @PreAuthorize("@permissionHandler.hasAccess(@tagDao,#updateTagDto.tagID)")
    public ResponseEntity<TagDto> updateTag(@RequestBody @Valid UpdateTagDto updateTagDto) {
        TagDto tagDto = this.tagService.updateTag(updateTagDto);
        return ResponseEntity.ok(tagDto);
    }

    @GetMapping("/private/publisher/{publisherID}")
    @PreAuthorize("@permissionHandler.hasAccess(#publisherID)")
    public ResponseEntity<PagedModel<TagDto>> getTagsByPublisher(@PathVariable("publisherID") UUID publisherID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<TagDto> tags = this.tagService.getTagsByPublisher(publisherID,paginationRequest.toPageRequest());
        return ResponseEntity.ok(tags);
    }

    @GetMapping("/private/name/{name}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_MEMBER')")
    public ResponseEntity<PagedModel<TagDto>> getTagsByName(@PathVariable("name") String name,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<TagDto> tags = this.tagService.getTagsByName(name,paginationRequest.toPageRequest());
        return ResponseEntity.ok(tags);
    }

    @DeleteMapping("/private/{tagID}")
    @PreAuthorize("@permissionHandler.hasAccess(@tagDao,#tagID)")
    public ResponseEntity<TagDto> deleteTag(@PathVariable("tagID") UUID tagID) {
        this.tagService.deleteTag(tagID);
        return ResponseEntity.noContent().build();
    }
}

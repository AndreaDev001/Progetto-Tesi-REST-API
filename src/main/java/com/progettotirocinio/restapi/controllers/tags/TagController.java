package com.progettotirocinio.restapi.controllers.tags;

import com.progettotirocinio.restapi.data.dto.input.PaginationRequest;
import com.progettotirocinio.restapi.data.dto.input.create.tags.CreateTagDto;
import com.progettotirocinio.restapi.data.dto.input.update.tags.UpdateTagDto;
import com.progettotirocinio.restapi.data.dto.output.tags.TagDto;
import com.progettotirocinio.restapi.services.interfaces.tags.TagService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
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
    @PreAuthorize("@permissionHandler.hasRole('ROLE_MEMBER')")
    public ResponseEntity<TagDto> getTagById(@PathVariable("tagID")UUID tagID) {
        TagDto tagDto = this.tagService.findTag(tagID);
        return ResponseEntity.ok(tagDto);
    }

    @GetMapping("/private/publisher/{publisherID}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_MEMBER')")
    public ResponseEntity<PagedModel<TagDto>> getTagsByPublisher(@PathVariable("publisherID") UUID publisherID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<TagDto> tags = this.tagService.getTagsByPublisher(publisherID,paginationRequest.toPageRequest());
        return ResponseEntity.ok(tags);
    }

    @GetMapping("/private/board/{boardID}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_MEMBER')")
    public ResponseEntity<CollectionModel<TagDto>> getTagsByBoard(@PathVariable("boardID") UUID boardID) {
        CollectionModel<TagDto> collectionModel = this.tagService.getTagsByBoard(boardID);
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/private/name/{name}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_MEMBER')")
    public ResponseEntity<PagedModel<TagDto>> getTagsByName(@PathVariable("name") String name,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<TagDto> tags = this.tagService.getTagsByName(name,paginationRequest.toPageRequest());
        return ResponseEntity.ok(tags);
    }

    @GetMapping("/private/color/{color}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_MEMBER')")
    public ResponseEntity<PagedModel<TagDto>> getTagsByColor(@PathVariable("color") String color,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<TagDto> tags = this.tagService.getTagsByColor(color,paginationRequest.toPageRequest());
        return ResponseEntity.ok(tags);
    }

    @PostMapping("/private")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_MEMBER')")
    public ResponseEntity<TagDto> createTag(@RequestBody @Valid CreateTagDto createTagDto) {
        TagDto tagDto = this.tagService.createTag(createTagDto);
        return ResponseEntity.status(201).body(tagDto);
    }

    @PutMapping("/private")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_MEMBER')")
    public ResponseEntity<TagDto> updateTag(@RequestBody @Valid UpdateTagDto updateTagDto) {
        TagDto tagDto = this.tagService.updateTag(updateTagDto);
        return ResponseEntity.ok(tagDto);
    }

    @DeleteMapping("/private/{tagID}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_MEMBER')")
    public ResponseEntity<Void> deleteTag(@PathVariable("tagID") UUID tagID) {
        this.tagService.deleteTag(tagID);
        return ResponseEntity.noContent().build();
    }
}

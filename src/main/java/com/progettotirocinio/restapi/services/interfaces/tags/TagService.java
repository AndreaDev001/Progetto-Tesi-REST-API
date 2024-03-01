package com.progettotirocinio.restapi.services.interfaces.tags;

import com.progettotirocinio.restapi.data.dto.input.create.tags.CreateTagDto;
import com.progettotirocinio.restapi.data.dto.input.update.tags.UpdateTagDto;
import com.progettotirocinio.restapi.data.dto.output.tags.TagDto;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;

import java.util.UUID;

public interface TagService
{
    PagedModel<TagDto> getTags(Pageable pageable);
    PagedModel<TagDto> getTagsByPublisher(UUID publisherID,Pageable pageable);
    PagedModel<TagDto> getTagsByName(String name,Pageable pageable);
    PagedModel<TagDto> getTagsByColor(String color,Pageable pageable);
    CollectionModel<TagDto> getTagsByBoard(UUID boardID);
    TagDto findTag(UUID tagID);
    TagDto createTag(CreateTagDto createTagDto);
    TagDto updateTag(UpdateTagDto updateTagDto);
    void deleteTag(UUID tagID);
}

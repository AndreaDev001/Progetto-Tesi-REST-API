package com.progettotirocinio.restapi.services.interfaces;

import com.progettotirocinio.restapi.data.dto.input.create.CreateTagDto;
import com.progettotirocinio.restapi.data.dto.output.TagDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import java.util.UUID;

public interface TagService {
    PagedModel<TagDto> getTags(Pageable pageable);
    PagedModel<TagDto> getTagsByPublisher(UUID tagID,Pageable pageable);
    PagedModel<TagDto> getTagsByBoard(UUID boardID,Pageable pageable);
    PagedModel<TagDto> getTagsByName(String name,Pageable pageable);
    TagDto getTag(UUID tagID);
    TagDto createTag(CreateTagDto createTagDto);
    void deleteTag(UUID tagID);
}

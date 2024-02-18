package com.progettotirocinio.restapi.services.interfaces;

import com.progettotirocinio.restapi.data.dto.input.create.CreateTagDto;
import com.progettotirocinio.restapi.data.dto.input.update.UpdateTagDto;
import com.progettotirocinio.restapi.data.dto.output.TagDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;

import java.util.UUID;

public interface TagService {
    PagedModel<TagDto> getTags(Pageable pageable);
    PagedModel<TagDto> getTagsByPublisher(UUID tagID,Pageable pageable);
    CollectionModel<TagDto> getTagsByTask(UUID taskID);
    PagedModel<TagDto> getTagsByName(String name,Pageable pageable);
    TagDto getTag(UUID tagID);
    TagDto createTag(CreateTagDto createTagDto);
    TagDto updateTag(UpdateTagDto updateTagDto);
    void deleteTag(UUID tagID);
}

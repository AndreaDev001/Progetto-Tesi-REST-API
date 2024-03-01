package com.progettotirocinio.restapi.services.interfaces.tags;

import com.progettotirocinio.restapi.data.dto.input.create.tags.CreateTagAssignmentDto;
import com.progettotirocinio.restapi.data.dto.output.tags.TagAssignmentDto;
import com.progettotirocinio.restapi.data.entities.tags.TagAssignment;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;

import java.util.UUID;

public interface TagAssignmentService
{
    PagedModel<TagAssignmentDto> getTags(Pageable pageable);
    CollectionModel<TagAssignmentDto> getTagAssignmentsByTask(UUID taskID);
    CollectionModel<TagAssignmentDto> getTagAssignmentsByTag(UUID tagID);
    TagAssignmentDto findTagAssignment(UUID tagAssignmentID);
    TagAssignmentDto createTagAssignment(CreateTagAssignmentDto createTagAssignmentDto);
    void deleteTagAssignment(UUID tagAssignmentID);
}

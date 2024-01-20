package com.progettotirocinio.restapi.services.interfaces;

import com.progettotirocinio.restapi.data.dto.input.create.CreateDiscussionDto;
import com.progettotirocinio.restapi.data.dto.input.update.UpdateDiscussionDto;
import com.progettotirocinio.restapi.data.dto.output.DiscussionDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import java.util.UUID;

public interface DiscussionService {
    PagedModel<DiscussionDto> getDiscussion(Pageable pageable);
    PagedModel<DiscussionDto> getDiscussionsByPublisher(UUID publisherID,Pageable pageable);
    PagedModel<DiscussionDto> getDiscussionsByTopic(String topic,Pageable pageable);
    PagedModel<DiscussionDto> getDiscussionsByTitle(String title,Pageable pageable);
    DiscussionDto getDiscussion(UUID discussionID);
    DiscussionDto createDiscussion(CreateDiscussionDto createDiscussionDto);
    DiscussionDto updateDiscussion(UpdateDiscussionDto updateDiscussionDto);
    void deleteDiscussion(UUID discussionID);
}

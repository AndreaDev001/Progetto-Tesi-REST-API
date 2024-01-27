package com.progettotirocinio.restapi.services.interfaces.likes;

import com.progettotirocinio.restapi.data.dto.output.likes.DiscussionLikeDto;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import java.util.UUID;

public interface DiscussionLikeService
{
    PagedModel<DiscussionLikeDto> getDiscussionLikes(Pageable pageable);
    PagedModel<DiscussionLikeDto> getDiscussionLikesByUser(UUID userID,Pageable pageable);
    PagedModel<DiscussionLikeDto> getDiscussionLikesByDiscussion(UUID discussionID,Pageable pageable);
    DiscussionLikeDto getDiscussionLike(UUID discussionLikeID);
    DiscussionLikeDto hasLike(UUID userID,UUID discussionID);
    DiscussionLikeDto createLike(UUID discussionID);
    void deleteLike(UUID discussionID);
}

package com.progettotirocinio.restapi.services.interfaces;

import com.progettotirocinio.restapi.data.dto.output.LikeDto;
import com.progettotirocinio.restapi.data.entities.enums.LikeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;

import java.util.UUID;

public interface LikeService
{
    PagedModel<LikeDto> getLikes(Pageable pageable);
    PagedModel<LikeDto> getLikesByType(LikeType type,Pageable pageable);
    PagedModel<LikeDto> getLikesByUserAndType(UUID userID,LikeType type,Pageable pageable);
    PagedModel<LikeDto> getLikesByUser(UUID userID,Pageable pageable);
    PagedModel<LikeDto> getLikesByTask(UUID taskID,Pageable pageable);
    PagedModel<LikeDto> getLikesByDiscussion(UUID discussionID,Pageable pageable);
    PagedModel<LikeDto> getLikesByComment(UUID commentID,Pageable pageable);
    PagedModel<LikeDto> getLikesByPoll(UUID pollID,Pageable pageable);
    LikeDto getLike(UUID likeID);
    void deleteLike(UUID likeID);
    CollectionModel<LikeType> getLikeTypes();
}

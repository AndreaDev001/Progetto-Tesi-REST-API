package com.progettotirocinio.restapi.services.interfaces.likes;

import com.progettotirocinio.restapi.data.dto.output.likes.TaskLikeDto;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import java.util.Optional;
import java.util.UUID;

public interface TaskLikeService
{
    PagedModel<TaskLikeDto> getTaskLikes(Pageable pageable);
    PagedModel<TaskLikeDto> getTaskLikesByUser(UUID userID, Pageable pageable);
    PagedModel<TaskLikeDto> getTaskLikesByTask(UUID taskID,Pageable pageable);
    TaskLikeDto getTaskLike(UUID taskLikeID);
    TaskLikeDto hasLike(UUID userID,UUID taskID);
    TaskLikeDto createLike(UUID taskID);
    void deleteLike(UUID taskLikeID);
}

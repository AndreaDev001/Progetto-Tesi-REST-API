package com.progettotirocinio.restapi.services.interfaces.likes;

import com.progettotirocinio.restapi.data.dto.output.likes.PollLikeDto;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import java.util.UUID;

public interface PollLikeService
{
    PagedModel<PollLikeDto> getPollLikes(Pageable pageable);
    PagedModel<PollLikeDto> getPollLikesByUser(UUID userID, Pageable pageable);
    PagedModel<PollLikeDto> getPollLikesByPoll(UUID pollID,Pageable pageable);
    PollLikeDto getPollLike(UUID pollLikeID);
    PollLikeDto hasLike(UUID userID,UUID pollID);
    void deletePollLike(UUID pollLikeID);
}

package com.progettotirocinio.restapi.data.dao.likes;

import com.progettotirocinio.restapi.data.entities.Poll;
import com.progettotirocinio.restapi.data.entities.likes.PollLike;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PollLikeDao extends JpaRepository<PollLike, UUID>
{
    @Query("select p from PollLike p where p.user.id = :requiredID")
    Page<PollLike> getPollLikesByUser(@Param("requiredID") UUID userID, Pageable pageable);
    @Query("select p from PollLike p where p.poll.id = :requiredPollID")
    Page<PollLike> getPollLikesByPoll(@Param("requiredPollID") UUID pollID,Pageable pageable);
    @Query("select p from PollLike p where p.user.id = :requiredUserID and p.poll.id = :requiredPollID")
    Optional<PollLike> hasLike(@Param("requiredUserID") UUID userID,@Param("requiredPollID") UUID pollID);
}

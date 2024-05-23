package com.progettotirocinio.restapi.data.dao;

import com.progettotirocinio.restapi.data.entities.polls.PollVote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

@Repository
public interface PollVoteDao extends JpaRepository<PollVote, UUID>
{
    @Query("select p from PollVote p where p.user.id = :requiredID")
    Page<PollVote> getPollVotesByUser(@Param("requiredID") UUID userID, Pageable pageable);
    @Query("select p from PollVote p where p.option.id = :requiredID")
    Page<PollVote> getPollVotesByOption(@Param("requiredID") UUID optionID,Pageable pageable);
    @Query("select p from PollVote p where p.user.id = :requiredUserID and p.option.id = :requiredOptionID")
    Optional<PollVote> getPollVote(@Param("requiredUserID") UUID userID, @Param("requiredOptionID") UUID optionID);
    @Query("select p from PollVote p where p.option.poll.id = :requiredID")
    List<PollVote> getPollVotesByPoll(@Param("requiredID") UUID pollID);
    @Query("select p from PollVote p where p.option.poll.id = :requiredPollID")
    Optional<PollVote> getCurrentVote(@Param("requiredPollID") UUID pollID);
}

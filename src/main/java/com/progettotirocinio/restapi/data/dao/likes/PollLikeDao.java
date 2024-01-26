package com.progettotirocinio.restapi.data.dao.likes;

import com.progettotirocinio.restapi.data.entities.Poll;
import com.progettotirocinio.restapi.data.entities.likes.PollLike;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PollLikeDao extends JpaRepository<PollLike, UUID>
{
    @Query("select l from PollLike l where l.poll.id = :requiredID")
    Page<PollLike> getPollLikes(@Param("requiredID") UUID pollID, Pageable pageable);
}

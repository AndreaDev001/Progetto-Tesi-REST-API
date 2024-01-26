package com.progettotirocinio.restapi.data.dao.likes;

import com.progettotirocinio.restapi.data.entities.likes.DiscussionLike;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DiscussionLikeDao extends JpaRepository<DiscussionLike, UUID>
{
    @Query("select l from DiscussionLike l where l.discussion.id = :requiredID")
    Page<DiscussionLike> getDiscussionLikes(@Param("requiredID") UUID discussionID, Pageable pageable);
}

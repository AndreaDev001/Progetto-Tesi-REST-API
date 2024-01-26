package com.progettotirocinio.restapi.data.dao.likes;

import com.progettotirocinio.restapi.data.entities.likes.DiscussionLike;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DiscussionLikeDao extends JpaRepository<DiscussionLike, UUID>
{
    @Query("select d from DiscussionLike d where d.user.id = :requiredID")
    Page<DiscussionLike> getDiscussionLikesByUser(@Param("requiredID") UUID userID, Pageable pageable);
    @Query("select d from DiscussionLike d where d.discussion.id = :requiredID")
    Page<DiscussionLike> getDiscussionLikesByDiscussion(@Param("requiredID") UUID discussionID,Pageable pageable);
    @Query("select d from DiscussionLike d where d.user.id = :requiredUserID and d.discussion.id = :requiredDiscussionID")
    Optional<DiscussionLike> hasLike(@Param("requiredUserID") UUID userID, @Param("requiredDiscussionID") UUID discussionID);
}

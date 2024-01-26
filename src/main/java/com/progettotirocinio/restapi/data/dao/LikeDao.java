package com.progettotirocinio.restapi.data.dao;

import com.progettotirocinio.restapi.data.entities.BoardMember;
import com.progettotirocinio.restapi.data.entities.Like;
import com.progettotirocinio.restapi.data.entities.Poll;
import com.progettotirocinio.restapi.data.entities.enums.LikeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LikeDao extends JpaRepository<Like, UUID> {
    @Query("select l from Like l where l.user.id = :requiredID")
    Page<Like> getLikesByUser(@Param("requiredID") UUID userID, Pageable pageable);
    @Query("select l from Like l where l.user.id = :requiredID and l.type = :requiredType")
    Page<Like> getLikesByUserAndType(@Param("requiredID") UUID userID, @Param("requiredType") LikeType likeType, Pageable pageable);
    @Query("select l from Like l join Task t where t.id = :requiredID")
    Page<Like> getLikesByTask(@Param("requiredID") UUID taskID,Pageable pageable);
    @Query("select l from Like l join Comment c where c.id = :requiredID")
    Page<Like> getLikesByComment(@Param("requiredID") UUID commentID,Pageable pageable);
    @Query("select l from Like l join Discussion d where d.id = :requiredID")
    Page<Like> getLikesByDiscussion(@Param("requiredID") UUID discussionID,Pageable pageable);
    @Query("select l from Like l join Poll p where p.id = :requiredID")
    Page<Like> getLikesByPoll(@Param("requiredID") UUID pollID,Pageable pageable);
    @Query("select l from Like l where l.type = :requiredType")
    Page<Like> getLikesByType(@Param("requiredType") LikeType likeType, Pageable pageable);
}
package com.progettotirocinio.restapi.data.dao.likes;

import com.progettotirocinio.restapi.data.entities.enums.LikeType;
import com.progettotirocinio.restapi.data.entities.likes.Like;
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
    Page<Like> getLikesByUserAndType(@Param("requiredID") UUID userID, @Param("requiredType")LikeType likeType,Pageable pageable);
    @Query("select l from Like l where l.type = :requiredType")
    Page<Like> getLikesByType(@Param("requiredType") LikeType likeType,Pageable pageable);
}

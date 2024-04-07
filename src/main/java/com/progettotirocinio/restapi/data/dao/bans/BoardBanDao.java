package com.progettotirocinio.restapi.data.dao.bans;

import com.progettotirocinio.restapi.data.entities.bans.BoardBan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BoardBanDao extends JpaRepository<BoardBan, UUID>
{
    @Query("select b from BoardBan b where b.banner.id = :requiredID")
    Page<BoardBan> getBoardBansByBanner(@Param("requiredID") UUID bannerID, Pageable pageable);
    @Query("select b from BoardBan b where b.banned.id = :requiredID")
    Page<BoardBan> getBoardBansByBanned(@Param("requiredID") UUID bannedID,Pageable pageable);
    @Query("select b from BoardBan b where b.board.id = :requiredID")
    Page<BoardBan> getBoardBansByBoard(@Param("requiredID") UUID boardID,Pageable pageable);
}

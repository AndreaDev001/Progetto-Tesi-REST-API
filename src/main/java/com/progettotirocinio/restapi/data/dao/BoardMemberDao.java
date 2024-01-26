package com.progettotirocinio.restapi.data.dao;

import com.progettotirocinio.restapi.data.entities.BoardMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BoardMemberDao extends JpaRepository<BoardMember, UUID> {
    @Query("select b from BoardMember b where b.user.id = :requiredID")
    Page<BoardMember> getBoardMembers(@Param("requiredID") UUID userID, Pageable pageable);
    @Query("select b from BoardMember b where b.board.id = :requiredID")
    Page<BoardMember> getBoardMembersByBoard(@Param("requiredID") UUID boardID,Pageable pageable);
    @Query("select b from BoardMember b where b.board.id = :requiredBoardID and b.user.id = :requiredUserID")
    Optional<BoardMember> getBoardMember(@Param("requiredBoardID") UUID boardID, @Param("requiredUserID") UUID userID);
}

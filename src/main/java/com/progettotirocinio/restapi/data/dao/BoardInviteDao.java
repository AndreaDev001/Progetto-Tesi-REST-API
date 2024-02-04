package com.progettotirocinio.restapi.data.dao;


import com.progettotirocinio.restapi.data.dto.output.BoardDto;
import com.progettotirocinio.restapi.data.entities.Board;
import com.progettotirocinio.restapi.data.entities.BoardInvite;
import com.progettotirocinio.restapi.data.entities.enums.BoardInviteStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BoardInviteDao extends JpaRepository<BoardInvite,UUID>
{
    @Query("select b from BoardInvite b where b.publisher.id = :publisherID")
    Page<BoardInvite> getBoardInvitesByPublisher(@Param("publisherID") UUID publisherID,Pageable pageable);
    @Query("select b from BoardInvite b where b.receiver.id = :requiredID")
    Page<BoardInvite> getBoardInvitesByReceiver(@Param("requiredID") UUID receiverID,Pageable pageable);
    @Query("select b from BoardInvite b where b.board.id =: boardID")
    Page<BoardInvite> getBoardInvitesByBoard(@Param("boardID") UUID boardID, Pageable pageable);

    @Query("select b from BoardInvite b where b.status = :requiredStatus")
    Page<BoardInvite> getBoardInvitesByStatus(@Param("requiredStatus")BoardInviteStatus status,Pageable pageable);
}

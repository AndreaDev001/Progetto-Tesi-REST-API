package com.progettotirocinio.restapi.services.interfaces;

import com.progettotirocinio.restapi.data.dto.output.BoardInviteDto;
import com.progettotirocinio.restapi.data.entities.BoardInvite;
import com.progettotirocinio.restapi.data.entities.enums.BoardInviteStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import java.util.UUID;

public interface BoardInviteService
{
    PagedModel<BoardInviteDto> getBoardInvites(Pageable pageable);
    PagedModel<BoardInviteDto> getBoardInvitesByPublisher(UUID publisherID,Pageable pageable);
    PagedModel<BoardInviteDto> getBoardInvitesByBoard(UUID boardID, Pageable pageable);
    PagedModel<BoardInviteDto> getBoardInvitesByStatus(BoardInviteStatus status,Pageable pageable);
    BoardInviteDto getBoardInvite(UUID inviteID);
    void deleteBoardInvite(UUID inviteID);
}

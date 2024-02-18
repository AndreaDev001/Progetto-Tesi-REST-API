package com.progettotirocinio.restapi.services.interfaces;

import com.progettotirocinio.restapi.data.dto.output.BoardMemberDto;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;

import java.util.UUID;

public interface BoardMemberService
{
    PagedModel<BoardMemberDto> getBoardMembers(Pageable pageable);
    PagedModel<BoardMemberDto> getBoardMembersByUser(UUID userID, Pageable pageable);
    CollectionModel<BoardMemberDto> getBoardMembersByBoard(UUID boardID);
    BoardMemberDto getBoardMember(UUID boardMemberID);
    BoardMemberDto isMember(UUID userID,UUID boardID);
    BoardMemberDto createMember(UUID boardID);
    void deleteMember(UUID boardMemberID);
}

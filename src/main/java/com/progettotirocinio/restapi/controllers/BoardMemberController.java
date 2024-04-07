package com.progettotirocinio.restapi.controllers;

import com.progettotirocinio.restapi.data.dao.BoardMemberDao;
import com.progettotirocinio.restapi.data.dto.input.PaginationRequest;
import com.progettotirocinio.restapi.data.dto.input.create.CreateBoardDto;
import com.progettotirocinio.restapi.data.dto.output.BoardMemberDto;
import com.progettotirocinio.restapi.services.interfaces.BoardMemberService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/boardMembers")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
public class BoardMemberController
{
    private final BoardMemberService boardMemberService;

    @GetMapping("/private")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<BoardMemberDto>> getBoardMembers(@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<BoardMemberDto> boardMembers = this.boardMemberService.getBoardMembers(paginationRequest.toPageRequest());
        return ResponseEntity.ok(boardMembers);
    }

    @GetMapping("/private/{boardMemberID}")
    @PreAuthorize("@permissionHandler.hasAccess(@boardMemberDao,#boardMemberID)")
    public ResponseEntity<BoardMemberDto> getBoardMember(@PathVariable("boardMemberID") UUID boardMemberID) {
        BoardMemberDto boardMemberDto = this.boardMemberService.getBoardMember(boardMemberID);
        return ResponseEntity.ok(boardMemberDto);
    }

    @GetMapping("/private/possible/task/board/{boardID}/task/{taskID}")
    @PreAuthorize("@permissionHandler.hasBoardRole('ADMIN',#boardID)")
    public ResponseEntity<CollectionModel<BoardMemberDto>> getPossibleTaskMembers(@PathVariable("boardID") UUID boardID,@PathVariable("taskID") UUID taskID) {
        CollectionModel<BoardMemberDto> collectionModel = this.boardMemberService.getPossibleTaskMembers(boardID,taskID);
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/private/possible/team/board/{boardID}/team/{teamID}")
    @PreAuthorize("@permissionHandler.hasBoardRole('ADMIN',#boardID)")
    public ResponseEntity<CollectionModel<BoardMemberDto>> getPossibleTeamMembers(@PathVariable("boardID") UUID boardID,@PathVariable("teamID") UUID teamID) {
        CollectionModel<BoardMemberDto> collectionModel = this.boardMemberService.getPossibleTeamMembers(boardID,teamID);
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/private/user/{userID}")
    @PreAuthorize("@permissionHandler.hasAccess(#userID)")
    public ResponseEntity<PagedModel<BoardMemberDto>> getBoardMembersByUser(@PathVariable("userID") UUID userID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<BoardMemberDto> boardMembers = this.boardMemberService.getBoardMembersByUser(userID,paginationRequest.toPageRequest());
        return ResponseEntity.ok(boardMembers);
    }

    @GetMapping("/private/board/{boardID}")
    @PreAuthorize("@permissionHandler.isMember(#boardID)")
    public ResponseEntity<CollectionModel<BoardMemberDto>> getBoardMembersByBoard(@PathVariable("boardID") UUID boardID) {
        CollectionModel<BoardMemberDto> collectionModel = this.boardMemberService.getBoardMembersByBoard(boardID);
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/private/user/{userID}/board/{boardID}")
    @PreAuthorize("@permissionHandler.hasAccess(#userID) and @permissionHandler.isMember(#boardID)")
    public ResponseEntity<BoardMemberDto> isMember(@PathVariable("userID") UUID userID,@PathVariable("boardID") UUID boardID) {
        BoardMemberDto boardMemberDto = this.boardMemberService.isMember(userID,boardID);
        return ResponseEntity.ok(boardMemberDto);
    }

    @DeleteMapping("/private/{boardMemberID}")
    @PreAuthorize("@permissionHandler.hasBoardRole('ADMIN',#boardMemberID,@boardMemberDao)")
    public ResponseEntity<Void> deleteBoardMember(@PathVariable("boardMemberID") UUID boardMemberID) {
        this.boardMemberService.deleteMember(boardMemberID);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/private/board/{boardID}/user/{userID}")
    @PreAuthorize("@permissionHandler.hasBoardRole('ADMIN',#boardID)")
    public ResponseEntity<Void> deleteBoardMemberFromBoard(@PathVariable("boardID") UUID boardID,@PathVariable("memberID") UUID memberID) {
        this.boardMemberService.deleteMemberFromBoard(boardID,memberID);
        return ResponseEntity.noContent().build();
    }
}

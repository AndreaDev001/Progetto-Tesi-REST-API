package com.progettotirocinio.restapi.controllers;


import com.progettotirocinio.restapi.data.dao.BoardInviteDao;
import com.progettotirocinio.restapi.data.dto.input.PaginationRequest;
import com.progettotirocinio.restapi.data.dto.input.create.CreateBoardInviteDto;
import com.progettotirocinio.restapi.data.dto.output.BoardInviteDto;
import com.progettotirocinio.restapi.data.entities.BoardInvite;
import com.progettotirocinio.restapi.data.entities.enums.BoardInviteStatus;
import com.progettotirocinio.restapi.services.interfaces.BoardInviteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boardInvites")
public class BoardInviteController
{
    private final BoardInviteService boardInviteService;

    @GetMapping("/private")
    public ResponseEntity<PagedModel<BoardInviteDto>> getBoardInvites(@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<BoardInviteDto> boardInvites = this.boardInviteService.getBoardInvites(paginationRequest.toPageRequest());
        return ResponseEntity.ok(boardInvites);
    }

    @GetMapping("/private/{boardInviteID}")
    public ResponseEntity<BoardInviteDto> getBoardInvite(@PathVariable("boardInviteID") UUID boardInviteID, @ParameterObject @Valid PaginationRequest paginationRequest) {
        BoardInviteDto boardInvite = this.boardInviteService.getBoardInvite(boardInviteID);
        return ResponseEntity.ok(boardInvite);
    }

    @PostMapping("/private")
    public ResponseEntity<BoardInviteDto> createBoardInvite(@RequestBody @Valid CreateBoardInviteDto createBoardInviteDto) {
        BoardInviteDto boardInviteDto = this.boardInviteService.createBoardInvite(createBoardInviteDto);
        return ResponseEntity.status(201).body(boardInviteDto);
    }

    @GetMapping("/private/publisher/{publisherID}")
    public ResponseEntity<PagedModel<BoardInviteDto>> getBoardInvitesByPublisher(@PathVariable("publisherID") UUID publisherID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<BoardInviteDto> boardInvites = this.boardInviteService.getBoardInvitesByPublisher(publisherID,paginationRequest.toPageRequest());
        return ResponseEntity.ok(boardInvites);
    }

    @GetMapping("/private/board/{boardID}")
    public ResponseEntity<PagedModel<BoardInviteDto>> getBoardInvitesByBoard(@PathVariable("boardID") UUID boardID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<BoardInviteDto> boardInvites = this.boardInviteService.getBoardInvitesByBoard(boardID,paginationRequest.toPageRequest());
        return ResponseEntity.ok(boardInvites);
    }

    @GetMapping("/private/status/{status}")
    public ResponseEntity<PagedModel<BoardInviteDto>> getBoardInvitesByStatus(@PathVariable("status")BoardInviteStatus status,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<BoardInviteDto> boardInvites = this.boardInviteService.getBoardInvitesByStatus(status,paginationRequest.toPageRequest());
        return ResponseEntity.ok(boardInvites);
    }

    @DeleteMapping("/private/{boardInviteID}")
    public ResponseEntity<Void> deleteBoardInvite(@PathVariable("boardInviteID") UUID boardInviteID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        this.boardInviteService.deleteBoardInvite(boardInviteID);
        return ResponseEntity.noContent().build();
    }

}

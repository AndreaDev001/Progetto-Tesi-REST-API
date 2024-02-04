package com.progettotirocinio.restapi.controllers;


import com.progettotirocinio.restapi.data.dao.specifications.BoardSpecifications;
import com.progettotirocinio.restapi.data.dto.input.PaginationRequest;
import com.progettotirocinio.restapi.data.dto.input.create.CreateBoardDto;
import com.progettotirocinio.restapi.data.dto.input.update.UpdateBoardDto;
import com.progettotirocinio.restapi.data.dto.output.BoardDto;
import com.progettotirocinio.restapi.data.entities.Board;
import com.progettotirocinio.restapi.data.entities.enums.BoardVisibility;
import com.progettotirocinio.restapi.services.interfaces.BoardService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
public class BoardController
{
    private final BoardService boardService;

    @GetMapping("/private")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<BoardDto>> getBoards(@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<BoardDto> boards = this.boardService.getBoards(paginationRequest.toPageRequest());
        return ResponseEntity.ok(boards);
    }

    @GetMapping("/private/{boardID}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<BoardDto> getBoard(@PathVariable("boardID") UUID boardID) {
        BoardDto board = this.boardService.getBoard(boardID);
        return ResponseEntity.ok(board);
    }

    @GetMapping("/private/publisher/{publisherID}")
    @PreAuthorize("@permissionHandler.hasAccess(#publisherID)")
    public ResponseEntity<PagedModel<BoardDto>> getBoardsByPublisher(@PathVariable("publisherID") UUID publisherID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<BoardDto> boards = this.boardService.getBoardsByPublisher(publisherID,paginationRequest.toPageRequest());
        return ResponseEntity.ok(boards);
    }

    @GetMapping("/public/spec")
    public ResponseEntity<PagedModel<BoardDto>> getBoardsBySpec(@ParameterObject @Valid BoardSpecifications.Filter filter,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<BoardDto> boards = this.boardService.getBoardsBySpec(BoardSpecifications.withFilter(filter),paginationRequest.toPageRequest());
        return ResponseEntity.ok(boards);
    }

    @GetMapping("/private/similar/{boardID}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_MEMBER')")
    public ResponseEntity<PagedModel<BoardDto>> getSimilarBoards(@PathVariable("boardID") UUID boardID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<BoardDto> boards = this.boardService.getSimilarBoards(boardID,paginationRequest.toPageRequest());
        return ResponseEntity.ok(boards);
    }

    @GetMapping("/public/visibilities")
    public ResponseEntity<CollectionModel<BoardVisibility>> getVisibilities() {
        CollectionModel<BoardVisibility> visibilities = this.boardService.getVisibilities();
        return ResponseEntity.ok(visibilities);
    }

    @PostMapping("/private")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_MEMBER')")
    public ResponseEntity<BoardDto> createBoard(@RequestBody @Valid CreateBoardDto createBoardDto) {
        BoardDto boardDto = this.boardService.createBoard(createBoardDto);
        return ResponseEntity.status(201).body(boardDto);
    }

    @GetMapping("/public/orderTypes")
    public ResponseEntity<CollectionModel<String>> getOrderTypes() {
        CollectionModel<String> orderTypes = this.boardService.getOrderTypes();
        return ResponseEntity.ok(orderTypes);
    }

    @PutMapping("/private")
    @PreAuthorize("@permissionHandler.hasAccess(@boardDao,#updateBoardDto.boardID)")
    public ResponseEntity<BoardDto> updateBoard(@RequestBody @Valid UpdateBoardDto updateBoardDto) {
        BoardDto boardDto = this.boardService.updateBoard(updateBoardDto);
        return ResponseEntity.ok(boardDto);
    }

    @GetMapping("/private/title/{title}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_MEMBER')")
    public ResponseEntity<PagedModel<BoardDto>> getBoardsByTitle(@PathVariable("title") String title,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<BoardDto> boards = this.boardService.getBoardsByTile(title,paginationRequest.toPageRequest());
        return ResponseEntity.ok(boards);
    }

    @GetMapping("/private/description/{description}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_MEMBER')")
    public ResponseEntity<PagedModel<BoardDto>> getBoardsByDescription(@PathVariable("description") String description,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<BoardDto> boards = this.boardService.getBoardsByDescription(description,paginationRequest.toPageRequest());
        return ResponseEntity.ok(boards);
    }

    @DeleteMapping("/private/{boardID}")
    @PreAuthorize("@permissionHandler.hasAccess(@boardDao,#boardID)")
    public ResponseEntity<BoardDto> deleteBoard(@PathVariable("boardID") UUID boardID) {
        this.boardService.deleteBoard(boardID);
        return ResponseEntity.noContent().build();
    }
}

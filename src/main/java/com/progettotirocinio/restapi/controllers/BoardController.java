package com.progettotirocinio.restapi.controllers;


import com.progettotirocinio.restapi.data.dao.specifications.BoardSpecifications;
import com.progettotirocinio.restapi.data.dto.input.PaginationRequest;
import com.progettotirocinio.restapi.data.dto.output.BoardDto;
import com.progettotirocinio.restapi.data.entities.Board;
import com.progettotirocinio.restapi.services.interfaces.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController
{
    private final BoardService boardService;

    @GetMapping("/private")
    public ResponseEntity<PagedModel<BoardDto>> getBoards(@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<BoardDto> boards = this.boardService.getBoards(paginationRequest.toPageRequest());
        return ResponseEntity.ok(boards);
    }

    @GetMapping("/private/{boardID}")
    public ResponseEntity<BoardDto> getBoard(@PathVariable("boardID") UUID boardID) {
        BoardDto board = this.boardService.getBoard(boardID);
        return ResponseEntity.ok(board);
    }

    @GetMapping("/private/publisher/{publisherID}")
    public ResponseEntity<PagedModel<BoardDto>> getBoardsByPublisher(@PathVariable("publisherID") UUID publisherID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<BoardDto> boards = this.boardService.getBoardsByPublisher(publisherID,paginationRequest.toPageRequest());
        return ResponseEntity.ok(boards);
    }

    @GetMapping("/private/spec")
    public ResponseEntity<PagedModel<BoardDto>> getBoardsBySpec(@ParameterObject @Valid BoardSpecifications.Filter filter,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<BoardDto> boards = this.boardService.getBoardsBySpec(BoardSpecifications.withFilter(filter),paginationRequest.toPageRequest());
        return ResponseEntity.ok(boards);
    }

    @GetMapping("/private/similar/{boardID}")
    public ResponseEntity<PagedModel<BoardDto>> getSimilarBoards(@PathVariable("boardID") UUID boardID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<BoardDto> boards = this.boardService.getSimilarBoards(boardID,paginationRequest.toPageRequest());
        return ResponseEntity.ok(boards);
    }

    @GetMapping("/public/orderTypes")
    public ResponseEntity<CollectionModel<String>> getOrderTypes() {
        CollectionModel<String> orderTypes = this.boardService.getOrderTypes();
        return ResponseEntity.ok(orderTypes);
    }


    @GetMapping("/private/title/{title}")
    public ResponseEntity<PagedModel<BoardDto>> getBoardsByTitle(@PathVariable("title") String title,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<BoardDto> boards = this.boardService.getBoardsByTile(title,paginationRequest.toPageRequest());
        return ResponseEntity.ok(boards);
    }

    @GetMapping("/private/description/{description}")
    public ResponseEntity<PagedModel<BoardDto>> getBoardsByDescription(@PathVariable("description") String description,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<BoardDto> boards = this.boardService.getBoardsByDescription(description,paginationRequest.toPageRequest());
        return ResponseEntity.ok(boards);
    }

    @DeleteMapping("/private/{boardID}")
    public ResponseEntity<BoardDto> deleteBoard(@PathVariable("boardID") UUID boardID) {
        this.boardService.deleteBoard(boardID);
        return ResponseEntity.noContent().build();
    }
}

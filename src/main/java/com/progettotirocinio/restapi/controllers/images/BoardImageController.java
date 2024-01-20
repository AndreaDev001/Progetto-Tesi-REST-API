package com.progettotirocinio.restapi.controllers.images;


import com.progettotirocinio.restapi.data.dto.input.PaginationRequest;
import com.progettotirocinio.restapi.data.dto.input.create.images.CreateBoardImageDto;
import com.progettotirocinio.restapi.data.dto.output.images.BoardImageDto;
import com.progettotirocinio.restapi.services.interfaces.images.BoardImageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boardImages")
public class BoardImageController
{
    private final BoardImageService boardImageService;

    @GetMapping("/private")
    public ResponseEntity<PagedModel<BoardImageDto>> getBoardImages(@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<BoardImageDto> boardImages = this.boardImageService.getBoardImages(paginationRequest.toPageRequest());
        return ResponseEntity.ok(boardImages);
    }

    @GetMapping("/private/{boardID}")
    public ResponseEntity<BoardImageDto> getBoardImage(@PathVariable("boardID") UUID boardID) {
        BoardImageDto boardImage = this.boardImageService.getBoard(boardID);
        return ResponseEntity.ok(boardImage);
    }

    @PostMapping("/private")
    public ResponseEntity<BoardImageDto> uploadBoardImage(@ModelAttribute @Valid CreateBoardImageDto createBoardImageDto) {
        BoardImageDto boardImageDto = this.boardImageService.getBoardImageByBoard(createBoardImageDto.getBoardID());
        return ResponseEntity.ok(boardImageDto);
    }

    @GetMapping("/private/board/{boardID}")
    public ResponseEntity<BoardImageDto> getBoardImageByBoard(@PathVariable("boardID") UUID boardID) {
        BoardImageDto boardImageDto = this.boardImageService.getBoardImageByBoard(boardID);
        return ResponseEntity.ok(boardImageDto);
    }
}

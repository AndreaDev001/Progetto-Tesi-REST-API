package com.progettotirocinio.restapi.controllers.images;


import com.progettotirocinio.restapi.data.dto.input.PaginationRequest;
import com.progettotirocinio.restapi.data.dto.output.images.BoardImageDto;
import com.progettotirocinio.restapi.services.interfaces.images.BoardImageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<BoardImageDto> getBoard(@PathVariable("boardID") UUID boardID) {
        BoardImageDto boardImage = this.boardImageService.getBoard(boardID);
        return ResponseEntity.ok(boardImage);
    }

    @GetMapping("/private/board/{boardID}")
    public ResponseEntity<BoardImageDto> getBoardImageByBoard(@PathVariable("boardID") UUID boardID) {
        BoardImageDto boardImageDto = this.boardImageService.getBoardImageByBoard(boardID);
        return ResponseEntity.ok(boardImageDto);
    }
}

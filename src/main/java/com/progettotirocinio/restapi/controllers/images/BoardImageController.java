package com.progettotirocinio.restapi.controllers.images;


import com.progettotirocinio.restapi.data.dto.input.PaginationRequest;
import com.progettotirocinio.restapi.data.dto.input.create.images.CreateBoardImageDto;
import com.progettotirocinio.restapi.data.dto.output.images.BoardImageDto;
import com.progettotirocinio.restapi.services.interfaces.images.BoardImageService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boardImages")
@SecurityRequirement(name = "Authorization")
public class BoardImageController
{
    private final BoardImageService boardImageService;

    @GetMapping("/private")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<BoardImageDto>> getBoardImages(@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<BoardImageDto> boardImages = this.boardImageService.getBoardImages(paginationRequest.toPageRequest());
        return ResponseEntity.ok(boardImages);
    }

    @GetMapping("/private/{boardID}")
    @PreAuthorize("@permissionHandler.isMember(#boardImageID)")
    public ResponseEntity<BoardImageDto> getBoardImage(@PathVariable("boardImageID") UUID boardImageID) {
        BoardImageDto boardImage = this.boardImageService.getBoard(boardImageID);
        return ResponseEntity.ok(boardImage);
    }

    @PostMapping(value = "/private/{boardID}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("@permissionHandler.hasBoardRole('ADMIN',#boardID)")
    public ResponseEntity<BoardImageDto> uploadBoardImage(@PathVariable("boardID") UUID boardID,@ModelAttribute @Valid CreateBoardImageDto createBoardImageDto) {
        BoardImageDto boardImageDto = this.boardImageService.uploadImage(boardID,createBoardImageDto);
        return ResponseEntity.ok(boardImageDto);
    }

    @GetMapping("/private/board/{boardID}")
    @PreAuthorize("@permissionHandler.isMember(#boardID)")
    public ResponseEntity<BoardImageDto> getBoardImageByBoard(@PathVariable("boardID") UUID boardID) {
        BoardImageDto boardImageDto = this.boardImageService.getBoardImageByBoard(boardID);
        return ResponseEntity.ok(boardImageDto);
    }

    @GetMapping("/private/image/{boardID}")
    @PreAuthorize("@permissionHandler.isMember(#boardID)")
    public ResponseEntity<byte[]> getBoardImageByBoardAsBytes(@PathVariable("boardID") UUID boardID) {
        BoardImageDto boardImageDto = this.boardImageService.getBoardImageByBoard(boardID);
        return ResponseEntity.ok().contentType(MediaType.valueOf(boardImageDto.getType().getName())).body(boardImageDto.getImage());
    }
}

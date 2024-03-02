package com.progettotirocinio.restapi.services.interfaces.images;

import com.progettotirocinio.restapi.data.dto.input.create.images.CreateBoardImageDto;
import com.progettotirocinio.restapi.data.dto.output.images.BoardImageDto;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import java.util.Optional;
import java.util.UUID;

public interface BoardImageService
{
    PagedModel<BoardImageDto> getBoardImages(Pageable pageable);
    BoardImageDto getBoardImageByBoard(UUID boardID);
    BoardImageDto getBoard(UUID boardID);
    BoardImageDto uploadImage(UUID boardID,CreateBoardImageDto createBoardImageDto);
}

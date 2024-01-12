package com.progettotirocinio.restapi.services.interfaces;

import com.progettotirocinio.restapi.data.dao.BoardDao;
import com.progettotirocinio.restapi.data.dto.output.BoardDto;
import com.progettotirocinio.restapi.data.entities.Board;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.RepresentationModel;

import java.util.UUID;

public interface BoardService {
    PagedModel<BoardDto> getBoards(Pageable pageable);
    PagedModel<BoardDto> getBoardsByPublisher(UUID publisherID,Pageable pageable);
    PagedModel<BoardDto> getBoardsByTile(String title,Pageable pageable);
    PagedModel<BoardDto> getBoardsByDescription(String description,Pageable pageable);
    BoardDto getBoard(UUID id);
    void deleteBoard(UUID id);
}

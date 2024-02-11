package com.progettotirocinio.restapi.services.interfaces;

import com.progettotirocinio.restapi.data.dao.BoardDao;
import com.progettotirocinio.restapi.data.dto.input.create.CreateBoardDto;
import com.progettotirocinio.restapi.data.dto.input.update.UpdateBoardDto;
import com.progettotirocinio.restapi.data.dto.output.BoardDto;
import com.progettotirocinio.restapi.data.entities.Board;
import com.progettotirocinio.restapi.data.entities.enums.BoardStatus;
import com.progettotirocinio.restapi.data.entities.enums.BoardVisibility;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.RepresentationModel;

import java.util.UUID;

public interface BoardService {
    PagedModel<BoardDto> getBoards(Pageable pageable);
    PagedModel<BoardDto> getBoardsByPublisher(UUID publisherID,Pageable pageable);
    PagedModel<BoardDto> getBoardsByTile(String title,Pageable pageable);
    PagedModel<BoardDto> getBoardsByDescription(String description,Pageable pageable);
    PagedModel<BoardDto> getBoardsBySpec(Specification<Board> specification,Pageable pageable);
    PagedModel<BoardDto> getSimilarBoards(UUID boardID,Pageable pageable);
    PagedModel<BoardDto> getBoardsByStatus(BoardStatus status,Pageable pageable);
    CollectionModel<BoardVisibility> getVisibilities();
    CollectionModel<BoardStatus> getStatues();
    CollectionModel<String> getOrderTypes();
    BoardDto getBoard(UUID id);
    BoardDto createBoard(CreateBoardDto createBoardDto);
    BoardDto updateBoard(UpdateBoardDto updateBoardDto);
    void handleExpiredBoards();
    void deleteExpiredBoards();
    void deleteBoard(UUID id);
}

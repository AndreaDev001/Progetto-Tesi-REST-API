package com.progettotirocinio.restapi.services.implementations;

import com.progettotirocinio.restapi.data.dao.BoardDao;
import com.progettotirocinio.restapi.data.dto.output.BoardDto;
import com.progettotirocinio.restapi.data.entities.Board;
import com.progettotirocinio.restapi.services.interfaces.BoardService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BoardServiceImp extends GenericServiceImp<Board, BoardDto> implements BoardService  {

    private final BoardDao boardDao;
    public BoardServiceImp(BoardDao boardDao,ModelMapper modelMapper, PagedResourcesAssembler<Board> pagedResourcesAssembler) {
        super(modelMapper,Board.class,BoardDto.class,pagedResourcesAssembler);
        this.boardDao = boardDao;
    }

    @Override
    public PagedModel<BoardDto> getBoards(Pageable pageable) {
        Page<Board> boards = this.boardDao.findAll(pageable);
        return this.pagedResourcesAssembler.toModel(boards,modelAssembler);
    }

    @Override
    public PagedModel<BoardDto> getBoardsByPublisher(UUID publisherID, Pageable pageable) {
        Page<Board> boards = this.boardDao.getBoardsByPublisher(publisherID,pageable);
        return this.pagedResourcesAssembler.toModel(boards,modelAssembler);
    }

    @Override
    public PagedModel<BoardDto> getBoardsByTile(String title, Pageable pageable) {
        Page<Board> boards = this.boardDao.getBoardsByTitle(title,pageable);
        return this.pagedResourcesAssembler.toModel(boards,modelAssembler);
    }

    @Override
    public PagedModel<BoardDto> getBoardsByDescription(String description, Pageable pageable) {
        Page<Board> boards = this.boardDao.getBoardsByDescription(description,pageable);
        return this.pagedResourcesAssembler.toModel(boards,modelAssembler);
    }

    @Override
    public BoardDto getBoard(UUID id) {
        Board board = this.boardDao.findById(id).orElseThrow();
        return this.modelMapper.map(board,BoardDto.class);
    }

    @Override
    public void deleteBoard(UUID id) {
        this.boardDao.findById(id).orElseThrow();
        this.boardDao.deleteById(id);
    }
}

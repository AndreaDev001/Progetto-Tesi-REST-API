package com.progettotirocinio.restapi.services.implementations;

import com.progettotirocinio.restapi.config.mapper.Mapper;
import com.progettotirocinio.restapi.data.dao.BoardDao;
import com.progettotirocinio.restapi.data.dao.BoardInviteDao;
import com.progettotirocinio.restapi.data.dao.UserDao;
import com.progettotirocinio.restapi.data.dto.input.create.CreateBoardInviteDto;
import com.progettotirocinio.restapi.data.dto.output.BoardInviteDto;
import com.progettotirocinio.restapi.data.entities.Board;
import com.progettotirocinio.restapi.data.entities.BoardInvite;
import com.progettotirocinio.restapi.data.entities.User;
import com.progettotirocinio.restapi.data.entities.enums.BoardInviteStatus;
import com.progettotirocinio.restapi.services.interfaces.BoardInviteService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class BoardInviteServiceImp extends GenericServiceImp<BoardInvite, BoardInviteDto> implements BoardInviteService {

    private final BoardInviteDao boardInviteDao;
    private final BoardDao boardDao;

    public BoardInviteServiceImp(BoardDao boardDao,Mapper modelMapper, UserDao userDao, BoardInviteDao boardInviteDao, PagedResourcesAssembler<BoardInvite> pagedResourcesAssembler) {
        super(userDao,modelMapper,BoardInvite.class,BoardInviteDto.class, pagedResourcesAssembler);
        this.boardInviteDao = boardInviteDao;
        this.boardDao = boardDao;
    }

    @Override
    public PagedModel<BoardInviteDto> getBoardInvites(Pageable pageable) {
        Page<BoardInvite> boardInvites = this.boardInviteDao.findAll(pageable);
        return this.pagedResourcesAssembler.toModel(boardInvites,modelAssembler);
    }

    @Override
    public PagedModel<BoardInviteDto> getBoardInvitesByPublisher(UUID publisherID, Pageable pageable) {
        Page<BoardInvite> boardInvites = this.boardInviteDao.getBoardInvitesByPublisher(publisherID,pageable);
        return this.pagedResourcesAssembler.toModel(boardInvites,modelAssembler);
    }

    @Override
    public PagedModel<BoardInviteDto> getBoardInvitesByBoard(UUID boardID, Pageable pageable) {
        Page<BoardInvite> boardInvites = this.boardInviteDao.getBoardInvitesByBoard(boardID,pageable);
        return this.pagedResourcesAssembler.toModel(boardInvites,modelAssembler);
    }

    @Override
    public PagedModel<BoardInviteDto> getBoardInvitesByStatus(BoardInviteStatus status, Pageable pageable) {
        Page<BoardInvite> boardInvites = this.boardInviteDao.getBoardInvitesByStatus(status,pageable);
        return this.pagedResourcesAssembler.toModel(boardInvites,modelAssembler);
    }

    @Override
    public BoardInviteDto getBoardInvite(UUID inviteID) {
        BoardInvite boardInvite = this.boardInviteDao.findById(inviteID).orElseThrow();
        return this.modelMapper.map(boardInvite,BoardInviteDto.class);
    }

    @Override
    public BoardInviteDto createBoardInvite(CreateBoardInviteDto createBoardInviteDto) {
        User publisher = this.userDao.findById(UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName())).orElseThrow();
        User invitedUser = this.userDao.findById(createBoardInviteDto.getUserID()).orElseThrow();
        Board requiredBoard = this.boardDao.findById(createBoardInviteDto.getBoardID()).orElseThrow();
        BoardInvite boardInvite = new BoardInvite();
        boardInvite.setBoard(requiredBoard);
        boardInvite.setPublisher(invitedUser);
        boardInvite.setStatus(BoardInviteStatus.ACTIVE);
        boardInvite.setText(createBoardInviteDto.getText());
        boardInvite.setPublisher(publisher);
        boardInvite = this.boardInviteDao.save(boardInvite);
        return this.modelMapper.map(boardInvite,BoardInviteDto.class);
    }

    @Override
    public void deleteBoardInvite(UUID inviteID) {
        this.boardInviteDao.findById(inviteID).orElseThrow();
        this.boardInviteDao.deleteById(inviteID);
    }
}

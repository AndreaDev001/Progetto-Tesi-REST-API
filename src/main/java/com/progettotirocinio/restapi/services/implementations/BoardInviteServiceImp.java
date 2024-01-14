package com.progettotirocinio.restapi.services.implementations;

import com.progettotirocinio.restapi.data.dao.BoardInviteDao;
import com.progettotirocinio.restapi.data.dto.output.BoardInviteDto;
import com.progettotirocinio.restapi.data.entities.Board;
import com.progettotirocinio.restapi.data.entities.BoardInvite;
import com.progettotirocinio.restapi.data.entities.enums.BoardInviteStatus;
import com.progettotirocinio.restapi.services.interfaces.BoardInviteService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class BoardInviteServiceImp extends GenericServiceImp<BoardInvite, BoardInviteDto> implements BoardInviteService {

    private final BoardInviteDao boardInviteDao;
    public BoardInviteServiceImp(ModelMapper modelMapper,BoardInviteDao boardInviteDao,PagedResourcesAssembler<BoardInvite> pagedResourcesAssembler) {
        super(modelMapper,BoardInvite.class,BoardInviteDto.class, pagedResourcesAssembler);
        this.boardInviteDao = boardInviteDao;
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
    public void deleteBoardInvite(UUID inviteID) {
        this.boardInviteDao.findById(inviteID).orElseThrow();
        this.boardInviteDao.deleteById(inviteID);
    }
}

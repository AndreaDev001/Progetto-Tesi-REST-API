package com.progettotirocinio.restapi.services.implementations;

import com.progettotirocinio.restapi.config.caching.CacheHandler;
import com.progettotirocinio.restapi.config.caching.RequiresCaching;
import com.progettotirocinio.restapi.config.exceptions.InvalidFormat;
import com.progettotirocinio.restapi.config.mapper.Mapper;
import com.progettotirocinio.restapi.data.dao.*;
import com.progettotirocinio.restapi.data.dto.input.create.CreateBoardInviteDto;
import com.progettotirocinio.restapi.data.dto.input.update.UpdateBoardInviteDto;
import com.progettotirocinio.restapi.data.dto.output.BoardInviteDto;
import com.progettotirocinio.restapi.data.entities.*;
import com.progettotirocinio.restapi.data.entities.enums.BoardInviteStatus;
import com.progettotirocinio.restapi.data.entities.enums.TaskStatus;
import com.progettotirocinio.restapi.services.interfaces.BoardInviteService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;


@Service
@RequiresCaching(allCacheName = "BOARD_INVITES")
public class BoardInviteServiceImp extends GenericServiceImp<BoardInvite, BoardInviteDto> implements BoardInviteService {

    private final BoardInviteDao boardInviteDao;
    private final RoleDao roleDao;
    private final RoleOwnerDao roleOwnerDao;
    private final BoardMemberDao boardMemberDao;
    private final BoardDao boardDao;

    public BoardInviteServiceImp(CacheHandler cacheHandler,BoardDao boardDao,BoardMemberDao boardMemberDao,RoleDao roleDao,RoleOwnerDao roleOwnerDao, Mapper modelMapper, UserDao userDao, BoardInviteDao boardInviteDao, PagedResourcesAssembler<BoardInvite> pagedResourcesAssembler) {
        super(cacheHandler,userDao,modelMapper,BoardInvite.class,BoardInviteDto.class, pagedResourcesAssembler);
        this.boardInviteDao = boardInviteDao;
        this.boardDao = boardDao;
        this.roleDao = roleDao;
        this.roleOwnerDao = roleOwnerDao;
        this.boardMemberDao = boardMemberDao;
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
    public PagedModel<BoardInviteDto> getBoardInvitesByReceiver(UUID receiverID, Pageable pageable) {
        Page<BoardInvite> boardInvites = this.boardInviteDao.getBoardInvitesByReceiver(receiverID,pageable);
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
    @Transactional
    public BoardInviteDto createBoardInvite(CreateBoardInviteDto createBoardInviteDto) {
        User publisher = this.userDao.findById(UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName())).orElseThrow();
        User invitedUser = this.userDao.findById(createBoardInviteDto.getUserID()).orElseThrow();
        if(publisher.getId().equals(invitedUser.getId()))
            throw new InvalidFormat("error.boardInvite.invalidPublisher");
        Board requiredBoard = this.boardDao.findById(createBoardInviteDto.getBoardID()).orElseThrow();
        BoardInvite boardInvite = new BoardInvite();
        boardInvite.setBoard(requiredBoard);
        boardInvite.setStatus(BoardInviteStatus.ACTIVE);
        boardInvite.setText(createBoardInviteDto.getText());
        boardInvite.setExpirationDate(createBoardInviteDto.getExpirationDate());
        boardInvite.setReceiver(invitedUser);
        boardInvite.setPublisher(publisher);
        boardInvite = this.boardInviteDao.save(boardInvite);
        return this.modelMapper.map(boardInvite,BoardInviteDto.class);
    }

    @Override
    @Transactional
    public BoardInviteDto updateBoardInvite(UpdateBoardInviteDto updateBoardInviteDto) {
        User authenticatedUser = this.userDao.findById(UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName())).orElseThrow();
        BoardInvite boardInvite = this.boardInviteDao.findById(updateBoardInviteDto.getInviteID()).orElseThrow();
        if(boardInvite.getStatus() != BoardInviteStatus.ACTIVE)
            throw new InvalidFormat("error.boardInvite.update.invalidCurrentStatus");
        if(authenticatedUser.getId().equals(boardInvite.getPublisher().getId()))
            throw new InvalidFormat("error.boardInvite.update.invalidUser");
        if(updateBoardInviteDto.getText() != null)
            boardInvite.setText(updateBoardInviteDto.getText());
        if(updateBoardInviteDto.getStatus() != null)
             boardInvite.setStatus(updateBoardInviteDto.getStatus());
        if(updateBoardInviteDto.getStatus().equals(BoardInviteStatus.ACCEPTED))
        {
            Board board = this.boardDao.findById(boardInvite.getBoardID()).orElseThrow();
            if(board.getMaxMembers() < board.getMembers().size() + 1)
                throw new InvalidFormat("error.boardInvite.boardFull");
            BoardMember boardMember = new BoardMember();
            boardMember.setUser(authenticatedUser);
            boardMember.setBoard(boardInvite.getBoard());
            this.boardMemberDao.save(boardMember);
            Role role = this.roleDao.getRoleByNameAndBoard("MEMBER",boardInvite.getBoard().getId()).orElseThrow();
            RoleOwner roleOwner = new RoleOwner();
            roleOwner.setOwner(authenticatedUser);
            roleOwner.setRole(role);
            this.roleOwnerDao.save(roleOwner);
        }
        return this.modelMapper.map(boardInvite,BoardInviteDto.class);
    }

    @Override
    public CollectionModel<BoardInviteStatus> getStatues() {
        return CollectionModel.of(Arrays.stream(BoardInviteStatus.values()).toList());
    }

    @Override
    @Transactional
    @Scheduled(fixedDelay = 24 * 60 * 60 * 1000)
    public void handleExpiredInvites() {
        List<BoardInvite> boardInvites = this.boardInviteDao.getBoardInvitesByDate(LocalDate.now());
        for(BoardInvite boardInvite : boardInvites)
            boardInvite.setStatus(BoardInviteStatus.EXPIRED);
        this.boardInviteDao.saveAll(boardInvites);
    }

    @Override
    @Transactional
    @Scheduled(fixedDelay = 24 * 60 * 60 * 1000,initialDelay = 24 * 60 * 60 * 1000)
    public void deleteExpiredInvites() {
        List<BoardInvite> boardInvites = this.boardInviteDao.getBoardInvitesByStatus(TaskStatus.EXPIRED);
        this.boardInviteDao.deleteAll(boardInvites);
    }

    @Override
    @Transactional
    public void deleteBoardInvite(UUID inviteID) {
        this.boardInviteDao.findById(inviteID).orElseThrow();
        this.boardInviteDao.deleteById(inviteID);
    }
}

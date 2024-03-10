package com.progettotirocinio.restapi.services.implementations;

import com.progettotirocinio.restapi.config.caching.CacheHandler;
import com.progettotirocinio.restapi.config.caching.RequiresCaching;
import com.progettotirocinio.restapi.config.mapper.Mapper;
import com.progettotirocinio.restapi.data.dao.BoardDao;
import com.progettotirocinio.restapi.data.dao.BoardMemberDao;
import com.progettotirocinio.restapi.data.dao.UserDao;
import com.progettotirocinio.restapi.data.dao.bans.BoardBanDao;
import com.progettotirocinio.restapi.data.dto.output.BoardMemberDto;
import com.progettotirocinio.restapi.data.entities.Board;
import com.progettotirocinio.restapi.data.entities.BoardMember;
import com.progettotirocinio.restapi.data.entities.User;
import com.progettotirocinio.restapi.services.interfaces.BoardMemberService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiresCaching(allCacheName = "ALL_BOARD_MEMBERS")
public class BoardMemberServiceImp extends GenericServiceImp<BoardMember, BoardMemberDto> implements BoardMemberService
{
    private final BoardMemberDao boardMemberDao;
    private final BoardDao boardDao;

    public BoardMemberServiceImp(CacheHandler cacheHandler,BoardDao boardDao, UserDao userDao, BoardMemberDao boardMemberDao, Mapper mapper, PagedResourcesAssembler<BoardMember> pagedResourcesAssembler) {
        super(cacheHandler,userDao, mapper, BoardMember.class,BoardMemberDto.class, pagedResourcesAssembler);
        this.boardMemberDao = boardMemberDao;
        this.boardDao = boardDao;
    }

    @Override
    public PagedModel<BoardMemberDto> getBoardMembers(Pageable pageable) {
        Page<BoardMember> boardMembers = this.boardMemberDao.findAll(pageable);
        return this.pagedResourcesAssembler.toModel(boardMembers,modelAssembler);
    }

    @Override
    public PagedModel<BoardMemberDto> getBoardMembersByUser(UUID userID, Pageable pageable) {
        Page<BoardMember> boardMembers = this.boardMemberDao.getBoardMembers(userID,pageable);
        return this.pagedResourcesAssembler.toModel(boardMembers,modelAssembler);
    }

    @Override
    public CollectionModel<BoardMemberDto> getBoardMembersByBoard(UUID boardID) {
        List<BoardMember> boardMembers = this.boardMemberDao.getBoardMembersByBoard(boardID);
        return CollectionModel.of(boardMembers.stream().map(boardMember -> this.modelMapper.map(boardMember,BoardMemberDto.class)).collect(Collectors.toList()));
    }

    @Override
    public CollectionModel<BoardMemberDto> getPossibleTaskMembers(UUID boardID, UUID taskID) {
        List<BoardMember> boardMembers = this.boardMemberDao.getPossibleTaskMembers(boardID,taskID);
        return CollectionModel.of(boardMembers.stream().map(boardMember -> this.modelMapper.map(boardMember,BoardMemberDto.class)).collect(Collectors.toList()));
    }

    @Override
    public CollectionModel<BoardMemberDto> getPossibleTeamMembers(UUID boardID, UUID teamID) {
        List<BoardMember> boardMembers = this.boardMemberDao.getPossibleTeamMembers(boardID,teamID);
        return CollectionModel.of(boardMembers.stream().map(boardMember -> this.modelMapper.map(boardMember,BoardMemberDto.class)).collect(Collectors.toList()));
    }

    @Override
    public BoardMemberDto getBoardMember(UUID boardMemberID) {
        BoardMember boardMember = this.boardMemberDao.findById(boardMemberID).orElseThrow();
        return this.modelMapper.map(boardMember,BoardMemberDto.class);
    }

    @Override
    public BoardMemberDto isMember(UUID userID, UUID boardID) {
        BoardMember boardMember = this.boardMemberDao.getBoardMember(boardID,userID).orElseThrow();
        return this.modelMapper.map(boardMember,BoardMemberDto.class);
    }

    @Override
    @Transactional
    public BoardMemberDto createMember(UUID boardID) {
        User authenticatedUser = this.userDao.findById(UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName())).orElseThrow();
        Board board = this.boardDao.findById(boardID).orElseThrow();
        BoardMember boardMember = new BoardMember();
        boardMember.setUser(authenticatedUser);
        boardMember.setBoard(board);
        boardMember = this.boardMemberDao.save(boardMember);
        return this.modelMapper.map(boardMember,BoardMemberDto.class);
    }

    @Override
    @Transactional
    public void deleteMember(UUID boardMemberID) {
        this.boardMemberDao.findById(boardMemberID).orElseThrow();
        this.boardMemberDao.deleteById(boardMemberID);
    }

    @Override
    @Transactional
    public void deleteMemberFromBoard(UUID boardID, UUID userID) {
        BoardMember boardMember = this.boardMemberDao.getBoardMember(boardID,userID).orElseThrow();
        this.boardMemberDao.deleteById(boardMember.getId());
    }
}

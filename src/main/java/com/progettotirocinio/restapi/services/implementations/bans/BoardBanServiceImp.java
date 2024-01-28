package com.progettotirocinio.restapi.services.implementations.bans;

import com.progettotirocinio.restapi.config.exceptions.InvalidFormat;
import com.progettotirocinio.restapi.config.mapper.Mapper;
import com.progettotirocinio.restapi.data.dao.BoardDao;
import com.progettotirocinio.restapi.data.dao.UserDao;
import com.progettotirocinio.restapi.data.dao.bans.BoardBanDao;
import com.progettotirocinio.restapi.data.dto.input.create.CreateBanDto;
import com.progettotirocinio.restapi.data.dto.output.bans.BoardBanDto;
import com.progettotirocinio.restapi.data.entities.Board;
import com.progettotirocinio.restapi.data.entities.User;
import com.progettotirocinio.restapi.data.entities.bans.BoardBan;
import com.progettotirocinio.restapi.data.entities.enums.BanType;
import com.progettotirocinio.restapi.services.implementations.GenericServiceImp;
import com.progettotirocinio.restapi.services.interfaces.bans.BoardBanService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BoardBanServiceImp extends GenericServiceImp<BoardBan, BoardBanDto> implements BoardBanService
{
    private final BoardBanDao boardBanDao;
    private final BoardDao boardDao;

    public BoardBanServiceImp(BoardBanDao boardBanDao,BoardDao boardDao,UserDao userDao, Mapper mapper, PagedResourcesAssembler<BoardBan> pagedResourcesAssembler) {
        super(userDao, mapper,BoardBan.class,BoardBanDto.class, pagedResourcesAssembler);
        this.boardBanDao = boardBanDao;
        this.boardDao = boardDao;
    }

    @Override
    public PagedModel<BoardBanDto> getBoardBans(Pageable pageable) {
        Page<BoardBan> boardBans = this.boardBanDao.findAll(pageable);
        return this.pagedResourcesAssembler.toModel(boardBans,modelAssembler);
    }

    @Override
    public PagedModel<BoardBanDto> getBoardBansByBanner(UUID bannerID, Pageable pageable) {
        Page<BoardBan> boardBans = this.boardBanDao.getBoardBansByBanner(bannerID,pageable);
        return this.pagedResourcesAssembler.toModel(boardBans,modelAssembler);
    }

    @Override
    public PagedModel<BoardBanDto> getBoardBansByBanned(UUID bannedID, Pageable pageable) {
        Page<BoardBan> boardBans = this.boardBanDao.getBoardBansByBanned(bannedID,pageable);
        return this.pagedResourcesAssembler.toModel(boardBans,modelAssembler);
    }

    @Override
    public PagedModel<BoardBanDto> getBoardBansByBoard(UUID boardID, Pageable pageable) {
        Page<BoardBan> boardBans = this.boardBanDao.getBoardBansByBoard(boardID,pageable);
        return this.pagedResourcesAssembler.toModel(boardBans,modelAssembler);
    }

    @Override
    public BoardBanDto getBoardBan(UUID boardBanID) {
        BoardBan boardBan = this.boardBanDao.findById(boardBanID).orElseThrow();
        return this.modelMapper.map(boardBan,BoardBanDto.class);
    }

    @Override
    public BoardBanDto createBoardBan(CreateBanDto createBanDto, UUID boardID) {
        User authenticatedUser = this.userDao.findById(UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName())).orElseThrow();
        User bannedUser = this.userDao.findById(createBanDto.getBannedID()).orElseThrow();
        Board board = this.boardDao.findById(boardID).orElseThrow();
        if(bannedUser.getId().equals(board.getOwnerID()))
            throw new InvalidFormat("error.boardBan.invalidBanned");
        BoardBan boardBan = new BoardBan();
        boardBan.setBanner(authenticatedUser);
        boardBan.setBanned(bannedUser);
        boardBan.setBoard(board);
        boardBan.setTitle(createBanDto.getTitle());
        boardBan.setDescription(createBanDto.getDescription());
        boardBan.setReason(createBanDto.getReason());
        boardBan.setExpirationDate(createBanDto.getExpirationDate());
        boardBan.setExpired(false);
        boardBan.setType(BanType.BOARD_BAN);
        boardBan = this.boardBanDao.save(boardBan);
        return this.modelMapper.map(boardBan,BoardBanDto.class);
    }

    @Override
    public void deleteBoardBan(UUID boardBanID) {
        this.boardBanDao.findById(boardBanID).orElseThrow();
        this.boardBanDao.deleteById(boardBanID);
    }
}

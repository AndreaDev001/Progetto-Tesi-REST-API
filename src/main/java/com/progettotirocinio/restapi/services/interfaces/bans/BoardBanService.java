package com.progettotirocinio.restapi.services.interfaces.bans;

import com.progettotirocinio.restapi.data.dto.output.bans.BoardBanDto;
import com.progettotirocinio.restapi.data.entities.bans.BoardBan;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import java.util.UUID;

public interface BoardBanService
{
    PagedModel<BoardBanDto> getBoardBans(Pageable pageable);
    PagedModel<BoardBanDto> getBoardBansByBanner(UUID bannerID,Pageable pageable);
    PagedModel<BoardBanDto> getBoardBansByBanned(UUID bannedID,Pageable pageable);
    PagedModel<BoardBanDto> getBoardBansByBoard(UUID boardID,Pageable pageable);
    BoardBanDto getBoardBan(UUID boardBanID);
    void deleteBoardBan(UUID boardBanID);
}

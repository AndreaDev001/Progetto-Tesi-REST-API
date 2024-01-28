package com.progettotirocinio.restapi.controllers.bans;

import com.progettotirocinio.restapi.data.dto.input.PaginationRequest;
import com.progettotirocinio.restapi.data.dto.output.bans.BanDto;
import com.progettotirocinio.restapi.data.dto.output.bans.BoardBanDto;
import com.progettotirocinio.restapi.data.entities.bans.BoardBan;
import com.progettotirocinio.restapi.services.interfaces.bans.BoardBanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/boardBans")
@RequiredArgsConstructor
public class BoardBanController
{
    private final BoardBanService boardBanService;

    @GetMapping("/private")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<BoardBanDto>> getBoardBans(@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<BoardBanDto> boardBans = this.boardBanService.getBoardBans(paginationRequest.toPageRequest());
        return ResponseEntity.ok(boardBans);
    }

    @GetMapping("/private/{boardBanID}")
    @PreAuthorize("@permissionHandler.hasAccess(@boardBanDao,#boardBanID)")
    public ResponseEntity<BoardBanDto> getBoardBan(@PathVariable("boardBanID")UUID boardBanID) {
        BoardBanDto boardBanDto = this.boardBanService.getBoardBan(boardBanID);
        return ResponseEntity.ok(boardBanDto);
    }

    @GetMapping("/private/banner/{bannerID}")
    @PreAuthorize("@permissionHandler.hasAccess(#bannerID)")
    public ResponseEntity<PagedModel<BoardBanDto>> getBoardBansByBanner(@PathVariable("bannerID") UUID bannerID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<BoardBanDto> boardBans = this.boardBanService.getBoardBansByBanner(bannerID,paginationRequest.toPageRequest());
        return ResponseEntity.ok(boardBans);
    }

    @GetMapping("/private/banned/{bannedID}")
    @PreAuthorize("@permissionHandler.hasAccess(#bannedID)")
    public ResponseEntity<PagedModel<BoardBanDto>> getBoardBansByBanned(@PathVariable("bannedID") UUID bannedID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<BoardBanDto> boardBans = this.boardBanService.getBoardBansByBanned(bannedID,paginationRequest.toPageRequest());
        return ResponseEntity.ok(boardBans);
    }

    @GetMapping("/private/board/{boardID}")
    @PreAuthorize("@permissionHandler.hasAccess(@boardDao,#boardID)")
    public ResponseEntity<PagedModel<BoardBanDto>> getBoardBansByBoard(@PathVariable("boardID") UUID boardID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<BoardBanDto> boardBans = this.boardBanService.getBoardBansByBoard(boardID,paginationRequest.toPageRequest());
        return ResponseEntity.ok(boardBans);
    }

    @DeleteMapping("/private/{boardBanID}")
    @PreAuthorize("@permissionHandler.hasAccess(@boardBanDao,#boardBanID)")
    public ResponseEntity<Void> deleteBoardBan(@PathVariable("boardBanID") UUID boardBanID) {
       this.boardBanService.deleteBoardBan(boardBanID);
       return ResponseEntity.noContent().build();
    }
}

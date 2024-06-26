package com.progettotirocinio.restapi.controllers;


import com.progettotirocinio.restapi.data.dto.input.PaginationRequest;
import com.progettotirocinio.restapi.data.dto.input.create.CreateTeamDto;
import com.progettotirocinio.restapi.data.dto.input.update.UpdateTeamDto;
import com.progettotirocinio.restapi.data.dto.output.BoardDto;
import com.progettotirocinio.restapi.data.dto.output.TeamDto;
import com.progettotirocinio.restapi.services.interfaces.TeamService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/teams")
@SecurityRequirement(name = "Authorization")
public class TeamController
{
    private final TeamService teamService;

    @GetMapping("/private")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<TeamDto>> getTeams(@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<TeamDto> pagedModel = this.teamService.getTeams(paginationRequest.toPageRequest());
        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/private/publisher/{publisherID}")
    @PreAuthorize("@permissionHandler.hasAccess(#publisherID)")
    public ResponseEntity<PagedModel<TeamDto>> getTeamsByPublisher(@PathVariable("publisherID") UUID publisherID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<TeamDto> teams = this.teamService.getTeamsByPublisher(publisherID,paginationRequest.toPageRequest());
        return ResponseEntity.ok(teams);
    }

    @GetMapping("/private/board/{boardID}")
    @PreAuthorize("@permissionHandler.hasBoardRole('MEMBER',#boardID)")
    public ResponseEntity<CollectionModel<TeamDto>> getTeamsByBoard(@PathVariable("boardID") UUID boardID) {
        CollectionModel<TeamDto> collectionModel = this.teamService.getTeamsByBoard(boardID);
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/private/name/{name}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<TeamDto>> getTeamsByName(@PathVariable("name") String name,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<TeamDto> teams = this.teamService.getTeamsByName(name,paginationRequest.toPageRequest());
        return ResponseEntity.ok(teams);
    }

    @PostMapping("/private")
    @PreAuthorize("@permissionHandler.hasBoardRole('ADMIN',#createTeamDto.boardID)")
    public ResponseEntity<TeamDto> createTeam(@RequestBody @Valid CreateTeamDto createTeamDto) {
        TeamDto teamDto = this.teamService.createTeam(createTeamDto);
        return ResponseEntity.status(201).body(teamDto);
    }

    @PutMapping("/private")
    @PreAuthorize("@permissionHandler.hasBoardRole('ADMIN',#updateTeamDto.teamID,@teamDao)")
    public ResponseEntity<TeamDto> updateTeam(@RequestBody @Valid UpdateTeamDto updateTeamDto) {
        TeamDto teamDto = this.teamService.updateTeam(updateTeamDto);
        return ResponseEntity.ok(teamDto);
    }

    @GetMapping("/private/{teamID}")
    @PreAuthorize("@permissionHandler.hasAccess(@teamDao,#teamID)")
    public ResponseEntity<TeamDto> getTeam(@PathVariable("teamID") UUID teamID) {
        TeamDto team = this.teamService.getTeam(teamID);
        return ResponseEntity.ok(team);
    }

    @DeleteMapping("/private/{teamID}")
    @PreAuthorize("@permissionHandler.hasBoardRole('ADMIN',#teamID,@teamDao)")
    public ResponseEntity<Void> deleteTeam(@PathVariable("teamID") UUID teamID) {
        this.teamService.deleteTeam(teamID);
        return ResponseEntity.noContent().build();
    }
}

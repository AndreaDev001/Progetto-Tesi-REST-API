package com.progettotirocinio.restapi.controllers;


import com.progettotirocinio.restapi.data.dto.input.PaginationRequest;
import com.progettotirocinio.restapi.data.dto.input.create.CreateTeamDto;
import com.progettotirocinio.restapi.data.dto.output.BoardDto;
import com.progettotirocinio.restapi.data.dto.output.TeamDto;
import com.progettotirocinio.restapi.services.interfaces.TeamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/teams")
public class TeamController
{
    private final TeamService teamService;

    @GetMapping("/private")
    public ResponseEntity<PagedModel<TeamDto>> getTeams(@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<TeamDto> pagedModel = this.teamService.getTeams(paginationRequest.toPageRequest());
        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/private/publisher/{publisherID}")
    public ResponseEntity<PagedModel<TeamDto>> getTeamsByPublisher(@PathVariable("publisherID") UUID publisherID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<TeamDto> teams = this.teamService.getTeamsByPublisher(publisherID,paginationRequest.toPageRequest());
        return ResponseEntity.ok(teams);
    }

    @GetMapping("/private/board/{boardID}")
    public ResponseEntity<PagedModel<TeamDto>> getTeamsByBoard(@PathVariable("boardID") UUID boardID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<TeamDto> teams = this.teamService.getTeamsByBoard(boardID,paginationRequest.toPageRequest());
        return ResponseEntity.ok(teams);
    }

    @GetMapping("/private/name/{name}")
    public ResponseEntity<PagedModel<TeamDto>> getTeamsByName(@PathVariable("name") String name,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<TeamDto> teams = this.teamService.getTeamsByName(name,paginationRequest.toPageRequest());
        return ResponseEntity.ok(teams);
    }

    @PostMapping("/private")
    public ResponseEntity<TeamDto> createTeam(@RequestBody @Valid CreateTeamDto createTeamDto) {
        TeamDto teamDto = this.teamService.createTeam(createTeamDto);
        return ResponseEntity.status(201).body(teamDto);
    }

    @GetMapping("/private/{teamID}")
    public ResponseEntity<TeamDto> getTeam(@PathVariable("teamID") UUID teamID) {
        TeamDto team = this.teamService.getTeam(teamID);
        return ResponseEntity.ok(team);
    }

    @DeleteMapping("/private/{teamID}")
    public ResponseEntity<Void> deleteTeam(@PathVariable("teamID") UUID teamID) {
        this.teamService.deleteTeam(teamID);
        return ResponseEntity.noContent().build();
    }
}

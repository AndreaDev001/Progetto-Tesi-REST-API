package com.progettotirocinio.restapi.controllers;


import com.progettotirocinio.restapi.data.dto.input.PaginationRequest;
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

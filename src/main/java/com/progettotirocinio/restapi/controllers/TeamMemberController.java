package com.progettotirocinio.restapi.controllers;


import com.progettotirocinio.restapi.data.dto.input.PaginationRequest;
import com.progettotirocinio.restapi.data.dto.input.create.CreateTeamMemberDto;
import com.progettotirocinio.restapi.data.dto.output.TeamMemberDto;
import com.progettotirocinio.restapi.services.interfaces.TeamMemberService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/teamMembers")
@SecurityRequirement(name = "Authorization")
public class TeamMemberController
{
    private final TeamMemberService teamMemberService;

    @GetMapping("/private")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<TeamMemberDto>> getTeamMembers(@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<TeamMemberDto> pagedModel = this.teamMemberService.getTeamMembers(paginationRequest.toPageRequest());
        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/private/{teamMemberID}")
    @PreAuthorize("@permissionHandler.hasAccess(@teamMemberDao,#teamMemberID)")
    public ResponseEntity<TeamMemberDto> getTeamMember(@PathVariable("teamMemberID") UUID teamMemberID) {
        TeamMemberDto teamMember = this.teamMemberService.getTeamMember(teamMemberID);
        return ResponseEntity.ok(teamMember);
    }

    @PostMapping("/private/{teamID}")
    @PreAuthorize("@permissionHandler.hasBoardRole('ADMIN',#teamID,@teamDao)")
    public ResponseEntity<TeamMemberDto> createTeamMember(@PathVariable("teamID") UUID teamID) {
        TeamMemberDto teamMemberDto = this.teamMemberService.createTeamMember(teamID);
        return ResponseEntity.status(201).body(teamMemberDto);
    }

    @PostMapping("/private")
    @PreAuthorize("@permissionHandler.hasBoardRole('ADMIN',#createTeamMemberDto.teamID,@teamDao)")
    public ResponseEntity<TeamMemberDto> createTeamMember(@RequestBody @Valid CreateTeamMemberDto createTeamMemberDto) {
        TeamMemberDto teamMemberDto = this.teamMemberService.createTeamMember(createTeamMemberDto);
        return ResponseEntity.ok(teamMemberDto);
    }

    @GetMapping("/private/member/{memberID}")
    @PreAuthorize("@permissionHandler.hasAccess(#memberID)")
    public ResponseEntity<PagedModel<TeamMemberDto>> getTeamMembersByMember(@PathVariable("memberID") UUID memberID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<TeamMemberDto> teamMembers = this.teamMemberService.getTeamMembersByMember(memberID,paginationRequest.toPageRequest());
        return ResponseEntity.ok(teamMembers);
    }

    @GetMapping("/private/team/{teamID}")
    @PreAuthorize("@permissionHandler.hasBoardRole('MEMBER',#teamID,@teamDao)")
    public ResponseEntity<CollectionModel<TeamMemberDto>> getTeamMembersByTeam(@PathVariable("teamID") UUID teamID) {
        CollectionModel<TeamMemberDto> teamMembers = this.teamMemberService.getTeamMembersByTeam(teamID);
        return ResponseEntity.ok(teamMembers);
    }

    @DeleteMapping("/private/{teamMemberID}")
    @PreAuthorize("@permissionHandler.hasBoardRole('ADMIN',#teamMemberID,@teamMemberDao)")
    public ResponseEntity<Void> deleteTeamMember(@PathVariable("teamMemberID") UUID teamMemberID) {
        this.teamMemberService.deleteTeamMember(teamMemberID);
        return ResponseEntity.noContent().build();
    }
}

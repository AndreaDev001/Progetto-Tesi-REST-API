package com.progettotirocinio.restapi.controllers;


import com.progettotirocinio.restapi.data.dto.input.PaginationRequest;
import com.progettotirocinio.restapi.data.dto.output.TeamMemberDto;
import com.progettotirocinio.restapi.services.interfaces.TeamMemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/teamMembers")
public class TeamMemberController
{
    private final TeamMemberService teamMemberService;

    @GetMapping("/private")
    public ResponseEntity<PagedModel<TeamMemberDto>> getTeamMembers(@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<TeamMemberDto> pagedModel = this.teamMemberService.getTeamMembers(paginationRequest.toPageRequest());
        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/private/{teamMemberID}")
    public ResponseEntity<TeamMemberDto> getTeamMember(@PathVariable("teamMemberID") UUID teamMemberID) {
        TeamMemberDto teamMember = this.teamMemberService.getTeamMember(teamMemberID);
        return ResponseEntity.ok(teamMember);
    }

    @GetMapping("/private/member/{memberID}")
    public ResponseEntity<PagedModel<TeamMemberDto>> getTeamMembersByMember(@PathVariable("memberID") UUID memberID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<TeamMemberDto> teamMembers = this.teamMemberService.getTeamMembersByMember(memberID,paginationRequest.toPageRequest());
        return ResponseEntity.ok(teamMembers);
    }

    @GetMapping("/private/team/{teamID}")
    public ResponseEntity<PagedModel<TeamMemberDto>> getTeamMembersByTeam(@PathVariable("teamID") UUID teamID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<TeamMemberDto> teamMembers = this.teamMemberService.getTeamMembersByTeam(teamID,paginationRequest.toPageRequest());
        return ResponseEntity.ok(teamMembers);
    }

    @DeleteMapping("/private/{teamMemberID}")
    public ResponseEntity<Void> deleteTeamMember(@PathVariable("teamMemberID") UUID teamMemberID) {
        this.teamMemberService.deleteTeamMember(teamMemberID);
        return ResponseEntity.noContent().build();
    }
}

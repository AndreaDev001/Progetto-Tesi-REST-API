package com.progettotirocinio.restapi.services.interfaces;

import com.progettotirocinio.restapi.data.dto.input.create.CreateTeamMemberDto;
import com.progettotirocinio.restapi.data.dto.output.TeamMemberDto;
import com.progettotirocinio.restapi.data.entities.TeamMember;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;

import java.util.UUID;

public interface TeamMemberService {
    PagedModel<TeamMemberDto> getTeamMembers(Pageable pageable);
    PagedModel<TeamMemberDto> getTeamMembersByMember(UUID memberID,Pageable pageable);
    PagedModel<TeamMemberDto> getTeamMembersByUser(UUID userID,Pageable pageable);
    CollectionModel<TeamMemberDto> getTeamMembersByTeam(UUID teamID);
    TeamMemberDto getTeamMember(UUID memberID);
    TeamMemberDto createTeamMember(UUID teamID);
    TeamMemberDto createTeamMember(CreateTeamMemberDto createTeamMemberDto);
    void deleteTeamMember(UUID memberID);
}

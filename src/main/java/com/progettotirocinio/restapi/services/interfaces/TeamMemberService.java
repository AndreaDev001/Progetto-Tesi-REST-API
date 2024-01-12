package com.progettotirocinio.restapi.services.interfaces;

import com.progettotirocinio.restapi.data.dto.output.TeamMemberDto;
import com.progettotirocinio.restapi.data.entities.TeamMember;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import java.util.UUID;

public interface TeamMemberService {
    PagedModel<TeamMemberDto> getTeamMembers(Pageable pageable);
    PagedModel<TeamMemberDto> getTeamMembersByMember(UUID memberID,Pageable pageable);
    PagedModel<TeamMemberDto> getTeamMembersByTeam(UUID teamID,Pageable pageable);
    TeamMemberDto getTeamMember(UUID memberID);
    void deleteTeamMember(UUID memberID);
}

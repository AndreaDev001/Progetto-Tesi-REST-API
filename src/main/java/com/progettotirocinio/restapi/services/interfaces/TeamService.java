package com.progettotirocinio.restapi.services.interfaces;

import com.progettotirocinio.restapi.data.dto.input.create.CreateTeamDto;
import com.progettotirocinio.restapi.data.dto.output.TeamDto;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import java.util.UUID;

public interface TeamService {
    PagedModel<TeamDto> getTeams(Pageable pageable);
    PagedModel<TeamDto> getTeamsByPublisher(UUID publisherID,Pageable pageable);
    PagedModel<TeamDto> getTeamsByBoard(UUID boardID,Pageable pageable);
    PagedModel<TeamDto> getTeamsByName(String name,Pageable pageable);
    TeamDto getTeam(UUID id);
    TeamDto createTeam(CreateTeamDto createTeamDto);
    void deleteTeam(UUID id);
}

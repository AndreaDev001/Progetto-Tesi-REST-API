package com.progettotirocinio.restapi.services.implementations;

import com.progettotirocinio.restapi.data.dao.TeamDao;
import com.progettotirocinio.restapi.data.dto.output.TeamDto;
import com.progettotirocinio.restapi.data.entities.Team;
import com.progettotirocinio.restapi.services.interfaces.TeamService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class TeamServiceImp extends GenericServiceImp<Team, TeamDto> implements TeamService {

    private final TeamDao teamDao;

    public TeamServiceImp(ModelMapper modelMapper,TeamDao teamDao, PagedResourcesAssembler<Team> pagedResourcesAssembler) {
        super(modelMapper, Team.class,TeamDto.class, pagedResourcesAssembler);
        this.teamDao = teamDao;
    }

    @Override
    public PagedModel<TeamDto> getTeams(Pageable pageable) {
        Page<Team> teams = this.teamDao.findAll(pageable);
        return this.pagedResourcesAssembler.toModel(teams,modelAssembler);
    }

    @Override
    public PagedModel<TeamDto> getTeamsByPublisher(UUID publisherID, Pageable pageable) {
        Page<Team> teams = this.teamDao.getTeamsByPublisher(publisherID,pageable);
        return this.pagedResourcesAssembler.toModel(teams,modelAssembler);
    }

    @Override
    public PagedModel<TeamDto> getTeamsByBoard(UUID boardID, Pageable pageable) {
        Page<Team> teams = this.teamDao.getTeamsByBoard(boardID,pageable);
        return this.pagedResourcesAssembler.toModel(teams,modelAssembler);
    }

    @Override
    public PagedModel<TeamDto> getTeamsByName(String name, Pageable pageable) {
        Page<Team> teams = this.teamDao.getTeamsByName(name,pageable);
        return this.pagedResourcesAssembler.toModel(teams,modelAssembler);
    }

    @Override
    public TeamDto getTeam(UUID id) {
        Team team = this.teamDao.findById(id).orElseThrow();
        return this.modelMapper.map(team,TeamDto.class);
    }

    @Override
    public void deleteTeam(UUID id) {
        this.teamDao.findById(id).orElseThrow();
        this.teamDao.deleteById(id);
    }
}

package com.progettotirocinio.restapi.services.implementations;

import com.progettotirocinio.restapi.config.caching.CacheHandler;
import com.progettotirocinio.restapi.config.caching.RequiresCaching;
import com.progettotirocinio.restapi.config.exceptions.InvalidFormat;
import com.progettotirocinio.restapi.config.mapper.Mapper;
import com.progettotirocinio.restapi.data.dao.*;
import com.progettotirocinio.restapi.data.dto.input.create.CreateTeamDto;
import com.progettotirocinio.restapi.data.dto.input.update.UpdateTeamDto;
import com.progettotirocinio.restapi.data.dto.output.TeamDto;
import com.progettotirocinio.restapi.data.entities.*;
import com.progettotirocinio.restapi.services.interfaces.TeamService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiresCaching(allCacheName = "ALL_TEAMS")
public class TeamServiceImp extends GenericServiceImp<Team, TeamDto> implements TeamService {

    private final TeamDao teamDao;
    private final TeamMemberDao teamMemberDao;
    private final BoardMemberDao boardMemberDao;
    private final BoardDao boardDao;

    public TeamServiceImp(CacheHandler cacheHandler,BoardMemberDao boardMemberDao,TeamMemberDao teamMemberDao,UserDao userDao, BoardDao boardDao, Mapper mapper, TeamDao teamDao, PagedResourcesAssembler<Team> pagedResourcesAssembler) {
        super(cacheHandler,userDao,mapper, Team.class,TeamDto.class, pagedResourcesAssembler);
        this.boardDao = boardDao;
        this.teamDao = teamDao;
        this.teamMemberDao = teamMemberDao;
        this.boardMemberDao = boardMemberDao;
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
    public CollectionModel<TeamDto> getTeamsByBoard(UUID boardID) {
        List<Team> teams = this.teamDao.getTeamsByBoard(boardID);
        return CollectionModel.of(teams.stream().map(team -> this.modelMapper.map(team,TeamDto.class)).collect(Collectors.toList()));
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
    @Transactional
    public TeamDto createTeam(CreateTeamDto createTeamDto) {
        User publisher = this.userDao.findById(UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName())).orElseThrow();
        Board board = this.boardDao.findById(createTeamDto.getBoardID()).orElseThrow();
        Optional<BoardMember> boardMemberOptional = this.boardMemberDao.getBoardMember(board.getId(),publisher.getId());
        if(boardMemberOptional.isEmpty())
            throw new InvalidFormat("error.team.invalidPublisher");
        Team team = new Team();
        team.setName(createTeamDto.getName());
        team.setBoard(board);
        team.setPublisher(publisher);
        team = this.teamDao.save(team);
        TeamMember teamMember = new TeamMember();
        teamMember.setTeam(team);
        teamMember.setMember(boardMemberOptional.get());
        this.teamMemberDao.save(teamMember);
        return this.modelMapper.map(team,TeamDto.class);

    }

    @Override
    @Transactional
    public TeamDto updateTeam(UpdateTeamDto updateTeamDto) {
        Team team = this.teamDao.findById(updateTeamDto.getTeamID()).orElseThrow();
        if(updateTeamDto.getName() != null)
            team.setName(updateTeamDto.getName());
        team = this.teamDao.save(team);
        return this.modelMapper.map(team,TeamDto.class);
    }

    @Override
    @Transactional
    public void deleteTeam(UUID id) {
        this.teamDao.findById(id).orElseThrow();
        this.teamDao.deleteById(id);
    }
}

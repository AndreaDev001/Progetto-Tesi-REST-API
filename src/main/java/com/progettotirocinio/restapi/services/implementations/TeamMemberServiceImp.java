package com.progettotirocinio.restapi.services.implementations;

import com.progettotirocinio.restapi.config.caching.CacheHandler;
import com.progettotirocinio.restapi.config.caching.RequiresCaching;
import com.progettotirocinio.restapi.config.exceptions.InvalidFormat;
import com.progettotirocinio.restapi.config.mapper.Mapper;
import com.progettotirocinio.restapi.data.dao.BoardMemberDao;
import com.progettotirocinio.restapi.data.dao.TeamDao;
import com.progettotirocinio.restapi.data.dao.TeamMemberDao;
import com.progettotirocinio.restapi.data.dao.UserDao;
import com.progettotirocinio.restapi.data.dto.output.TeamMemberDto;
import com.progettotirocinio.restapi.data.entities.*;
import com.progettotirocinio.restapi.services.interfaces.TeamMemberService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiresCaching(allCacheName = "ALL_TEAM_MEMBERS")
public class TeamMemberServiceImp extends GenericServiceImp<TeamMember, TeamMemberDto> implements TeamMemberService {

    private final TeamMemberDao teamMemberDao;
    private final BoardMemberDao boardMemberDao;
    private final TeamDao teamDao;

    public TeamMemberServiceImp(CacheHandler cacheHandler, BoardMemberDao boardMemberDao, Mapper mapper, TeamDao teamDao, UserDao userDao, TeamMemberDao teamMemberDao, PagedResourcesAssembler<TeamMember> pagedResourcesAssembler) {
        super(cacheHandler,userDao,mapper, TeamMember.class,TeamMemberDto.class, pagedResourcesAssembler);
        this.teamDao = teamDao;
        this.teamMemberDao = teamMemberDao;
        this.boardMemberDao = boardMemberDao;
    }

    @Override
    public PagedModel<TeamMemberDto> getTeamMembers(Pageable pageable) {
        Page<TeamMember> teamMembers = this.teamMemberDao.findAll(pageable);
        return this.pagedResourcesAssembler.toModel(teamMembers,modelAssembler);
    }

    @Override
    public PagedModel<TeamMemberDto> getTeamMembersByMember(UUID memberID, Pageable pageable) {
        Page<TeamMember> teamMembers = this.teamMemberDao.getTeamMemberByMember(memberID,pageable);
        return this.pagedResourcesAssembler.toModel(teamMembers,modelAssembler);
    }

    @Override
    public CollectionModel<TeamMemberDto> getTeamMembersByTeam(UUID teamID) {
        List<TeamMember> teamMembers = this.teamMemberDao.getTeamMembersByTeam(teamID);
        return CollectionModel.of(teamMembers.stream().map(member -> this.modelMapper.map(member,TeamMemberDto.class)).collect(Collectors.toList()));
    }

    @Override
    public TeamMemberDto getTeamMember(UUID memberID) {
        TeamMember teamMember = this.teamMemberDao.findById(memberID).orElseThrow();
        return this.modelMapper.map(teamMember,TeamMemberDto.class);
    }

    @Override
    @Transactional
    public TeamMemberDto createTeamMember(UUID teamID) {
        Team requiredTeam = this.teamDao.findById(teamID).orElseThrow();
        User requiredUser = this.userDao.findById(UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName())).orElseThrow();
        Optional<BoardMember> boardMemberOptional = this.boardMemberDao.getBoardMember(requiredTeam.getBoard().getId(),requiredUser.getId());
        if(boardMemberOptional.isEmpty())
            throw new InvalidFormat("error.teamMember.missingBoardMember");
        TeamMember teamMember = new TeamMember();
        teamMember.setMember(requiredUser);
        teamMember.setTeam(requiredTeam);
        teamMember = this.teamMemberDao.save(teamMember);
        return this.modelMapper.map(teamMember,TeamMemberDto.class);
    }

    @Override
    @Transactional
    public void deleteTeamMember(UUID memberID) {
        TeamMember teamMember = this.teamMemberDao.findById(memberID).orElseThrow();
        this.teamMemberDao.deleteById(teamMember.getId());
        List<TeamMember> remainingMembers = this.teamMemberDao.getTeamMembersByTeam(teamMember.getTeam().getId());
        if(remainingMembers.isEmpty())
            this.teamDao.deleteById(teamMember.getTeam().getId());
    }
}

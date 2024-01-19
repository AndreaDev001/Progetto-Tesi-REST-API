package com.progettotirocinio.restapi.services.implementations;

import com.progettotirocinio.restapi.config.mapper.Mapper;
import com.progettotirocinio.restapi.data.dao.TeamDao;
import com.progettotirocinio.restapi.data.dao.TeamMemberDao;
import com.progettotirocinio.restapi.data.dao.UserDao;
import com.progettotirocinio.restapi.data.dto.output.TeamMemberDto;
import com.progettotirocinio.restapi.data.entities.Team;
import com.progettotirocinio.restapi.data.entities.TeamMember;
import com.progettotirocinio.restapi.data.entities.User;
import com.progettotirocinio.restapi.services.interfaces.TeamMemberService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TeamMemberServiceImp extends GenericServiceImp<TeamMember, TeamMemberDto> implements TeamMemberService {

    private final TeamMemberDao teamMemberDao;
    private final TeamDao teamDao;

    public TeamMemberServiceImp(Mapper mapper,TeamDao teamDao, UserDao userDao, TeamMemberDao teamMemberDao, PagedResourcesAssembler<TeamMember> pagedResourcesAssembler) {
        super(userDao,mapper, TeamMember.class,TeamMemberDto.class, pagedResourcesAssembler);
        this.teamDao = teamDao;
        this.teamMemberDao = teamMemberDao;
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
    public PagedModel<TeamMemberDto> getTeamMembersByTeam(UUID teamID, Pageable pageable) {
        Page<TeamMember> teamMembers = this.teamMemberDao.getTeamMembersByTeam(teamID,pageable);
        return this.pagedResourcesAssembler.toModel(teamMembers,modelAssembler);
    }

    @Override
    public TeamMemberDto getTeamMember(UUID memberID) {
        TeamMember teamMember = this.teamMemberDao.findById(memberID).orElseThrow();
        return this.modelMapper.map(teamMember,TeamMemberDto.class);
    }

    @Override
    public TeamMemberDto createTeamMember(UUID teamID) {
        Team requiredTeam = this.teamDao.findById(teamID).orElseThrow();
        User requiredUser = this.userDao.findById(UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName())).orElseThrow();
        TeamMember teamMember = new TeamMember();
        teamMember.setMember(requiredUser);
        teamMember.setTeam(requiredTeam);
        teamMember = this.teamMemberDao.save(teamMember);
        return this.modelMapper.map(teamMember,TeamMemberDto.class);
    }

    @Override
    public void deleteTeamMember(UUID memberID) {
        this.teamMemberDao.findById(memberID).orElseThrow();
        this.teamMemberDao.deleteById(memberID);
    }
}

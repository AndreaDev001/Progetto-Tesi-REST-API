package com.progettotirocinio.restapi.services.implementations;

import com.progettotirocinio.restapi.data.dao.TeamMemberDao;
import com.progettotirocinio.restapi.data.dto.output.TeamMemberDto;
import com.progettotirocinio.restapi.data.entities.TeamMember;
import com.progettotirocinio.restapi.services.interfaces.TeamMemberService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TeamMemberServiceImp extends GenericServiceImp<TeamMember, TeamMemberDto> implements TeamMemberService {

    private final TeamMemberDao teamMemberDao;
    public TeamMemberServiceImp(ModelMapper modelMapper,TeamMemberDao teamMemberDao, PagedResourcesAssembler<TeamMember> pagedResourcesAssembler) {
        super(modelMapper, TeamMember.class,TeamMemberDto.class, pagedResourcesAssembler);
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
    public void deleteTeamMember(UUID memberID) {
        this.teamMemberDao.findById(memberID).orElseThrow();
        this.teamMemberDao.deleteById(memberID);
    }
}

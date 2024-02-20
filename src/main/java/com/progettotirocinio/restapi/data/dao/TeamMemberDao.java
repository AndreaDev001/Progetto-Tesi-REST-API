package com.progettotirocinio.restapi.data.dao;


import com.progettotirocinio.restapi.data.entities.TeamMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

@Repository
public interface TeamMemberDao  extends JpaRepository<TeamMember, UUID>
{
    @Query("select m from TeamMember m where m.member.id = :requiredID")
    Page<TeamMember> getTeamMemberByMember(@Param("requiredID") UUID memberID, Pageable pageable);
    @Query("select m from TeamMember m where m.team.id = :requiredID")
    List<TeamMember> getTeamMembersByTeam(@Param("requiredID") UUID teamID);
    @Query("select m from TeamMember m where m.member.id = :requiredMemberID and m.team.id = :requiredTeamID")
    Optional<TeamMember> getTeamMember(@Param("requiredMemberID") UUID memberID,@Param("requiredTeamID") UUID teamID);
}

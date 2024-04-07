package com.progettotirocinio.restapi.data.dao;


import com.progettotirocinio.restapi.data.entities.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TeamDao extends JpaRepository<Team, UUID>
{
    @Query("select t from Team t where t.publisher.id = :requiredPublisherID")
    Page<Team> getTeamsByPublisher(@Param("requiredPublisherID") UUID publisherID, Pageable pageable);
    @Query("select t from Team t where t.name = :requiredName")
    Page<Team> getTeamsByName(@Param("requiredName") String name,Pageable pageable);
    @Query("select t from Team t where t.board.id = :requiredBoardID")
    List<Team> getTeamsByBoard(@Param("requiredBoardID") UUID boardID);
}

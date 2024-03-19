package com.progettotirocinio.restapi.data.dao;

import com.progettotirocinio.restapi.data.entities.polls.PollOption;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.List;

@Repository
public interface PollOptionDao extends JpaRepository<PollOption, UUID>
{
    @Query("select p from PollOption p where p.poll.id = :requiredID")
    List<PollOption> getPollOptionsByPoll(@Param("requiredID") UUID pollID);
    @Query("select p from PollOption p where p.name = :requiredName")
    Page<PollOption> getPollOptionsByName(@Param("requiredName") String name,Pageable pageable);
    @Query("select p from PollOption p where p.description = :requiredDescription")
    Page<PollOption> getPollOptionsByDescription(@Param("requiredDescription") String description,Pageable pageable);
}

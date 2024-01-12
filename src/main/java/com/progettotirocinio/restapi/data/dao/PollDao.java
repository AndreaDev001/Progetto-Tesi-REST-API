package com.progettotirocinio.restapi.data.dao;


import com.progettotirocinio.restapi.data.entities.Poll;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PollDao extends JpaRepository<Poll, UUID> {
    @Query("select p from Poll p where p.publisher.id = :requiredID")
    Page<Poll> getPollsByPublisher(@Param("requiredID") UUID publisherID, Pageable pageable);
    @Query("select p from Poll p where p.title = :requiredTitle")
    Page<Poll> getPollsByTitle(@Param("requiredTitle") String title,Pageable pageable);
    @Query("select p from Poll p where p.description = :requiredDescription")
    Page<Poll> getPollsByDescription(@Param("requiredDescription") String description,Pageable pageable);
    @Query("select p from Poll p where p.minimumVotes = :requiredMinimumVotes")
    Page<Poll> getPollsByMinimumVotes(@Param("requiredMinimumVotes") Integer minimumVotes,Pageable pageable);
    @Query("select p from Poll p where p.maximumVotes = :requiredMaximumVotes")
    Page<Poll> getPollsByMaximumVotes(@Param("requiredMaximumVotes") Integer maximumVotes,Pageable pageable);
}

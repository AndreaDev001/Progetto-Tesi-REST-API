package com.progettotirocinio.restapi.data.dao;


import com.progettotirocinio.restapi.data.entities.polls.Poll;
import com.progettotirocinio.restapi.data.entities.enums.PollStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

@Repository
public interface PollDao extends JpaRepository<Poll, UUID>, JpaSpecificationExecutor<Poll> {
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
    @Query("select p from Poll p where p.status = :requiredStatus")
    Page<Poll> getPollsByStatus(@Param("requiredStatus")PollStatus status,Pageable pageable);
    @Query("select p from Poll p where p.status = :requiredStatus")
    List<Poll> getPollsByStatus(@Param("requiredStatus") PollStatus status);
    @Query("select p from Poll p where p.expirationDate > :requiredDate")
    List<Poll> getPollsByDate(@Param("requiredDate")LocalDate date);
}

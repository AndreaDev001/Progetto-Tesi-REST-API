package com.progettotirocinio.restapi.data.dao;


import com.progettotirocinio.restapi.data.entities.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BoardDao extends JpaRepository<Board, UUID> {
    @Query("select b from Board b where b.publisher.id = :requiredID")
    Page<Board> getBoardsByPublisher(@Param("requiredID") UUID publisherID, Pageable pageable);
    @Query("select b from Board b where b.title = :requiredTitle")
    Page<Board> getBoardsByTitle(@Param("requiredTitle") String title,Pageable pageable);
    @Query("select b from Board b where b.description = :requiredDescription")
    Page<Board> getBoardsByDescription(@Param("requiredDescription") String description,Pageable pageable);
}

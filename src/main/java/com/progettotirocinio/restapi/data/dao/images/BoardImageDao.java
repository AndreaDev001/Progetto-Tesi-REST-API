package com.progettotirocinio.restapi.data.dao.images;

import com.progettotirocinio.restapi.data.dto.output.BoardDto;
import com.progettotirocinio.restapi.data.dto.output.images.BoardImageDto;
import com.progettotirocinio.restapi.data.entities.Board;
import com.progettotirocinio.restapi.data.entities.images.BoardImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BoardImageDao extends JpaRepository<BoardImage, UUID> {
    @Query("select b from BoardImage b where b.board.id = :requiredID")
    Optional<BoardImage> getBoardImageByBoard(@Param("requiredID") UUID boardID);
}

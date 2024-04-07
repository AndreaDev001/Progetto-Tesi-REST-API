package com.progettotirocinio.restapi.data.dao.tags;

import com.progettotirocinio.restapi.data.entities.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TagDao extends JpaRepository<Tag, UUID>
{
    @Query("select t from Tag t where t.publisher.id = :requiredPublisherID")
    Page<Tag> getTagsByPublisher(@Param("requiredPublisherID") UUID publisherID, Pageable pageable);
    @Query("select t from Tag t where t.name = :requiredName")
    Page<Tag> getTagsByName(@Param("requiredName") String name,Pageable pageable);
    @Query("select t from Tag t where t.color = :requiredColor")
    Page<Tag> getTagsByColor(@Param("requiredColor") String color,Pageable pageable);
    @Query("select t from Tag t where t.board.id = :requiredID")
    List<Tag> getTagsByBoard(@Param("requiredID") UUID requiredID);
}

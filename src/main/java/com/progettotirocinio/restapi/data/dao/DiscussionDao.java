package com.progettotirocinio.restapi.data.dao;


import com.progettotirocinio.restapi.data.entities.Discussion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DiscussionDao extends JpaRepository<Discussion, UUID>, JpaSpecificationExecutor<Discussion>
{
    @Query("select d from Discussion d where d.publisher.id = :requiredPublisherID")
    Page<Discussion> getDiscussionByPublisher(@Param("requiredPublisherID") UUID publisherID,Pageable pageable);
    @Query("select d from Discussion d where d.text = :requiredText")
    Page<Discussion> getDiscussionsByText(@Param("requiredText") String text,Pageable pageable);
    @Query("select d from Discussion d where d.topic = :requiredTopic")
    Page<Discussion> getDiscussionsByTopic(@Param("requiredTopic") String topic,Pageable pageable);
    @Query("select d from Discussion d where d.title = :requiredTitle")
    Page<Discussion> getDiscussionsByTitle(@Param("requiredTitle") String title,Pageable pageable);
}

package com.progettotirocinio.restapi.data.dao;

import com.progettotirocinio.restapi.data.entities.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface RoleDao extends JpaRepository<Role, UUID> {

    @Query("select r from Role r where r.publisher.id = :requiredPublisherID")
    Page<Role> getRolesByPublisher(@Param("requiredPublisherID") UUID publisherID, Pageable pageable);
    @Query("select r from Role r where r.name = :requiredName")
    Page<Role> getRolesByName(@Param("requiredName") String name,Pageable pageable);
    @Query("select t from Role r where r.name = :requiredName and r.board.id = :requiredID")
    Optional<Role> getRoleByNameAndBoard(@Param("requiredName") String name,@Param("requiredID") UUID id);
    @Query("select r from Role r where r.board.id = :requiredID")
    Page<Role> getRolesByBoard(@Param("requiredID") UUID requiredID,Pageable pageable);
}

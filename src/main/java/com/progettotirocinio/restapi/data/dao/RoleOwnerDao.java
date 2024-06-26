package com.progettotirocinio.restapi.data.dao;

import com.progettotirocinio.restapi.data.entities.Role;
import com.progettotirocinio.restapi.data.entities.RoleOwner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface RoleOwnerDao extends JpaRepository<RoleOwner, UUID> {
    @Query("select r from RoleOwner r where r.owner.id = :requiredID")
    Page<RoleOwner> getOwnerByUser(@Param("requiredID") UUID ownerID, Pageable pageable);
    @Query("select r from RoleOwner r where r.role.id = :requiredID")
    Page<RoleOwner> getOwnerByRole(@Param("requiredID") UUID roleID,Pageable pageable);
    @Query("select r from RoleOwner r where r.role.id = :requiredRoleID and r.owner.id = :requiredOwnerID")
    Optional<RoleOwner> getOwner(@Param("requiredRoleID") UUID roleID, @Param("requiredOwnerID") UUID ownerID);
    @Query("select r from RoleOwner r where r.owner.id = :requiredUserID and r.role.board.id = :requiredBoardID and r.role.name = :requiredName")
    Optional<RoleOwner> getOwnerByNameAndBoardAndUser(@Param("requiredUserID") UUID userID,@Param("requiredBoardID") UUID boardID,@Param("requiredName") String name);
    @Query("select r from RoleOwner r where r.role.name = :requiredName and r.role.board.id = :requiredBoardID and r.owner.id = :requiredOwnerID")
    Optional<RoleOwner> hasRole(@Param("requiredName") String name,@Param("requiredBoardID") UUID boardID,@Param("requiredOwnerID") UUID ownerID);
    @Query("select r from RoleOwner r where r.owner.id = :requiredUserID and r.role.board.id = :requiredRoleID")
    List<RoleOwner> getRoleOwnersByUserAndBoard(@Param("requiredUserID") UUID userID, @Param("requiredRoleID") UUID boardID);
}

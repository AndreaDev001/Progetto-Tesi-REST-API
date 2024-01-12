package com.progettotirocinio.restapi.data.dao;


import com.progettotirocinio.restapi.data.entities.Permission;
import com.progettotirocinio.restapi.data.entities.Role;
import com.progettotirocinio.restapi.data.entities.enums.PermissionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@Repository
public interface PermissionDao extends JpaRepository<Permission, UUID>
{
    @Query("select p from Permission p where p.role.id = :requiredID")
    Page<Permission> getPermissionsByRole(@Param("requiredID") UUID roleID, Pageable pageable);
    @Query("select p from Permission p where p.name = :requiredName")
    Page<Permission> getPermissionsByName(@Param("requiredName") String name,Pageable pageable);
    @Query("select p from Permission p where p.type = :requiredType")
    Page<Permission> getPermissionsByType(@Param("requiredType") PermissionType type,Pageable pageable);
}

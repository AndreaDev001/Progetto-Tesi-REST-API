package com.progettotirocinio.restapi.services.interfaces;

import com.progettotirocinio.restapi.data.dto.input.create.CreateRoleOwnerDto;
import com.progettotirocinio.restapi.data.dto.output.RoleDto;
import com.progettotirocinio.restapi.data.dto.output.RoleOwnerDto;
import com.progettotirocinio.restapi.data.entities.RoleOwner;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import java.util.UUID;

public interface RoleOwnerService {
    PagedModel<RoleOwnerDto> getOwners(Pageable pageable);
    PagedModel<RoleOwnerDto> getOwnersByUser(UUID userID,Pageable pageable);
    PagedModel<RoleOwnerDto> getOwnersByRole(UUID roleID,Pageable pageable);
    RoleOwnerDto hasRole(UUID ownerID,UUID boardID,String name);
    RoleOwnerDto getById(UUID id);
    RoleOwnerDto createRoleOwner(UUID roleID);
    RoleOwnerDto createRoleOwner(CreateRoleOwnerDto createRoleOwnerDto);
    void deleteOwner(UUID id);
    void deleteOwner(String name,UUID boardID,UUID userID);
}

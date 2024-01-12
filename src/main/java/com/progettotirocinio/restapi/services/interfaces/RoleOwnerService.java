package com.progettotirocinio.restapi.services.interfaces;

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
    RoleOwnerDto getById(UUID id);
    void deleteOwner(UUID id);
}

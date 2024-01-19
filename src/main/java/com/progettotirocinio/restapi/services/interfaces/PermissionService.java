package com.progettotirocinio.restapi.services.interfaces;

import com.progettotirocinio.restapi.data.dto.input.create.CreatePermissionDto;
import com.progettotirocinio.restapi.data.dto.output.PermissionDto;
import com.progettotirocinio.restapi.data.entities.enums.PermissionType;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import java.util.UUID;

public interface PermissionService {
    PagedModel<PermissionDto> getPermissions(Pageable pageable);
    PagedModel<PermissionDto> getPermissionsByRole(UUID roleID,Pageable pageable);
    PagedModel<PermissionDto> getPermissionsByType(PermissionType type,Pageable pageable);
    PagedModel<PermissionDto> getPermissionsByName(String name,Pageable pageable);
    PermissionDto getPermission(UUID id);
    PermissionDto createPermission(CreatePermissionDto createPermissionDto);
    void deletePermission(UUID id);
}

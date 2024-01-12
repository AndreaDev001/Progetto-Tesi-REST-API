package com.progettotirocinio.restapi.services.interfaces;

import com.progettotirocinio.restapi.data.dto.output.RoleDto;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import java.util.UUID;

public interface RoleService {
    PagedModel<RoleDto> getRoles(Pageable pageable);
    PagedModel<RoleDto> getRolesByPublisher(UUID publisherID,Pageable pageable);
    PagedModel<RoleDto> getRolesByName(String name,Pageable pageable);
    PagedModel<RoleDto> getRolesByBoard(UUID boardID,Pageable pageable);
    RoleDto getRole(UUID roleID);
    void deleteRole(UUID roleID);
}

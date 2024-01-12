package com.progettotirocinio.restapi.controllers;


import com.progettotirocinio.restapi.data.dto.input.PaginationRequest;
import com.progettotirocinio.restapi.data.dto.output.PermissionDto;
import com.progettotirocinio.restapi.data.dto.output.RoleDto;
import com.progettotirocinio.restapi.data.entities.Role;
import com.progettotirocinio.restapi.data.entities.enums.PermissionType;
import com.progettotirocinio.restapi.services.interfaces.PermissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
public class PermissionController
{
    private final PermissionService permissionService;

    @GetMapping("/private")
    public ResponseEntity<PagedModel<PermissionDto>> getPermissions(@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<PermissionDto> permissions = this.permissionService.getPermissions(paginationRequest.toPageRequest());
        return ResponseEntity.ok(permissions);
    }

    @GetMapping("/private/{permissionID}")
    public ResponseEntity<PermissionDto> getPermission(@PathVariable("permissionID") UUID permissionID) {
        PermissionDto permission = this.permissionService.getPermission(permissionID);
        return ResponseEntity.ok(permission);
    }


    @GetMapping("/private/role/{roleID}")
    public ResponseEntity<PagedModel<PermissionDto>> getPermissionsByRole(@PathVariable("roleID") UUID roleID, @ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<PermissionDto> permissions = this.permissionService.getPermissionsByRole(roleID,paginationRequest.toPageRequest());
        return ResponseEntity.ok(permissions);
    }

    @GetMapping("/private/name/{name}")
    public ResponseEntity<PagedModel<PermissionDto>> getPermissionsByName(@PathVariable("name") String name,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<PermissionDto> permissions = this.permissionService.getPermissionsByName(name,paginationRequest.toPageRequest());
        return ResponseEntity.ok(permissions);
    }

    @GetMapping("/private/type/{type}")
    public ResponseEntity<PagedModel<PermissionDto>> getPermissionsByType(@PathVariable("type")PermissionType type,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<PermissionDto> permissions = this.permissionService.getPermissionsByType(type,paginationRequest.toPageRequest());
        return ResponseEntity.ok(permissions);
    }

    @DeleteMapping("/private/{permissionID}")
    public ResponseEntity<PermissionDto> deletePermission(@PathVariable("permissionID") UUID permissionID) {
        this.permissionService.deletePermission(permissionID);
        return ResponseEntity.noContent().build();
    }
}

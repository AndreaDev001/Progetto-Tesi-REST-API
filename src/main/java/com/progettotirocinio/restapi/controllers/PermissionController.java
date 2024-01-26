package com.progettotirocinio.restapi.controllers;


import com.progettotirocinio.restapi.data.dto.input.PaginationRequest;
import com.progettotirocinio.restapi.data.dto.input.create.CreatePermissionDto;
import com.progettotirocinio.restapi.data.dto.input.update.UpdatePermissionDto;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
public class PermissionController
{
    private final PermissionService permissionService;

    @GetMapping("/private")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<PermissionDto>> getPermissions(@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<PermissionDto> permissions = this.permissionService.getPermissions(paginationRequest.toPageRequest());
        return ResponseEntity.ok(permissions);
    }

    @GetMapping("/private/{permissionID}")
    @PreAuthorize("@permissionHandler.hasAccess(@permissionDao,#permissionID)")
    public ResponseEntity<PermissionDto> getPermission(@PathVariable("permissionID") UUID permissionID) {
        PermissionDto permission = this.permissionService.getPermission(permissionID);
        return ResponseEntity.ok(permission);
    }


    @GetMapping("/private/role/{roleID}")
    @PreAuthorize("@permissionHandler.hasAccess(@roleDao,#roleID)")
    public ResponseEntity<PagedModel<PermissionDto>> getPermissionsByRole(@PathVariable("roleID") UUID roleID, @ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<PermissionDto> permissions = this.permissionService.getPermissionsByRole(roleID,paginationRequest.toPageRequest());
        return ResponseEntity.ok(permissions);
    }

    @PostMapping("/private")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_MEMBER')")
    public ResponseEntity<PermissionDto> createPermission(@RequestBody @Valid CreatePermissionDto createPermissionDto) {
        PermissionDto permissionDto = this.permissionService.createPermission(createPermissionDto);
        return ResponseEntity.status(201).body(permissionDto);
    }

    @PutMapping("/private")
    @PreAuthorize("@permissionHandler.hasAccess(@permissionDao,#updatePermissionDto.permissionID)")
    public ResponseEntity<PermissionDto> updatePermission(@RequestBody @Valid UpdatePermissionDto updatePermissionDto) {
        PermissionDto permissionDto = this.permissionService.updatePermission(updatePermissionDto);
        return ResponseEntity.ok(permissionDto);
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

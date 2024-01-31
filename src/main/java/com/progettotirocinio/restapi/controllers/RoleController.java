package com.progettotirocinio.restapi.controllers;


import com.progettotirocinio.restapi.data.dto.input.PaginationRequest;
import com.progettotirocinio.restapi.data.dto.input.create.CreateRoleDto;
import com.progettotirocinio.restapi.data.dto.input.update.UpdateRoleDto;
import com.progettotirocinio.restapi.data.dto.output.RoleDto;
import com.progettotirocinio.restapi.services.interfaces.RoleService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/roles")
@SecurityRequirement(name = "Authorization")
public class RoleController
{
    private final RoleService roleService;

    @GetMapping("/private")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<RoleDto>> getRoles(@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<RoleDto> roles = this.roleService.getRoles(paginationRequest.toPageRequest());
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/private/{roleID}")
    @PreAuthorize("@permissionHandler.hasRole(@roleDao,#roleID)")
    public ResponseEntity<RoleDto> getRole(@PathVariable("roleID") UUID roleID) {
        RoleDto role = this.roleService.getRole(roleID);
        return ResponseEntity.ok(role);
    }

    @PostMapping("/private")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_MEMBER')")
    public ResponseEntity<RoleDto> createRole(@RequestBody @Valid CreateRoleDto createRoleDto) {
        RoleDto roleDto = this.roleService.createRole(createRoleDto);
        return ResponseEntity.status(201).body(roleDto);
    }

    @PutMapping("/private")
    @PreAuthorize("@permissionHandler.hasAccess(@roleDao,#updateRoleDto.roleID)")
    public ResponseEntity<RoleDto> updateRole(@RequestBody @Valid UpdateRoleDto updateRoleDto) {
        RoleDto roleDto = this.roleService.updateRole(updateRoleDto);
        return ResponseEntity.ok(roleDto);
    }

    @GetMapping("/private/board/{boardID}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_MEMBER')")
    public ResponseEntity<PagedModel<RoleDto>> getRolesByBoard(@PathVariable("boardID") UUID boardID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<RoleDto> roles = this.roleService.getRolesByBoard(boardID,paginationRequest.toPageRequest());
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/private/publisher/{publisherID}")
    @PreAuthorize("@permissionHandler.hasAccess(#publisherID)")
    public ResponseEntity<PagedModel<RoleDto>> getRolesByPublisher(@PathVariable("publisherID") UUID publisherID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<RoleDto> roles = this.roleService.getRolesByPublisher(publisherID,paginationRequest.toPageRequest());
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/private/name/{name}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_MEMBER')")
    public ResponseEntity<PagedModel<RoleDto>> getRolesByName(@PathVariable("name") String name,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<RoleDto> roles = this.roleService.getRolesByName(name,paginationRequest.toPageRequest());
        return ResponseEntity.ok(roles);
    }
}

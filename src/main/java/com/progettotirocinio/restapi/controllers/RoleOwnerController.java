package com.progettotirocinio.restapi.controllers;


import com.progettotirocinio.restapi.data.dto.input.PaginationRequest;
import com.progettotirocinio.restapi.data.dto.output.RoleOwnerDto;
import com.progettotirocinio.restapi.services.interfaces.RoleOwnerService;
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
@SecurityRequirement(name = "Authorization")
@RequestMapping("/roleOwner")
public class RoleOwnerController
{
    private final RoleOwnerService roleOwnerService;

    @GetMapping("/private")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<RoleOwnerDto>> getOwners(@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<RoleOwnerDto> pagedModel = this.roleOwnerService.getOwners(paginationRequest.toPageRequest());
        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/private/{roleOwnerID}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<RoleOwnerDto> getOwner(@PathVariable("roleOwnerID")UUID roleOwnerID) {
        RoleOwnerDto roleOwner = this.roleOwnerService.getById(roleOwnerID);
        return ResponseEntity.ok(roleOwner);
    }

    @PostMapping("/private/{roleID}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_MEMBER')")
    public ResponseEntity<RoleOwnerDto> createRoleOwner(@PathVariable("roleID") UUID roleID) {
        RoleOwnerDto roleOwnerDto = this.roleOwnerService.createRoleOwner(roleID);
        return ResponseEntity.status(201).body(roleOwnerDto);
    }

    @GetMapping("/private/owner/{ownerID}")
    @PreAuthorize("@permissionHandler.hasAccess(#ownerID)")
    public ResponseEntity<PagedModel<RoleOwnerDto>> getRoleOwnersByOwner(@PathVariable("ownerID") UUID ownerID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<RoleOwnerDto> roleOwners = this.roleOwnerService.getOwnersByUser(ownerID,paginationRequest.toPageRequest());
        return ResponseEntity.ok(roleOwners);
    }

    @GetMapping("/private/role/{roleID}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<RoleOwnerDto>> getRoleOwnersByRole(@PathVariable("roleID") UUID roleID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<RoleOwnerDto> roleOwners = this.roleOwnerService.getOwnersByRole(roleID,paginationRequest.toPageRequest());
        return ResponseEntity.ok(roleOwners);
    }

    @DeleteMapping("/private/{roleOwnerID}")
    @PreAuthorize("permissionHandler.hasAccess(@roleOwnerDao,#roleOwnerID)")
    public ResponseEntity<Void> deleteRoleOwner(@PathVariable("roleOwnerID") UUID roleOwnerID) {
        this.roleOwnerService.deleteOwner(roleOwnerID);
        return ResponseEntity.noContent().build();
    }
}

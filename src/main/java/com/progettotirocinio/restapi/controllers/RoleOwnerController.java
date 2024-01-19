package com.progettotirocinio.restapi.controllers;


import com.progettotirocinio.restapi.data.dto.input.PaginationRequest;
import com.progettotirocinio.restapi.data.dto.output.RoleOwnerDto;
import com.progettotirocinio.restapi.services.interfaces.RoleOwnerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/roleOwner")
public class RoleOwnerController
{
    private final RoleOwnerService roleOwnerService;

    @GetMapping("/private")
    public ResponseEntity<PagedModel<RoleOwnerDto>> getOwners(@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<RoleOwnerDto> pagedModel = this.roleOwnerService.getOwners(paginationRequest.toPageRequest());
        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/private/{roleOwnerID}")
    public ResponseEntity<RoleOwnerDto> getOwner(@PathVariable("roleOwnerID")UUID roleOwnerID) {
        RoleOwnerDto roleOwner = this.roleOwnerService.getById(roleOwnerID);
        return ResponseEntity.ok(roleOwner);
    }

    @PostMapping("/private/{roleID}")
    public ResponseEntity<RoleOwnerDto> createRoleOwner(@PathVariable("roleID") UUID roleID) {
        RoleOwnerDto roleOwnerDto = this.roleOwnerService.createRoleOwner(roleID);
        return ResponseEntity.status(201).body(roleOwnerDto);
    }

    @GetMapping("/private/owner/{ownerID}")
    public ResponseEntity<PagedModel<RoleOwnerDto>> getRoleOwnersByOwner(@PathVariable("ownerID") UUID ownerID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<RoleOwnerDto> roleOwners = this.roleOwnerService.getOwnersByUser(ownerID,paginationRequest.toPageRequest());
        return ResponseEntity.ok(roleOwners);
    }

    @GetMapping("/private/role/{roleID}")
    public ResponseEntity<PagedModel<RoleOwnerDto>> getRoleOwnersByRole(@PathVariable("roleID") UUID roleID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<RoleOwnerDto> roleOwners = this.roleOwnerService.getOwnersByRole(roleID,paginationRequest.toPageRequest());
        return ResponseEntity.ok(roleOwners);
    }

    @DeleteMapping("/private/{roleOwnerID}")
    public ResponseEntity<Void> deleteRoleOwner(@PathVariable("roleOwnerID") UUID roleOwnerID) {
        this.roleOwnerService.deleteOwner(roleOwnerID);
        return ResponseEntity.noContent().build();
    }
}

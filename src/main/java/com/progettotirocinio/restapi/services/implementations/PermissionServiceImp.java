package com.progettotirocinio.restapi.services.implementations;


import com.progettotirocinio.restapi.data.dao.PermissionDao;
import com.progettotirocinio.restapi.data.dto.output.PermissionDto;
import com.progettotirocinio.restapi.data.entities.Permission;
import com.progettotirocinio.restapi.data.entities.enums.PermissionType;
import com.progettotirocinio.restapi.services.interfaces.PermissionService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PermissionServiceImp extends GenericServiceImp<Permission, PermissionDto> implements PermissionService
{
    private final PermissionDao permissionDao;

    public PermissionServiceImp(ModelMapper modelMapper,PermissionDao permissionDao, PagedResourcesAssembler<Permission> pagedResourcesAssembler) {
        super(modelMapper,Permission.class,PermissionDto.class, pagedResourcesAssembler);
        this.permissionDao = permissionDao;
    }

    @Override
    public PagedModel<PermissionDto> getPermissions(Pageable pageable) {
        Page<Permission> permissions = this.permissionDao.findAll(pageable);
        return this.pagedResourcesAssembler.toModel(permissions,modelAssembler);
    }

    @Override
    public PagedModel<PermissionDto> getPermissionsByRole(UUID roleID, Pageable pageable) {
        Page<Permission> permissions = this.permissionDao.getPermissionsByRole(roleID,pageable);
        return this.pagedResourcesAssembler.toModel(permissions,modelAssembler);
    }

    @Override
    public PagedModel<PermissionDto> getPermissionsByType(PermissionType type, Pageable pageable) {
        Page<Permission> permissions = this.permissionDao.getPermissionsByType(type,pageable);
        return this.pagedResourcesAssembler.toModel(permissions,modelAssembler);
    }

    @Override
    public PagedModel<PermissionDto> getPermissionsByName(String name, Pageable pageable) {
        Page<Permission> permissions = this.permissionDao.getPermissionsByName(name,pageable);
        return this.pagedResourcesAssembler.toModel(permissions,modelAssembler);
    }

    @Override
    public PermissionDto getPermission(UUID id) {
        Permission permission = this.permissionDao.findById(id).orElseThrow();
        return this.modelMapper.map(permission,PermissionDto.class);
    }

    @Override
    public void deletePermission(UUID id) {
        this.permissionDao.findById(id).orElseThrow();
        this.permissionDao.deleteById(id);
    }
}

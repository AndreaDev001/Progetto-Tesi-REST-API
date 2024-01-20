package com.progettotirocinio.restapi.services.implementations;


import com.progettotirocinio.restapi.config.mapper.Mapper;
import com.progettotirocinio.restapi.data.dao.PermissionDao;
import com.progettotirocinio.restapi.data.dao.RoleDao;
import com.progettotirocinio.restapi.data.dao.UserDao;
import com.progettotirocinio.restapi.data.dto.input.create.CreatePermissionDto;
import com.progettotirocinio.restapi.data.dto.input.update.UpdatePermissionDto;
import com.progettotirocinio.restapi.data.dto.output.PermissionDto;
import com.progettotirocinio.restapi.data.entities.Permission;
import com.progettotirocinio.restapi.data.entities.Role;
import com.progettotirocinio.restapi.data.entities.User;
import com.progettotirocinio.restapi.data.entities.enums.PermissionType;
import com.progettotirocinio.restapi.services.interfaces.PermissionService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PermissionServiceImp extends GenericServiceImp<Permission, PermissionDto> implements PermissionService
{
    private final PermissionDao permissionDao;
    private final RoleDao roleDao;

    public PermissionServiceImp(UserDao userDao,RoleDao roleDao,Mapper mapper, PermissionDao permissionDao, PagedResourcesAssembler<Permission> pagedResourcesAssembler) {
        super(userDao,mapper,Permission.class,PermissionDto.class, pagedResourcesAssembler);
        this.permissionDao = permissionDao;
        this.roleDao = roleDao;
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
    @Transactional
    public PermissionDto createPermission(CreatePermissionDto createPermissionDto) {
        Role role = this.roleDao.findById(createPermissionDto.getRoleID()).orElseThrow();
        Permission permission = new Permission();
        permission.setRole(role);
        permission.setName(createPermissionDto.getName());
        permission.setType(createPermissionDto.getType());
        permission = this.permissionDao.save(permission);
        return this.modelMapper.map(permission,PermissionDto.class);
    }

    @Override
    @Transactional
    public PermissionDto updatePermission(UpdatePermissionDto updatePermission) {
        Permission permission = this.permissionDao.findById(updatePermission.getPermissionID()).orElseThrow();
        if(updatePermission.getName() != null)
            permission.setName(updatePermission.getName());
        if(updatePermission.getType() != null)
            permission.setType(updatePermission.getType());
        permission = this.permissionDao.save(permission);
        return this.modelMapper.map(permission,PermissionDto.class);
    }

    @Override
    @Transactional
    public void deletePermission(UUID id) {
        this.permissionDao.findById(id).orElseThrow();
        this.permissionDao.deleteById(id);
    }
}

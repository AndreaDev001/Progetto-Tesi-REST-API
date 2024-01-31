package com.progettotirocinio.restapi.services.implementations;


import com.progettotirocinio.restapi.config.caching.CacheHandler;
import com.progettotirocinio.restapi.config.caching.RequiresCaching;
import com.progettotirocinio.restapi.config.mapper.Mapper;
import com.progettotirocinio.restapi.data.dao.BoardDao;
import com.progettotirocinio.restapi.data.dao.RoleDao;
import com.progettotirocinio.restapi.data.dao.RoleOwnerDao;
import com.progettotirocinio.restapi.data.dao.UserDao;
import com.progettotirocinio.restapi.data.dto.output.RoleOwnerDto;
import com.progettotirocinio.restapi.data.entities.Role;
import com.progettotirocinio.restapi.data.entities.RoleOwner;
import com.progettotirocinio.restapi.data.entities.User;
import com.progettotirocinio.restapi.services.interfaces.RoleOwnerService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiresCaching(allCacheName = "ALL_ROLE_OWNERS")
public class RoleOwnerServiceImp extends GenericServiceImp<RoleOwner, RoleOwnerDto> implements RoleOwnerService
{
    private final RoleOwnerDao roleOwnerDao;
    private final RoleDao roleDao;

    public RoleOwnerServiceImp(CacheHandler cacheHandler,RoleDao roleDao, UserDao userDao, Mapper mapper, RoleOwnerDao roleOwnerDao, PagedResourcesAssembler<RoleOwner> pagedResourcesAssembler) {
        super(cacheHandler,userDao,mapper,RoleOwner.class,RoleOwnerDto.class, pagedResourcesAssembler);
        this.roleOwnerDao = roleOwnerDao;
        this.roleDao = roleDao;
    }

    @Override
    public PagedModel<RoleOwnerDto> getOwners(Pageable pageable) {
        Page<RoleOwner> owners = this.roleOwnerDao.findAll(pageable);
        return this.pagedResourcesAssembler.toModel(owners,modelAssembler);
    }

    @Override
    public PagedModel<RoleOwnerDto> getOwnersByUser(UUID userID, Pageable pageable) {
        Page<RoleOwner> roleOwners = this.roleOwnerDao.getOwnerByUser(userID,pageable);
        return this.pagedResourcesAssembler.toModel(roleOwners,modelAssembler);
    }

    @Override
    public PagedModel<RoleOwnerDto> getOwnersByRole(UUID roleID, Pageable pageable) {
        Page<RoleOwner> roleOwners = this.roleOwnerDao.getOwnerByRole(roleID,pageable);
        return this.pagedResourcesAssembler.toModel(roleOwners,modelAssembler);
    }

    @Override
    public RoleOwnerDto getById(UUID id) {
        RoleOwner roleOwner = this.roleOwnerDao.findById(id).orElseThrow();
        return this.modelMapper.map(roleOwner,RoleOwnerDto.class);
    }

    @Override
    public RoleOwnerDto createRoleOwner(UUID roleID) {
        User user = this.userDao.findById(UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName())).orElseThrow();
        Role role = this.roleDao.findById(roleID).orElseThrow();
        RoleOwner roleOwner = new RoleOwner();
        roleOwner.setOwner(user);
        roleOwner.setRole(role);
        roleOwner = this.roleOwnerDao.save(roleOwner);
        return this.modelMapper.map(roleOwner,RoleOwnerDto.class);
    }

    @Override
    public void deleteOwner(UUID id) {
        this.roleOwnerDao.findById(id).orElseThrow();
        this.roleOwnerDao.deleteById(id);
    }
}

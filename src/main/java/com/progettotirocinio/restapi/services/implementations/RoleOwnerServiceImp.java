package com.progettotirocinio.restapi.services.implementations;


import com.progettotirocinio.restapi.config.caching.CacheHandler;
import com.progettotirocinio.restapi.config.caching.RequiresCaching;
import com.progettotirocinio.restapi.config.exceptions.InvalidFormat;
import com.progettotirocinio.restapi.config.mapper.Mapper;
import com.progettotirocinio.restapi.data.dao.*;
import com.progettotirocinio.restapi.data.dto.input.create.CreateRoleOwnerDto;
import com.progettotirocinio.restapi.data.dto.output.RoleOwnerDto;
import com.progettotirocinio.restapi.data.entities.BoardMember;
import com.progettotirocinio.restapi.data.entities.Role;
import com.progettotirocinio.restapi.data.entities.RoleOwner;
import com.progettotirocinio.restapi.data.entities.User;
import com.progettotirocinio.restapi.services.interfaces.RoleOwnerService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.Optional;

@Service
@RequiresCaching(allCacheName = "ALL_ROLE_OWNERS")
public class RoleOwnerServiceImp extends GenericServiceImp<RoleOwner, RoleOwnerDto> implements RoleOwnerService
{
    private final RoleOwnerDao roleOwnerDao;
    private final BoardMemberDao boardMemberDao;
    private final RoleDao roleDao;

    public RoleOwnerServiceImp(CacheHandler cacheHandler,RoleDao roleDao,BoardMemberDao boardMemberDao, UserDao userDao, Mapper mapper, RoleOwnerDao roleOwnerDao, PagedResourcesAssembler<RoleOwner> pagedResourcesAssembler) {
        super(cacheHandler,userDao,mapper,RoleOwner.class,RoleOwnerDto.class, pagedResourcesAssembler);
        this.roleOwnerDao = roleOwnerDao;
        this.boardMemberDao = boardMemberDao;
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
    public RoleOwnerDto hasRole(UUID ownerID,UUID boardID, String name) {
        RoleOwner roleOwner = this.roleOwnerDao.hasRole(name,boardID,ownerID).orElseThrow();
        return this.modelMapper.map(roleOwner,RoleOwnerDto.class);
    }

    @Override
    public RoleOwnerDto getById(UUID id) {
        RoleOwner roleOwner = this.roleOwnerDao.findById(id).orElseThrow();
        return this.modelMapper.map(roleOwner,RoleOwnerDto.class);
    }

    @Override
    @Transactional
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
    @Transactional
    public RoleOwnerDto createRoleOwner(CreateRoleOwnerDto createRoleOwnerDto) {
        User requiredUser = this.userDao.findById(createRoleOwnerDto.getUserID()).orElseThrow();
        Role requiredRole = this.roleDao.getRoleByNameAndBoard(createRoleOwnerDto.getName(),createRoleOwnerDto.getBoardID()).orElseThrow();
        Optional<BoardMember> boardMemberOptional = this.boardMemberDao.getBoardMember(createRoleOwnerDto.getBoardID(),createRoleOwnerDto.getUserID());
        if(boardMemberOptional.isEmpty())
            throw new InvalidFormat("error.roleOwner.invalidUser");
        RoleOwner roleOwner = new RoleOwner();
        roleOwner.setOwner(requiredUser);
        roleOwner.setRole(requiredRole);
        roleOwner = this.roleOwnerDao.save(roleOwner);
        return this.modelMapper.map(roleOwner,RoleOwnerDto.class);
    }

    @Override
    @Transactional
    public void deleteOwner(UUID id) {
        this.roleOwnerDao.findById(id).orElseThrow();
        this.roleOwnerDao.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteOwner(String name, UUID boardID, UUID userID) {
        RoleOwner roleOwner = this.roleOwnerDao.getOwnerByNameAndBoardAndUser(userID,boardID,name).orElseThrow();
        this.roleOwnerDao.deleteById(roleOwner.getId());
    }
}

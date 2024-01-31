package com.progettotirocinio.restapi.services.implementations;


import com.progettotirocinio.restapi.config.caching.CacheHandler;
import com.progettotirocinio.restapi.config.caching.RequiresCaching;
import com.progettotirocinio.restapi.config.mapper.Mapper;
import com.progettotirocinio.restapi.data.dao.BoardDao;
import com.progettotirocinio.restapi.data.dao.RoleDao;
import com.progettotirocinio.restapi.data.dao.UserDao;
import com.progettotirocinio.restapi.data.dto.input.create.CreateRoleDto;
import com.progettotirocinio.restapi.data.dto.input.update.UpdateRoleDto;
import com.progettotirocinio.restapi.data.dto.output.RoleDto;
import com.progettotirocinio.restapi.data.entities.Board;
import com.progettotirocinio.restapi.data.entities.Role;
import com.progettotirocinio.restapi.data.entities.User;
import com.progettotirocinio.restapi.services.interfaces.RoleService;
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
@RequiresCaching(allCacheName = "ALL_ROLES")
public class RoleServiceImp extends GenericServiceImp<Role, RoleDto> implements RoleService
{
    private final RoleDao roleDao;
    private final BoardDao boardDao;

    public RoleServiceImp(CacheHandler cacheHandler,BoardDao boardDao, UserDao userDao, Mapper mapper, RoleDao roleDao, PagedResourcesAssembler<Role> pagedResourcesAssembler) {
        super(cacheHandler,userDao,mapper,Role.class,RoleDto.class, pagedResourcesAssembler);
        this.roleDao = roleDao;
        this.boardDao = boardDao;
    }

    @Override
    public PagedModel<RoleDto> getRoles(Pageable pageable) {
        Page<Role> roles = this.roleDao.findAll(pageable);
        return this.pagedResourcesAssembler.toModel(roles,modelAssembler);
    }

    @Override
    public PagedModel<RoleDto> getRolesByPublisher(UUID publisherID, Pageable pageable) {
        Page<Role> roles = this.roleDao.getRolesByPublisher(publisherID,pageable);
        return this.pagedResourcesAssembler.toModel(roles,modelAssembler);
    }

    @Override
    public PagedModel<RoleDto> getRolesByName(String name, Pageable pageable) {
        Page<Role> roles = this.roleDao.getRolesByName(name,pageable);
        return this.pagedResourcesAssembler.toModel(roles,modelAssembler);
    }

    @Override
    public PagedModel<RoleDto> getRolesByBoard(UUID boardID, Pageable pageable) {
        Page<Role> roles = this.roleDao.getRolesByBoard(boardID,pageable);
        return this.pagedResourcesAssembler.toModel(roles,modelAssembler);
    }

    @Override
    public RoleDto getRole(UUID roleID) {
        Role role = this.roleDao.findById(roleID).orElseThrow();
        return this.modelMapper.map(role,RoleDto.class);
    }

    @Override
    @Transactional
    public RoleDto createRole(CreateRoleDto createRoleDto) {
        User publisher = this.userDao.findById(UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName())).orElseThrow();
        Board board = this.boardDao.findById(createRoleDto.getBoardID()).orElseThrow();
        Role role = new Role();
        role.setName(createRoleDto.getName());
        role.setPublisher(publisher);
        role.setBoard(board);
        role = this.roleDao.save(role);
        return this.modelMapper.map(role,RoleDto.class);
    }

    @Override
    @Transactional
    public RoleDto updateRole(UpdateRoleDto updateRoleDto) {
        Role role = this.roleDao.findById(updateRoleDto.getRoleID()).orElseThrow();
        if(updateRoleDto.getName() != null)
            role.setName(updateRoleDto.getName());
        role = this.roleDao.save(role);
        return this.modelMapper.map(role,RoleDto.class);
    }

    @Override
    @Transactional
    public void deleteRole(UUID roleID) {
        this.roleDao.findById(roleID).orElseThrow();
        this.roleDao.deleteById(roleID);
    }
}

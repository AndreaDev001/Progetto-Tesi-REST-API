package com.progettotirocinio.restapi.services.implementations;


import com.progettotirocinio.restapi.data.dao.RoleOwnerDao;
import com.progettotirocinio.restapi.data.dto.output.RoleOwnerDto;
import com.progettotirocinio.restapi.data.entities.RoleOwner;
import com.progettotirocinio.restapi.services.interfaces.RoleOwnerService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RoleOwnerServiceImp extends GenericServiceImp<RoleOwner, RoleOwnerDto> implements RoleOwnerService
{
    private final RoleOwnerDao roleOwnerDao;

    public RoleOwnerServiceImp(ModelMapper modelMapper,RoleOwnerDao roleOwnerDao, PagedResourcesAssembler<RoleOwner> pagedResourcesAssembler) {
        super(modelMapper,RoleOwner.class,RoleOwnerDto.class, pagedResourcesAssembler);
        this.roleOwnerDao = roleOwnerDao;
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
    public void deleteOwner(UUID id) {
        this.roleOwnerDao.findById(id).orElseThrow();
        this.roleOwnerDao.deleteById(id);
    }
}

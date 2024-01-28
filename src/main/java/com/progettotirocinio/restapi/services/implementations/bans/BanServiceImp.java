package com.progettotirocinio.restapi.services.implementations.bans;

import com.progettotirocinio.restapi.config.mapper.Mapper;
import com.progettotirocinio.restapi.data.dao.UserDao;
import com.progettotirocinio.restapi.data.dao.bans.BanDao;
import com.progettotirocinio.restapi.data.dao.specifications.BanSpecifications;
import com.progettotirocinio.restapi.data.dao.specifications.SpecificationsUtils;
import com.progettotirocinio.restapi.data.dto.output.bans.BanDto;
import com.progettotirocinio.restapi.data.entities.bans.Ban;
import com.progettotirocinio.restapi.data.entities.enums.BanType;
import com.progettotirocinio.restapi.data.entities.enums.ReportReason;
import com.progettotirocinio.restapi.services.implementations.GenericServiceImp;
import com.progettotirocinio.restapi.services.interfaces.bans.BanService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.UUID;

@Service
public class BanServiceImp extends GenericServiceImp<Ban, BanDto> implements BanService
{
    private final BanDao banDao;

    public BanServiceImp(BanDao banDao,UserDao userDao, Mapper mapper,PagedResourcesAssembler<Ban> pagedResourcesAssembler) {
        super(userDao, mapper,Ban.class,BanDto.class, pagedResourcesAssembler);
        this.banDao = banDao;
    }

    @Override
    public PagedModel<BanDto> getBans(Pageable pageable) {
        Page<Ban> bans = this.banDao.findAll(pageable);
        return this.pagedResourcesAssembler.toModel(bans,modelAssembler);
    }

    @Override
    public PagedModel<BanDto> getBansBySpec(Specification<Ban> specification, Pageable pageable) {
        Page<Ban> bans = this.banDao.findAll(specification,pageable);
        return this.pagedResourcesAssembler.toModel(bans,modelAssembler);
    }

    @Override
    public PagedModel<BanDto> getSimilarBans(UUID banID, Pageable pageable) {
        Ban ban = this.banDao.findById(banID).orElseThrow();
        BanSpecifications.Filter filter = new BanSpecifications.Filter(ban);
        Page<Ban> bans = this.banDao.findAll(BanSpecifications.withFilter(filter),pageable);
        return this.pagedResourcesAssembler.toModel(bans,modelAssembler);
    }

    @Override
    public PagedModel<BanDto> getBansByBanner(UUID bannerID, Pageable pageable) {
        Page<Ban> bans = this.banDao.getBansByBanner(bannerID,pageable);
        return this.pagedResourcesAssembler.toModel(bans,modelAssembler);
    }

    @Override
    public PagedModel<BanDto> getBansByBanned(UUID bannedID, Pageable pageable) {
        Page<Ban> bans = this.banDao.getBansByBanned(bannedID,pageable);
        return this.pagedResourcesAssembler.toModel(bans,modelAssembler);
    }

    @Override
    public PagedModel<BanDto> getBansByType(BanType type, Pageable pageable) {
        Page<Ban> bans = this.banDao.getBansByType(type,pageable);
        return this.pagedResourcesAssembler.toModel(bans,modelAssembler);
    }

    @Override
    public PagedModel<BanDto> getBansByReason(ReportReason reason, Pageable pageable) {
        Page<Ban> bans = this.banDao.getBansByReason(reason,pageable);
        return this.pagedResourcesAssembler.toModel(bans,modelAssembler);
    }

    @Override
    public PagedModel<BanDto> getBansByExpired(Boolean expired, Pageable pageable) {
        Page<Ban> bans = this.banDao.getBansByExpired(expired,pageable);
        return this.pagedResourcesAssembler.toModel(bans,modelAssembler);
    }

    @Override
    public CollectionModel<BanType> getTypes() {
        return CollectionModel.of(Arrays.stream(BanType.values()).toList());
    }

    @Override
    public CollectionModel<String> getOrderTypes() {
        return CollectionModel.of(SpecificationsUtils.generateOrderTypes(Ban.class));
    }

    @Override
    public BanDto getBan(UUID banID) {
        Ban ban = this.banDao.findById(banID).orElseThrow();
        return this.modelMapper.map(ban,BanDto.class);
    }

    @Override
    public void deleteBan(UUID banID) {
        this.banDao.findById(banID).orElseThrow();
        this.banDao.deleteById(banID);
    }
}

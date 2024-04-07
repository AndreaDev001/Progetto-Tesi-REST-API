package com.progettotirocinio.restapi.services.implementations.bans;

import com.progettotirocinio.restapi.config.caching.CacheHandler;
import com.progettotirocinio.restapi.config.caching.RequiresCaching;
import com.progettotirocinio.restapi.config.exceptions.InvalidFormat;
import com.progettotirocinio.restapi.config.mapper.Mapper;
import com.progettotirocinio.restapi.data.dao.UserDao;
import com.progettotirocinio.restapi.data.dao.bans.BanDao;
import com.progettotirocinio.restapi.data.dao.specifications.BanSpecifications;
import com.progettotirocinio.restapi.data.dao.specifications.SpecificationsUtils;
import com.progettotirocinio.restapi.data.dto.input.create.CreateBanDto;
import com.progettotirocinio.restapi.data.dto.input.update.UpdateBanDto;
import com.progettotirocinio.restapi.data.dto.output.bans.BanDto;
import com.progettotirocinio.restapi.data.entities.User;
import com.progettotirocinio.restapi.data.entities.bans.Ban;
import com.progettotirocinio.restapi.data.entities.enums.BanType;
import com.progettotirocinio.restapi.data.entities.enums.ReportReason;
import com.progettotirocinio.restapi.services.implementations.GenericServiceImp;
import com.progettotirocinio.restapi.services.interfaces.bans.BanService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiresCaching(allCacheName = "ALL_BANS",allSearchName = "SEARCH_BANS",searchCachingRequired = true)
public class BanServiceImp extends GenericServiceImp<Ban, BanDto> implements BanService
{
    private final BanDao banDao;

    public BanServiceImp(CacheHandler cacheHandler,BanDao banDao, UserDao userDao, Mapper mapper, PagedResourcesAssembler<Ban> pagedResourcesAssembler) {
        super(cacheHandler,userDao, mapper,Ban.class,BanDto.class, pagedResourcesAssembler);
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
    @Transactional
    public BanDto createBan(CreateBanDto createBanDto) {
        User authenticatedUser = this.userDao.findById(UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName())).orElseThrow();
        User bannedUser = this.userDao.findById(createBanDto.getBannedID()).orElseThrow();
        if(authenticatedUser.getId().equals(bannedUser.getId()))
            throw new InvalidFormat("error.ban.invalidBanner");
        Ban ban = new Ban();
        ban.setTitle(createBanDto.getTitle());
        ban.setDescription(createBanDto.getDescription());
        ban.setReason(createBanDto.getReason());
        ban.setBanner(authenticatedUser);
        ban.setBanned(bannedUser);
        ban.setType(BanType.BAN);
        ban.setExpirationDate(createBanDto.getExpirationDate());
        ban.setExpired(false);
        ban = this.banDao.save(ban);
        return this.modelMapper.map(ban,BanDto.class);
    }

    @Override
    @Transactional
    public BanDto updateBan(UpdateBanDto updateBanDto) {
        Ban ban =  this.banDao.findById(updateBanDto.getBanID()).orElseThrow();
        if(updateBanDto.getTitle() != null)
            ban.setTitle(updateBanDto.getTitle());
        if(updateBanDto.getDescription() != null)
            ban.setDescription(updateBanDto.getDescription());
        if(updateBanDto.getReason() != null)
            ban.setReason(updateBanDto.getReason());
        ban = this.banDao.save(ban);
        return this.modelMapper.map(ban,BanDto.class);
    }

    @Override
    @Transactional
    @Scheduled(fixedDelay = 24 * 60 * 60 * 1000)
    public void handleExpiredBans() {
        List<Ban> bans = this.banDao.getBansByDate(LocalDate.now());
        for(Ban current : bans)
            current.setExpired(true);
        this.banDao.saveAll(bans);
    }

    @Override
    @Transactional
    @Scheduled(fixedDelay = 24 * 60 * 60 * 1000,initialDelay = 24 * 60 * 60 * 1000)
    public void deleteExpiredBans() {
        List<Ban> bans = this.banDao.getBansByExpired(true);
        this.banDao.deleteAll(bans);
    }

    @Override
    @Transactional
    public void deleteBan(UUID banID) {
        this.banDao.findById(banID).orElseThrow();
        this.banDao.deleteById(banID);
    }
}

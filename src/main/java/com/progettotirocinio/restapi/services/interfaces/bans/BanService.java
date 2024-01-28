package com.progettotirocinio.restapi.services.interfaces.bans;

import com.progettotirocinio.restapi.data.dto.output.bans.BanDto;
import com.progettotirocinio.restapi.data.entities.bans.Ban;
import com.progettotirocinio.restapi.data.entities.enums.BanType;
import com.progettotirocinio.restapi.data.entities.enums.ReportReason;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;

import java.util.Collection;
import java.util.UUID;

public interface BanService
{
    PagedModel<BanDto> getBans(Pageable pageable);
    PagedModel<BanDto> getBansBySpec(Specification<Ban> specification,Pageable pageable);
    PagedModel<BanDto> getSimilarBans(UUID banID,Pageable pageable);
    PagedModel<BanDto> getBansByBanner(UUID bannerID,Pageable pageable);
    PagedModel<BanDto> getBansByBanned(UUID bannedID,Pageable pageable);
    PagedModel<BanDto> getBansByType(BanType type,Pageable pageable);
    PagedModel<BanDto> getBansByReason(ReportReason reason,Pageable pageable);
    PagedModel<BanDto> getBansByExpired(Boolean expired,Pageable pageable);
    CollectionModel<BanType> getTypes();
    CollectionModel<String> getOrderTypes();
    BanDto getBan(UUID banID);
    void deleteBan(UUID banID);
}

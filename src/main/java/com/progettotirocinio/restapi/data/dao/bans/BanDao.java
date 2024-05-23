package com.progettotirocinio.restapi.data.dao.bans;

import com.progettotirocinio.restapi.data.entities.bans.Ban;
import com.progettotirocinio.restapi.data.entities.enums.BanType;
import com.progettotirocinio.restapi.data.entities.enums.ReportReason;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface BanDao extends JpaRepository<Ban, UUID>, JpaSpecificationExecutor<Ban>
{
    @Query("select b from Ban b where b.banner.id = :requiredID")
    Page<Ban> getBansByBanner(@Param("requiredID") UUID bannerID, Pageable pageable);
    @Query("select b from Ban b where b.banned.id = :requiredID")
    Page<Ban> getBansByBanned(@Param("requiredID") UUID bannedID,Pageable pageable);
    @Query("select b from Ban b where b.banned.id = :requiredID and b.expired = :requiredExpired")
    Ban getBan(@Param("requiredID") UUID userID,@Param("requiredExpired") boolean expired);
    @Query("select b from Ban b where b.type = :requiredType")
    Page<Ban> getBansByType(@Param("requiredType")BanType type,Pageable pageable);
    @Query("select b from Ban b where b.reason = :requiredReason")
    Page<Ban> getBansByReason(@Param("requiredReason")ReportReason reason,Pageable pageable);
    @Query("select b from Ban b where b.expired = :requiredExpired")
    Page<Ban> getBansByExpired(@Param("requiredExpired") boolean expired,Pageable pageable);
    @Query("select b from Ban b where :requiredDate > b.expirationDate")
    List<Ban> getBansByDate(@Param("requiredDate")LocalDate date);
    @Query("select b from Ban b where b.expired = :requiredExpired")
    List<Ban> getBansByExpired(@Param("requiredExpired") boolean expired);
}

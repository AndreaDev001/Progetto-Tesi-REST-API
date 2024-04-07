package com.progettotirocinio.restapi.data.dao.specifications;

import com.progettotirocinio.restapi.data.dao.specifications.annotations.SpecificationPath;
import com.progettotirocinio.restapi.data.entities.Board;
import com.progettotirocinio.restapi.data.entities.User;
import com.progettotirocinio.restapi.data.entities.bans.Ban;
import com.progettotirocinio.restapi.data.entities.enums.BanType;
import com.progettotirocinio.restapi.data.entities.enums.Gender;
import com.progettotirocinio.restapi.data.entities.enums.ReportReason;
import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.List;

public class BanSpecifications
{
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = false)
    public static class Filter extends BaseFilter {
        @SpecificationPath(path = "title",comparison = SpecificationComparison.LIKE)
        private String title;
        @SpecificationPath(path = "description",comparison = SpecificationComparison.LIKE)
        private String description;
        @SpecificationPath(path = "type",comparison = SpecificationComparison.EQUAL)
        private BanType type;
        @SpecificationPath(path = "reason",comparison = SpecificationComparison.EQUAL)
        private ReportReason reason;
        @SpecificationPath(path = "expirationDate",comparison = SpecificationComparison.LIKE)
        private LocalDate expirationDate;
        @SpecificationPath(path =  "expired",comparison = SpecificationComparison.EQUAL)
        private boolean expired;
        @SpecificationPath(path = "banner.name",comparison = SpecificationComparison.LIKE)
        private String bannerName;
        @SpecificationPath(path = "banned.name",comparison = SpecificationComparison.LIKE)
        private String bannedName;
        @SpecificationPath(path = "banner.surname",comparison = SpecificationComparison.LIKE)
        private String bannerSurname;
        @SpecificationPath(path = "banned.surname",comparison = SpecificationComparison.LIKE)
        private String bannedSurname;
        @SpecificationPath(path = "banner.username",comparison = SpecificationComparison.LIKE)
        private String bannerUsername;
        @SpecificationPath(path = "banned.username",comparison = SpecificationComparison.LIKE)
        private String bannedUsername;
        @SpecificationPath(path = "banner.gender",comparison = SpecificationComparison.EQUAL)
        private Gender bannerGender;
        @SpecificationPath(path = "banned.gender",comparison = SpecificationComparison.EQUAL)
        private Gender bannedGender;

        public Filter(Ban ban) {
            User banner = ban.getBanner();
            User banned = ban.getBanned();
            this.title = ban.getTitle();
            this.description = ban.getDescription();
            this.reason = ban.getReason();
            this.type = ban.getType();
            this.expirationDate = ban.getExpirationDate();
            this.expired = ban.isExpired();
            this.bannerName = banner.getName();
            this.bannedName = banned.getName();
            this.bannerSurname = banner.getSurname();
            this.bannedSurname = banned.getSurname();
            this.bannerUsername = banner.getUsername();
            this.bannedUsername = banned.getUsername();
            this.bannerGender = banner.getGender();
            this.bannedGender = banned.getGender();
            this.excludedIDs.add(ban.getId());
        }
    }

    public static Specification<Ban> withFilter(BanSpecifications.Filter filter) {
        return (Root<Ban> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {
            Predicate requiredPredicate = SpecificationsUtils.generatePredicate(filter,root,criteriaBuilder);
            List<Order> requiredOrders = SpecificationsUtils.generateOrders(root,Ban.class,criteriaBuilder,filter.orderTypes,filter.orderMode);
            return criteriaQuery.where(requiredPredicate).orderBy(requiredOrders).getRestriction();
        };
    }
}

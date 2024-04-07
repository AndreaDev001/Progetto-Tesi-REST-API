package com.progettotirocinio.restapi.data.dao.specifications;

import com.progettotirocinio.restapi.data.dao.specifications.annotations.SpecificationPath;
import com.progettotirocinio.restapi.data.entities.User;
import com.progettotirocinio.restapi.data.entities.enums.Gender;
import com.progettotirocinio.restapi.data.entities.enums.ReportReason;
import com.progettotirocinio.restapi.data.entities.enums.ReportType;
import com.progettotirocinio.restapi.data.entities.reports.Report;
import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class ReportSpecifications
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
        @SpecificationPath(path = "reporter.name",comparison = SpecificationComparison.LIKE)
        private String reporterName;
        @SpecificationPath(path = "reported.name",comparison = SpecificationComparison.LIKE)
        private String reportedName;
        @SpecificationPath(path = "reporter.surname",comparison = SpecificationComparison.LIKE)
        private String reporterSurname;
        @SpecificationPath(path = "reported.surname",comparison = SpecificationComparison.LIKE)
        private String reportedSurname;
        @SpecificationPath(path = "reporter.username",comparison = SpecificationComparison.LIKE)
        private String reporterUsername;
        @SpecificationPath(path = "reported.username",comparison = SpecificationComparison.LIKE)
        private String reportedUsername;
        @SpecificationPath(path = "reporter.gender",comparison = SpecificationComparison.EQUAL)
        private Gender reporterGender;
        @SpecificationPath(path = "reported.gender",comparison = SpecificationComparison.EQUAL)
        private Gender reportedGender;
        @SpecificationPath(path = "type",comparison = SpecificationComparison.EQUAL)
        private ReportType type;
        @SpecificationPath(path = "reason",comparison = SpecificationComparison.EQUAL)
        private ReportReason reason;

        public Filter(Report report) {
            User reporter = report.getReporter();
            User reported = report.getReported();
            this.title = report.getTitle();
            this.description = report.getDescription();
            this.reporterName = reporter.getName();
            this.reportedName = reported.getName();
            this.reporterSurname = reporter.getSurname();
            this.reportedSurname = reported.getSurname();
            this.reporterUsername = reporter.getUsername();
            this.reportedUsername = reported.getUsername();
            this.reporterGender = reporter.getGender();
            this.reportedGender = reported.getGender();
            this.type = report.getType();
            this.reason = report.getReason();
            this.excludedIDs.add(report.getId());
        }
    }
    public static Specification<Report> withFilter(Filter filter) {
        return (Root<Report> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {
            Predicate requiredPredicate = SpecificationsUtils.generatePredicate(filter,root,criteriaBuilder);
            List<Order> requiredOrders = SpecificationsUtils.generateOrders(root,Report.class,criteriaBuilder,filter.orderTypes,filter.orderMode);
            return criteriaQuery.where(requiredPredicate).orderBy(requiredOrders).getRestriction();
        };
    }
}

package com.progettotirocinio.restapi.data.dao.specifications;

import com.progettotirocinio.restapi.data.dao.specifications.annotations.SpecificationPath;
import com.progettotirocinio.restapi.data.entities.Board;
import com.progettotirocinio.restapi.data.entities.Discussion;
import com.progettotirocinio.restapi.data.entities.enums.Gender;
import jakarta.persistence.criteria.*;
import lombok.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class DiscussionSpecifications
{
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = false)
    public static class Filter extends BaseFilter {
        @SpecificationPath(path = "title",comparison = SpecificationComparison.LIKE)
        private String title;
        @SpecificationPath(path = "topic",comparison = SpecificationComparison.LIKE)
        private String topic;
        @SpecificationPath(path = "name",comparison = SpecificationComparison.LIKE)
        private String publisherName;
        @SpecificationPath(path = "surname",comparison = SpecificationComparison.LIKE)
        private String publisherSurname;
        @SpecificationPath(path = "username",comparison = SpecificationComparison.LIKE)
        private String publisherUsername;
        @SpecificationPath(path = "gender",comparison = SpecificationComparison.EQUAL)
        private Gender publisherGender;

        public Filter(Discussion discussion) {
            this.title = discussion.getTitle();
            this.topic = discussion.getTopic();
            this.publisherName = discussion.getPublisher().getName();
            this.publisherSurname = discussion.getPublisher().getSurname();
            this.publisherUsername = discussion.getPublisher().getUsername();
            this.publisherGender = discussion.getPublisher().getGender();
            this.excludedIDs.add(discussion.getId());
        }
    }

    public static Specification<Discussion> withFilter(Filter filter) {
        return (Root<Discussion> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {
            Predicate requiredPredicate = SpecificationsUtils.generatePredicate(filter,root,criteriaBuilder);
            List<Order> requiredOrders = SpecificationsUtils.generateOrders(root,Discussion.class,criteriaBuilder,filter.orderTypes,filter.orderMode);
            return criteriaQuery.where(requiredPredicate).orderBy(requiredOrders).getRestriction();
        };
    }
}

package com.progettotirocinio.restapi.data.dao.specifications;

import com.progettotirocinio.restapi.data.dao.specifications.annotations.SpecificationPath;
import com.progettotirocinio.restapi.data.entities.Board;
import com.progettotirocinio.restapi.data.entities.User;
import com.progettotirocinio.restapi.data.entities.enums.Gender;
import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class BoardSpecifications
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
        @SpecificationPath(path = "email",comparison = SpecificationComparison.LIKE)
        private String publisherEmail;
        @SpecificationPath(path = "username",comparison = SpecificationComparison.LIKE)
        private String publisherUsername;
        @SpecificationPath(path = "name",comparison = SpecificationComparison.LIKE)
        private String publisherName;
        @SpecificationPath(path = "surname",comparison = SpecificationComparison.LIKE)
        private String publisherSurname;
        @SpecificationPath(path = "gender",comparison = SpecificationComparison.EQUAL)
        private Gender publisherGender;

        public Filter(Board board) {
            this.title = board.getTitle();
            this.description = board.getDescription();
            this.publisherEmail = board.getPublisher().getEmail();
            this.publisherUsername = board.getPublisher().getUsername();
            this.publisherName = board.getPublisher().getName();
            this.publisherSurname = board.getPublisher().getSurname();
            this.publisherGender = board.getPublisher().getGender();
        }
    }
    public static Specification<Board> withFilter(BoardSpecifications.Filter filter) {
        return (Root<Board> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {
            Predicate requiredPredicate = SpecificationsUtils.generatePredicate(filter,root,criteriaBuilder);
            List<Order> requiredOrders = SpecificationsUtils.generateOrders(root,Board.class,criteriaBuilder,filter.orderTypes,filter.orderMode);
            return criteriaQuery.where(requiredPredicate).orderBy(requiredOrders).getRestriction();
        };
    }
}

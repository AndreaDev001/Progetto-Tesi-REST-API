package com.progettotirocinio.restapi.data.dao.specifications;

import com.progettotirocinio.restapi.data.dao.specifications.annotations.SpecificationPath;
import com.progettotirocinio.restapi.data.entities.User;
import com.progettotirocinio.restapi.data.entities.enums.Gender;
import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class UserSpecifications
{
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = false)
    public static class Filter extends BaseFilter {
        @SpecificationPath(path = "email",comparison = SpecificationComparison.LIKE)
        private String email;
        @SpecificationPath(path = "username",comparison = SpecificationComparison.LIKE)
        private String username;
        @SpecificationPath(path = "name",comparison = SpecificationComparison.LIKE)
        private String name;
        @SpecificationPath(path = "surname",comparison = SpecificationComparison.LIKE)
        private String surname;
        @SpecificationPath(path = "gender",comparison = SpecificationComparison.EQUAL)
        private Gender gender;

        public Filter(User user) {
            this.email = user.getEmail();
            this.username = user.getUsername();
            this.name = user.getName();
            this.surname = user.getSurname();
            this.gender = user.getGender();
            this.excludedIDs.add(user.getId());
        }
    }

    public static Specification<User> withFilter(Filter filter) {
        return (Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {
            Predicate requiredPredicate = SpecificationsUtils.generatePredicate(filter,root,criteriaBuilder);
            List<Order> requiredOrders = SpecificationsUtils.generateOrders(root,User.class,criteriaBuilder,filter.orderTypes,filter.orderMode);
            return criteriaQuery.where(requiredPredicate).orderBy(requiredOrders).getRestriction();
        };
    }
}

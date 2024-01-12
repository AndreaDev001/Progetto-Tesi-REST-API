package com.progettotirocinio.restapi.data.dao.specifications;


import com.progettotirocinio.restapi.data.dao.specifications.annotations.SpecificationPath;
import com.progettotirocinio.restapi.data.entities.Task;
import com.progettotirocinio.restapi.data.entities.User;
import com.progettotirocinio.restapi.data.entities.enums.Priority;
import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.UUID;

public class TaskSpecifications
{
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = false)
    public static class Filter extends BaseFilter {
        @SpecificationPath(path = "title",comparison = SpecificationComparison.LIKE)
        private String title;
        @SpecificationPath(path = "name",comparison = SpecificationComparison.LIKE)
        private String name;
        @SpecificationPath(path = "description",comparison = SpecificationComparison.LIKE)
        private String description;
        @SpecificationPath(path = "priority",comparison = SpecificationComparison.EQUAL)
        private Priority priority;
        @SpecificationPath(path = "group.id",comparison = SpecificationComparison.EQUAL)
        private UUID groupID;
        @SpecificationPath(path = "publisher.email",comparison = SpecificationComparison.LIKE)
        private String publisherEmail;
        @SpecificationPath(path = "publisher.username",comparison = SpecificationComparison.LIKE)
        private String publisherUsername;
        @SpecificationPath(path = "publisher.surname",comparison = SpecificationComparison.LIKE)
        private String publisherSurname;
        @SpecificationPath(path = "publisher.name",comparison = SpecificationComparison.LIKE)
        private String publisherName;


        public Filter(Task task) {
            this.title = task.getTitle();
            this.name = task.getName();
            this.description = task.getDescription();
            this.priority = task.getPriority();
            this.groupID = task.getGroup().getId();
        }
    }
    public static Specification<Task> withFilter(TaskSpecifications.Filter filter) {
        return (Root<Task> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {
            Predicate requiredPredicate = SpecificationsUtils.generatePredicate(filter,root,criteriaBuilder);
            List<Order> requiredOrders = SpecificationsUtils.generateOrders(root,Task.class,criteriaBuilder,filter.orderTypes,filter.orderMode);
            return criteriaQuery.where(requiredPredicate).orderBy(requiredOrders).getRestriction();
        };
    }
}

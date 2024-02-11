package com.progettotirocinio.restapi.data.dao.specifications;

import com.progettotirocinio.restapi.data.dao.specifications.annotations.SpecificationPath;
import com.progettotirocinio.restapi.data.entities.Board;
import com.progettotirocinio.restapi.data.entities.Poll;
import com.progettotirocinio.restapi.data.entities.enums.Gender;
import com.progettotirocinio.restapi.data.entities.enums.PollStatus;
import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class PollSpecifications
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
        @SpecificationPath(path = "minimumVotes",comparison = SpecificationComparison.EQUAL)
        private Integer minimumVotes;
        @SpecificationPath(path = "maximumVotes",comparison = SpecificationComparison.EQUAL)
        private Integer maximumVotes;
        @SpecificationPath(path = "publisher.name",comparison = SpecificationComparison.LIKE)
        private String publisherName;
        @SpecificationPath(path = "publisher.surname",comparison = SpecificationComparison.LIKE)
        private String publisherSurname;
        @SpecificationPath(path = "publisher.username",comparison = SpecificationComparison.LIKE)
        private String publisherUsername;
        @SpecificationPath(path = "publisher.gender",comparison = SpecificationComparison.EQUAL)
        private Gender publisherGender;
        @SpecificationPath(path = "status",comparison = SpecificationComparison.EQUAL)
        private PollStatus status;

        public Filter(Poll poll) {
            this.title = poll.getTitle();
            this.description = poll.getDescription();
            this.minimumVotes = poll.getMinimumVotes();
            this.maximumVotes = poll.getMaximumVotes();
            this.publisherName = poll.getPublisher().getName();
            this.publisherSurname = poll.getPublisher().getSurname();
            this.publisherUsername = poll.getPublisher().getUsername();
            this.publisherGender = poll.getPublisher().getGender();
            this.status = poll.getStatus();
            this.excludedIDs.add(poll.getId());
        }
    }

    public static Specification<Poll> withFilter(PollSpecifications.Filter filter) {
        return (Root<Poll> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {
            Predicate requiredPredicate = SpecificationsUtils.generatePredicate(filter,root,criteriaBuilder);
            List<Order> requiredOrders = SpecificationsUtils.generateOrders(root,Poll.class,criteriaBuilder,filter.orderTypes,filter.orderMode);
            return criteriaQuery.where(requiredPredicate).orderBy(requiredOrders).getRestriction();
        };
    }
}

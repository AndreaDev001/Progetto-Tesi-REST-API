package com.progettotirocinio.restapi.data.entities.polls;


import com.progettotirocinio.restapi.data.converters.TrimConverter;
import com.progettotirocinio.restapi.data.dao.specifications.annotations.SpecificationOrderType;
import com.progettotirocinio.restapi.data.dao.specifications.annotations.SpecificationPrefix;
import com.progettotirocinio.restapi.data.entities.AmountEntity;
import com.progettotirocinio.restapi.data.entities.User;
import com.progettotirocinio.restapi.data.entities.comments.CommentPoll;
import com.progettotirocinio.restapi.data.entities.enums.PollStatus;
import com.progettotirocinio.restapi.data.entities.interfaces.OwnableEntity;
import com.progettotirocinio.restapi.data.entities.likes.PollLike;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.UUID;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@EntityListeners(value = AuditingEntityListener.class)
@Table(name = "POLLS")
@SpecificationPrefix
public class Poll extends AmountEntity implements OwnableEntity
{

    @Column(name = "TITLE",nullable = false,updatable = false)
    @Convert(converter = TrimConverter.class)
    @Length(min = 3,max = 20)
    @SpecificationOrderType
    private String title;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "PUBLISHER_ID",updatable = false,nullable = false)
    @SpecificationOrderType(allowDepth = true)
    private User publisher;

    @Column(name = "DESCRIPTION",nullable = false,updatable = false)
    @Convert(converter = TrimConverter.class)
    @Length(min = 20,max = 200)
    @SpecificationOrderType
    @Lob
    private String description;

    @OneToMany(cascade = CascadeType.REMOVE,fetch = FetchType.EAGER,orphanRemoval = true,mappedBy = "poll")
    private Set<PollLike> receivedLikes = new HashSet<>();

    @OneToMany(cascade = CascadeType.REMOVE,fetch = FetchType.EAGER,orphanRemoval = true,mappedBy = "poll")
    private Set<CommentPoll> receivedComments = new HashSet<>();

    @OneToMany(cascade = CascadeType.REMOVE,fetch = FetchType.EAGER,orphanRemoval = true,mappedBy = "poll")
    private Set<PollOption> options = new HashSet<>();

    @Column(name = "MINIMUM_VOTES",nullable = false)
    @SpecificationOrderType
    @Positive
    @Max(value = 20)
    private Integer minimumVotes;

    @Column(name = "MAXIMUM_VOTES",nullable = false)
    @SpecificationOrderType
    @Min(value = 20)
    @Max(value = 40)
    private Integer maximumVotes;

    @Column(name = "STATUS",nullable = false)
    @Enumerated(value = EnumType.STRING)
    private PollStatus status;

    @Column(name = "EXPIRATION_DATE",nullable = false)
    private LocalDate expirationDate;

    @Override
    public UUID getOwnerID() {
        return this.publisher.getId();
    }
}

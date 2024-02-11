package com.progettotirocinio.restapi.data.entities;


import com.progettotirocinio.restapi.data.converters.TrimConverter;
import com.progettotirocinio.restapi.data.dao.specifications.annotations.SpecificationOrderType;
import com.progettotirocinio.restapi.data.dao.specifications.annotations.SpecificationPrefix;
import com.progettotirocinio.restapi.data.entities.interfaces.OwnableEntity;
import com.progettotirocinio.restapi.data.entities.likes.DiscussionLike;
import com.progettotirocinio.restapi.data.entities.likes.Like;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder
@Entity
@EntityListeners(value = AuditingEntityListener.class)
@Table(name = "DISCUSSIONS")
@SpecificationPrefix
public class Discussion extends AmountEntity implements OwnableEntity
{
    @Column(name = "TITLE",nullable = false,updatable = false)
    @Convert(converter = TrimConverter.class)
    @SpecificationOrderType
    private String title;

    @Column(name = "TOPIC",nullable = false,updatable = false)
    @Convert(converter = TrimConverter.class)
    @SpecificationOrderType
    private String topic;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true,mappedBy = "discussion")
    private Set<DiscussionLike> receivedLikes = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "discussion")
    private Set<Comment> comments = new HashSet<>();

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "PUBLISHER_ID",updatable = false)
    @SpecificationOrderType(allowDepth = true)
    private User publisher;

    @Override
    public UUID getOwnerID() {
        return this.publisher.getId();
    }
}

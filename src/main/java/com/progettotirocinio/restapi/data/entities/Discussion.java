package com.progettotirocinio.restapi.data.entities;


import com.progettotirocinio.restapi.data.converters.TrimConverter;
import com.progettotirocinio.restapi.data.dao.specifications.annotations.SpecificationOrderType;
import com.progettotirocinio.restapi.data.dao.specifications.annotations.SpecificationPrefix;
import com.progettotirocinio.restapi.data.entities.comments.Comment;
import com.progettotirocinio.restapi.data.entities.comments.CommentDiscussion;
import com.progettotirocinio.restapi.data.entities.interfaces.OwnableEntity;
import com.progettotirocinio.restapi.data.entities.likes.DiscussionLike;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

    @Column(name = "TEXT",nullable = false)
    @Convert(converter = TrimConverter.class)
    @Lob
    private String text;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true,mappedBy = "discussion")
    private Set<DiscussionLike> receivedLikes = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "discussion")
    private Set<CommentDiscussion> receivedComments = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "PUBLISHER_ID",updatable = false)
    @SpecificationOrderType(allowDepth = true)
    private User publisher;

    @Override
    public UUID getOwnerID() {
        return this.publisher.getId();
    }
}

package com.progettotirocinio.restapi.data.entities;


import com.progettotirocinio.restapi.data.converters.TrimConverter;
import com.progettotirocinio.restapi.data.entities.interfaces.OwnableEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@Entity
@EntityListeners(value = AuditingEntityListener.class)
@Table(name = "COMMENTS")
public class Comment extends AmountEntity implements OwnableEntity
{
    @Column(name = "TITLE",nullable = false)
    @Convert(converter = TrimConverter.class)
    private String title;

    @Column(name = "TEXT",nullable = false)
    @Convert(converter = TrimConverter.class)
    private String text;

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinTable(name = "COMMENT_LIKES",joinColumns = @JoinColumn(name = "COMMENT_ID"),
    inverseJoinColumns = @JoinColumn(name = "LIKE_ID"),uniqueConstraints = @UniqueConstraint(columnNames = {"COMMENT_ID","LIKE_ID"}))
    private Set<Like> receivedLikes = new HashSet<>();

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY,optional = true)
    @JoinColumn(name = "DISCUSSION_ID",updatable = false)
    private Discussion discussion;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "PUBLISHER_ID",nullable = false,updatable = false)
    private User publisher;

    @Override
    public UUID getOwnerID() {
        return this.publisher.getId();
    }
}

package com.progettotirocinio.restapi.data.entities.comments;


import com.progettotirocinio.restapi.data.converters.TrimConverter;
import com.progettotirocinio.restapi.data.entities.AmountEntity;
import com.progettotirocinio.restapi.data.entities.Discussion;
import com.progettotirocinio.restapi.data.entities.User;
import com.progettotirocinio.restapi.data.entities.enums.CommentType;
import com.progettotirocinio.restapi.data.entities.interfaces.OwnableEntity;
import com.progettotirocinio.restapi.data.entities.likes.CommentLike;
import com.progettotirocinio.restapi.data.entities.likes.Like;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@EntityListeners(value = AuditingEntityListener.class)
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "COMMENTS")
public class Comment extends AmountEntity implements OwnableEntity
{
    @Column(name = "TITLE",nullable = false)
    @Convert(converter = TrimConverter.class)
    protected String title;

    @Column(name = "TEXT",nullable = false)
    @Convert(converter = TrimConverter.class)
    protected String text;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true,mappedBy = "comment")
    protected Set<CommentLike> receivedLikes = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "PUBLISHER_ID",nullable = false,updatable = false)
    protected User publisher;

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE",nullable = false)
    private CommentType type;

    @Override
    public UUID getOwnerID() {
        return this.publisher.getId();
    }
}

package com.progettotirocinio.restapi.data.entities;


import com.progettotirocinio.restapi.data.converters.TrimConverter;
import com.progettotirocinio.restapi.data.dao.specifications.annotations.SpecificationOrderType;
import com.progettotirocinio.restapi.data.dao.specifications.annotations.SpecificationPrefix;
import com.progettotirocinio.restapi.data.entities.enums.BoardStatus;
import com.progettotirocinio.restapi.data.entities.enums.BoardVisibility;
import com.progettotirocinio.restapi.data.entities.images.BoardImage;
import com.progettotirocinio.restapi.data.entities.interfaces.OwnableEntity;
import com.progettotirocinio.restapi.data.entities.tags.Tag;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@EntityListeners(value = AuditingEntityListener.class)
@Table(name = "BOARDS")
@SpecificationPrefix(prefix = "BOARDS")
public class Board extends AmountEntity implements OwnableEntity
{
    @Column(name = "TITLE",nullable = false)
    @Convert(converter = TrimConverter.class)
    @Length(min = 3,max = 20)
    @SpecificationOrderType
    private String title;

    @Column(name = "DESCRIPTION",nullable = false)
    @Convert(converter = TrimConverter.class)
    @Length(min = 20,max = 200)
    @SpecificationOrderType
    private String description;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "PUBLISHER_ID",nullable = false,updatable = false)
    @SpecificationOrderType(allowDepth = true)
    private User publisher;

    @Enumerated(EnumType.STRING)
    @Column(name = "VISIBILITY",nullable = false)
    private BoardVisibility visibility;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS",nullable = false)
    private BoardStatus status;

    @OneToOne(mappedBy = "board",fetch = FetchType.LAZY)
    private BoardImage boardImage;


    @EqualsAndHashCode.Exclude
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "board",orphanRemoval = true)
    private Set<TaskGroup> groups = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "board",orphanRemoval = true)
    private Set<Role> roles = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "board",orphanRemoval = true)
    private Set<BoardInvite> invites = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "board",orphanRemoval = true)
    private Set<BoardMember> members = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "board",orphanRemoval = true)
    private Set<Tag> associatedTags = new HashSet<>();

    @Column(name = "MAX_MEMBERS",nullable = false)
    @Min(value = 5)
    @Min(value = 20)
    private Integer maxMembers;

    @Column(name = "EXPIRATION_DATE")
    private LocalDate expirationDate;

    @Override
    public UUID getOwnerID() {
        return this.publisher.getId();
    }
}

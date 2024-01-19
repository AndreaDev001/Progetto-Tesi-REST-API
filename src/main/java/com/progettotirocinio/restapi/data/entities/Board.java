package com.progettotirocinio.restapi.data.entities;


import com.progettotirocinio.restapi.data.converters.TrimConverter;
import com.progettotirocinio.restapi.data.dao.specifications.annotations.SpecificationOrderType;
import com.progettotirocinio.restapi.data.dao.specifications.annotations.SpecificationPrefix;
import com.progettotirocinio.restapi.data.entities.enums.BoardVisibility;
import com.progettotirocinio.restapi.data.entities.images.BoardImage;
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
@EqualsAndHashCode(callSuper = true)
@Builder
@Entity
@EntityListeners(value = AuditingEntityListener.class)
@Table(name = "BOARDS")
@SpecificationPrefix
public class Board extends AmountEntity implements OwnableEntity
{
    @Column(name = "TITLE",nullable = false)
    @Convert(converter = TrimConverter.class)
    @SpecificationOrderType
    private String title;

    @Column(name = "DESCRIPTION",nullable = false)
    @Convert(converter = TrimConverter.class)
    @SpecificationOrderType
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "VISIBILITY",nullable = false)
    private BoardVisibility visibility;

    @OneToOne(mappedBy = "board",fetch = FetchType.LAZY)
    private BoardImage boardImage;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "PUBLISHER_ID",nullable = false,updatable = false)
    @SpecificationOrderType(allowDepth = true)
    private User publisher;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "board",orphanRemoval = true)
    private Set<TaskGroup> groups = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "board",orphanRemoval = true)
    private Set<Tag> tags = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "board",orphanRemoval = true)
    private Set<Role> roles = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "board",orphanRemoval = true)
    private Set<BoardInvite> invites = new HashSet<>();

    @Column(name = "MAX_MEMBERS",nullable = false)
    private Integer maxMembers;

    @Column(name = "EXPIRATION_DATE",nullable = false)
    private LocalDate expirationDate;

    @Override
    public UUID getOwnerID() {
        return this.publisher.getId();
    }
}

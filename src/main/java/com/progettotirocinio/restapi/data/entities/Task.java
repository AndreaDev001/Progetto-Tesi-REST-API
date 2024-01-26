package com.progettotirocinio.restapi.data.entities;


import com.progettotirocinio.restapi.data.converters.TrimConverter;
import com.progettotirocinio.restapi.data.dao.specifications.annotations.SpecificationOrderType;
import com.progettotirocinio.restapi.data.dao.specifications.annotations.SpecificationPath;
import com.progettotirocinio.restapi.data.dao.specifications.annotations.SpecificationPrefix;
import com.progettotirocinio.restapi.data.entities.enums.Priority;
import com.progettotirocinio.restapi.data.entities.images.TaskImage;
import com.progettotirocinio.restapi.data.entities.interfaces.OwnableEntity;
import jakarta.persistence.*;
import lombok.*;
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
@Table(name = "TASKS")
@SpecificationPrefix
public class Task extends GenericEntity implements OwnableEntity
{
    @Column(name = "TITLE",nullable = false)
    @Convert(converter = TrimConverter.class)
    @SpecificationOrderType
    private String title;

    @Column(name = "NAME",nullable = false)
    @Convert(converter = TrimConverter.class)
    @SpecificationOrderType
    private String name;

    @Column(name = "DESCRIPTION",nullable = false)
    @Convert(converter = TrimConverter.class)
    @SpecificationOrderType
    private String description;

    @Column(name = "PRIORITY",nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Priority priority;

    @OneToOne(mappedBy = "task",fetch = FetchType.LAZY)
    private TaskImage taskImage;

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinTable(name = "TASK_LIKES",joinColumns = @JoinColumn(name = "TASK_ID"),
    inverseJoinColumns = @JoinColumn(name = "LIKE_ID"),uniqueConstraints = @UniqueConstraint(columnNames = {"LIKE_ID","TASK_ID"}))
    private Set<Like> receivedLikes = new HashSet<>();

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "PUBLISHER_ID",nullable = false,updatable = false)
    @SpecificationOrderType(allowDepth = true)
    private User publisher;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "GROUP_ID",nullable = false)
    private TaskGroup group;

    @Column(name = "EXPIRATION_DATE")
    @Convert(converter = TrimConverter.class)
    private LocalDate expirationDate;

    @Override
    public UUID getOwnerID() {
        return this.publisher.getId();
    }
}

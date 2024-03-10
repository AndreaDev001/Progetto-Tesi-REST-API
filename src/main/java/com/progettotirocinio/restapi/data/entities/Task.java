package com.progettotirocinio.restapi.data.entities;


import com.progettotirocinio.restapi.data.converters.TrimConverter;
import com.progettotirocinio.restapi.data.dao.specifications.annotations.SpecificationOrderType;
import com.progettotirocinio.restapi.data.dao.specifications.annotations.SpecificationPrefix;
import com.progettotirocinio.restapi.data.entities.checklists.CheckList;
import com.progettotirocinio.restapi.data.entities.comments.CommentTask;
import com.progettotirocinio.restapi.data.entities.enums.Priority;
import com.progettotirocinio.restapi.data.entities.enums.TaskStatus;
import com.progettotirocinio.restapi.data.entities.images.TaskImage;
import com.progettotirocinio.restapi.data.entities.interfaces.BoardElement;
import com.progettotirocinio.restapi.data.entities.interfaces.OwnableEntity;
import com.progettotirocinio.restapi.data.entities.likes.TaskLike;
import com.progettotirocinio.restapi.data.entities.tags.TagAssignment;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(value = AuditingEntityListener.class)
@Table(name = "TASKS")
@SpecificationPrefix
public class Task extends AmountEntity implements OwnableEntity, BoardElement
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

    @Column(name = "CURRENT_ORDER",nullable = false)
    private Integer currentOrder;

    @Column(name = "PRIORITY",nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Priority priority;

    @Column(name = "STATUS",nullable = false)
    @Enumerated(value = EnumType.STRING)
    private TaskStatus status;


    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "task",orphanRemoval = true)
    private Set<TaskLike> receivedLikes = new HashSet<>();

    @OneToMany(cascade = CascadeType.REMOVE,fetch = FetchType.EAGER,mappedBy = "task",orphanRemoval = true)
    private Set<TagAssignment> receivedTags = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "task",orphanRemoval = true)
    private Set<TaskAssignment> assignments =  new HashSet<>();

    @OneToMany(cascade = CascadeType.REMOVE,fetch = FetchType.EAGER,mappedBy = "task",orphanRemoval = true)
    private Set<TaskImage> taskImages = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "task",orphanRemoval = true)
    private Set<CommentTask> receivedComments = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "task",orphanRemoval = true)
    private Set<CheckList> checkLists = new HashSet<>();


    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "PUBLISHER_ID",nullable = false,updatable = false)
    @SpecificationOrderType(allowDepth = true)
    private User publisher;

    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    @JoinColumn(name = "GROUP_ID",nullable = false)
    @EqualsAndHashCode.Exclude
    private TaskGroup group;

    @Column(name = "EXPIRATION_DATE")
    private LocalDate expirationDate;

    @Override
    public UUID getOwnerID() {
        return this.publisher.getId();
    }

    @Override
    public UUID getBoardID() {
        return this.group.getBoardID();
    }
}

package com.progettotirocinio.restapi.data.entities;


import com.progettotirocinio.restapi.data.converters.TrimConverter;
import com.progettotirocinio.restapi.data.dao.BoardMemberDao;
import com.progettotirocinio.restapi.data.dao.specifications.annotations.SpecificationOrderType;
import com.progettotirocinio.restapi.data.dao.specifications.annotations.SpecificationPrefix;
import com.progettotirocinio.restapi.data.entities.enums.Gender;
import com.progettotirocinio.restapi.data.entities.images.UserImage;
import com.progettotirocinio.restapi.data.entities.likes.Like;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@EntityListeners(value = AuditingEntityListener.class)
@Table(name = "USERS")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@SpecificationPrefix
public class User extends AmountEntity
{
    @Column(name = "EMAIL",nullable = false,updatable = false)
    @Convert(converter = TrimConverter.class)
    @SpecificationOrderType
    private String email;

    @Column(name = "USERNAME",nullable = false,updatable = false)
    @Convert(converter = TrimConverter.class)
    @SpecificationOrderType
    private String username;

    @Column(name = "NAME",nullable = false)
    @Convert(converter = TrimConverter.class)
    @SpecificationOrderType
    private String name;

    @Column(name = "SURNAME",nullable = false,updatable = false)
    @Convert(converter = TrimConverter.class)
    @SpecificationOrderType
    private String surname;

    @Column(name = "GENDER",nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @OneToOne(mappedBy = "user",fetch = FetchType.LAZY)
    private UserImage user;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = false,mappedBy = "publisher")
    private Set<Board> createdBoards = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = false,mappedBy = "publisher")
    private Set<Task> createdTasks = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = false,mappedBy = "publisher")
    private Set<TaskGroup> createdTaskGroups = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true,mappedBy = "publisher")
    private Set<Comment> createdComments = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true,mappedBy = "publisher")
    private Set<Discussion> createdDiscussions = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true,mappedBy = "owner")
    private Set<RoleOwner> rolesOwned  = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true,mappedBy = "publisher")
    private Set<BoardInvite> boardInvites = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true,mappedBy = "publisher")
    private Set<Poll> createdPolls = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "publisher")
    private Set<Tag> createdTags = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "publisher")
    private Set<Role> createdRoles = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "user")
    private Set<BoardMember> joinedBoards = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "user")
    private Set<Like> createdLikes = new HashSet<>();
}

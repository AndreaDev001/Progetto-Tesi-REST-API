package com.progettotirocinio.restapi.data.entities;


import com.progettotirocinio.restapi.data.converters.TrimConverter;
import com.progettotirocinio.restapi.data.dao.specifications.annotations.SpecificationOrderType;
import com.progettotirocinio.restapi.data.dao.specifications.annotations.SpecificationPrefix;
import com.progettotirocinio.restapi.data.entities.bans.Ban;
import com.progettotirocinio.restapi.data.entities.comments.Comment;
import com.progettotirocinio.restapi.data.entities.enums.Gender;
import com.progettotirocinio.restapi.data.entities.enums.UserVisibility;
import com.progettotirocinio.restapi.data.entities.images.UserImage;
import com.progettotirocinio.restapi.data.entities.likes.*;
import com.progettotirocinio.restapi.data.entities.polls.Poll;
import com.progettotirocinio.restapi.data.entities.polls.PollVote;
import com.progettotirocinio.restapi.data.entities.reports.Report;
import com.progettotirocinio.restapi.data.entities.tags.Tag;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.HashSet;
import java.util.Set;

@Entity
@EntityListeners(value = AuditingEntityListener.class)
@Table(name = "USERS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

    @Column(name = "VISIBILITY",nullable = false)
    @Enumerated(EnumType.STRING)
    private UserVisibility visibility;

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
    private Set<Role> createdRoles = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "user")
    private Set<BoardMember> joinedBoards = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "user")
    private Set<TaskLike> likedTasks = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "user")
    private Set<PollLike> likedPolls = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "user")
    private Set<CommentLike> likedComments = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "publisher")
    private Set<Tag> createdTags = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "user")
    private Set<DiscussionLike> likedDiscussions = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "reporter")
    private Set<Report> createdReports = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "reported")
    private Set<Report> receivedReports = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "banner")
    private Set<Ban> createdBans = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "banned")
    private Set<Ban> receivedBans = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "publisher")
    private Set<TaskAssignment> createdAssignments = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "user")
    private Set<PollVote> createdVotes = new HashSet<>();
}

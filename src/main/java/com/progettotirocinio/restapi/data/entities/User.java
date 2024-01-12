package com.progettotirocinio.restapi.data.entities;


import com.progettotirocinio.restapi.data.converters.TrimConverter;
import com.progettotirocinio.restapi.data.entities.enums.Gender;
import com.progettotirocinio.restapi.data.entities.images.UserImage;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
@Entity
@EntityListeners(value = AuditingEntityListener.class)
@Table(name = "USERS")
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "EMAIL",nullable = false,updatable = false)
    @Convert(converter = TrimConverter.class)
    private String email;

    @Column(name = "USERNAME",nullable = false,updatable = false)
    @Convert(converter = TrimConverter.class)
    private String username;

    @Column(name = "NAME",nullable = false)
    @Convert(converter = TrimConverter.class)
    private String name;

    @Column(name = "SURNAME",nullable = false,updatable = false)
    @Convert(converter = TrimConverter.class)
    private String surname;

    @Column(name = "GENDER",nullable = false)
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
    private Set<Poll> createdPolls = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "publisher")
    private Set<Tag> createdTags = new HashSet<>();

    @CreatedDate
    @Column(name = "CREATED_DATE",nullable = false)
    private LocalDate createdDate;

    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_DATE",nullable = false)
    private LocalDate lastModifiedDate;
}

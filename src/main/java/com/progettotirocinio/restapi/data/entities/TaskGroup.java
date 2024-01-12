package com.progettotirocinio.restapi.data.entities;


import com.progettotirocinio.restapi.data.converters.TrimConverter;
import com.progettotirocinio.restapi.data.entities.interfaces.OwnableEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
@Entity
@EntityListeners(value = AuditingEntityListener.class)
@Table(name = "TASK_GROUPS")
public class TaskGroup implements OwnableEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "NAME",nullable = false,updatable = false)
    @Convert(converter = TrimConverter.class)
    private String name;

    @Column(name = "EXPIRATION_DATE",nullable = false,updatable = false)
    private LocalDate expirationDate;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "PUBLISHER_ID",nullable = false)
    private User publisher;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "BOARD_ID",nullable = false,updatable = false)
    private Board board;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "group",orphanRemoval = true)
    private Set<Task> tasks = new HashSet<>();

    @CreatedDate
    @Column(name = "CREATED_DATE",nullable = false,updatable = false)
    private LocalDate createdDate;

    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_DATE",nullable = false,updatable = false)
    private LocalDate lastModifiedDate;

    @Override
    public UUID getOwnerID() {
        return this.publisher.getId();
    }
}

package com.progettotirocinio.restapi.data.entities;


import com.progettotirocinio.restapi.data.converters.TrimConverter;
import com.progettotirocinio.restapi.data.entities.enums.Priority;
import com.progettotirocinio.restapi.data.entities.images.TaskImage;
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
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@EntityListeners(value = AuditingEntityListener.class)
@Table(name = "TASKS")
public class Task implements OwnableEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "TITLE",nullable = false)
    @Convert(converter = TrimConverter.class)
    private String title;

    @Column(name = "NAME",nullable = false)
    @Convert(converter = TrimConverter.class)
    private String name;

    @Column(name = "DESCRIPTION",nullable = false)
    @Convert(converter = TrimConverter.class)
    private String description;

    @Column(name = "PRIORITY",nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Priority priority;

    @OneToOne(mappedBy = "task",fetch = FetchType.LAZY)
    private TaskImage taskImage;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "PUBLISHER_ID",nullable = false,updatable = false)
    private User publisher;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "GROUP_ID",nullable = false)
    private TaskGroup group;

    @Column(name = "EXPIRATION_DATE")
    @Convert(converter = TrimConverter.class)
    private LocalDate expirationDate;

    @CreatedDate
    @Column(name = "CREATED_DATE",nullable = false,updatable = false)
    private LocalDate createdDate;

    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_DATE",nullable = false)
    private LocalDate lastModifiedDate;

    @Override
    public UUID getOwnerID() {
        return this.publisher.getId();
    }
}

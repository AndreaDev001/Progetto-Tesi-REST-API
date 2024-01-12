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
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@EntityListeners(value = AuditingEntityListener.class)
@Table(name = "POLLS")
public class Poll implements OwnableEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "TITLE",nullable = false,updatable = false)
    @Convert(converter = TrimConverter.class)
    private String title;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "PUBLISHER_ID",updatable = false,nullable = false)
    private User publisher;

    @Column(name = "DESCRIPTION",nullable = false,updatable = false)
    @Convert(converter = TrimConverter.class)
    private String description;

    @Column(name = "MINIMUM_VOTES",nullable = false)
    private Integer minimumVotes;

    @Column(name = "MAXIMUM_VOTES",nullable = false)
    private Integer maximumVotes;

    @Column(name = "EXPIRATION_DATE",nullable = false)
    private LocalDate expirationDate;

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

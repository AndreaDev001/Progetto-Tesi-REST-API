package com.progettotirocinio.restapi.data.entities;


import com.progettotirocinio.restapi.data.converters.TrimConverter;
import com.progettotirocinio.restapi.data.entities.interfaces.OwnableEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
@Entity
@EntityListeners(value = AuditingEntityListener.class)
@Table(name = "POLLS")
public class Poll extends GenericEntity implements OwnableEntity
{

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

    @Override
    public UUID getOwnerID() {
        return this.publisher.getId();
    }
}

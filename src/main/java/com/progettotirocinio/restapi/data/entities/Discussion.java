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
@Table(name = "DISCUSSIONS")
public class Discussion extends GenericEntity implements OwnableEntity
{
    @Column(name = "TITLE",nullable = false,updatable = false)
    @Convert(converter = TrimConverter.class)
    private String title;

    @Column(name = "TOPIC",nullable = false,updatable = false)
    @Convert(converter = TrimConverter.class)
    private String topic;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "discussion")
    private Set<Comment> comments = new HashSet<>();

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "PUBLISHER_ID",updatable = false)
    private User publisher;

    @Column(name = "EXPIRATION_DATE",nullable = false,updatable = false)
    private LocalDate expirationDate;

    @Override
    public UUID getOwnerID() {
        return this.publisher.getId();
    }
}

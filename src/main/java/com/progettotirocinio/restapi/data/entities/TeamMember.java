package com.progettotirocinio.restapi.data.entities;


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
@Builder
@EqualsAndHashCode(callSuper = true)
@Entity
@EntityListeners(value = AuditingEntityListener.class)
@Table(name = "TEAM_MEMBERS")
public class TeamMember extends GenericEntity implements OwnableEntity
{
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "MEMBER_ID",nullable = false,updatable = false)
    private User member;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "TEAM_ID",nullable = false,updatable = false)
    private Team team;
    @Override
    public UUID getOwnerID() {
        return member.getId();
    }
}

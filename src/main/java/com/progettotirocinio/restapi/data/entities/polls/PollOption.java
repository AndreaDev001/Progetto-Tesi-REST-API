package com.progettotirocinio.restapi.data.entities.polls;

import com.progettotirocinio.restapi.data.converters.TrimConverter;
import com.progettotirocinio.restapi.data.entities.AmountEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "POLL_OPTIONS")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PollOption extends AmountEntity
{
    @Column(name = "NAME",nullable = false,updatable = false)
    @Convert(converter = TrimConverter.class)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "POLL_ID",nullable = false,updatable = false)
    private Poll poll;

    @OneToMany(fetch = FetchType.LAZY,orphanRemoval = true,mappedBy = "pollOption")
    private Set<PollVote> receivedVotes = new HashSet<>();
}

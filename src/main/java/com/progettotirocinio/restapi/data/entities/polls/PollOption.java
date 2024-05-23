package com.progettotirocinio.restapi.data.entities.polls;

import com.progettotirocinio.restapi.data.converters.TrimConverter;
import com.progettotirocinio.restapi.data.entities.AmountEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "POLL_OPTIONS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PollOption extends AmountEntity
{
    @Column(name = "NAME",nullable = false)
    @Convert(converter = TrimConverter.class)
    @Length(min = 3,max = 20)
    private String name;

    @Column(name = "DESCRIPTION",nullable = false)
    @Convert(converter = TrimConverter.class)
    @Length(min = 3,max = 20)
    private String description;

    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    @JoinColumn(name = "POLL_ID",nullable = false,updatable = false)
    private Poll poll;

    @OneToMany(fetch = FetchType.EAGER,orphanRemoval = true,mappedBy = "option")
    private Set<PollVote> receivedVotes = new HashSet<>();
}

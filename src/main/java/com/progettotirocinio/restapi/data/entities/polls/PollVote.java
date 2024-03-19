package com.progettotirocinio.restapi.data.entities.polls;

import com.progettotirocinio.restapi.data.entities.GenericEntity;
import com.progettotirocinio.restapi.data.entities.User;
import com.progettotirocinio.restapi.data.entities.interfaces.OwnableEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "POLL_VOTES")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PollVote extends GenericEntity implements OwnableEntity
{
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "POLL_OPTION_ID",nullable = false,updatable = false)
    private PollOption option;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "USER_ID",nullable = false,updatable = false)
    private User user;

    @Override
    public UUID getOwnerID() {
        return this.user.getId();
    }
}

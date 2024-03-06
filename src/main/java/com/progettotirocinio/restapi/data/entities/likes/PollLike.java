package com.progettotirocinio.restapi.data.entities.likes;

import com.progettotirocinio.restapi.data.entities.polls.Poll;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "POLL_LIKES",uniqueConstraints = @UniqueConstraint(columnNames = {"POLL_ID","USER_ID"}))
public class PollLike extends Like
{
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "POLL_ID",nullable = false,updatable = false)
    private Poll poll;
}

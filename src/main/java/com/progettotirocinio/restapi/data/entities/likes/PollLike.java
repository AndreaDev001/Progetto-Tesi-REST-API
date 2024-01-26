package com.progettotirocinio.restapi.data.entities.likes;


import com.progettotirocinio.restapi.data.entities.Poll;
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
@Table(name = "POLL_LIKES",uniqueConstraints = {@UniqueConstraint(columnNames = {"USER_ID","POLL_ID"})})
public class PollLike extends Like
{
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "POLL_ID",nullable = false,updatable = false)
    private Poll poll;
}

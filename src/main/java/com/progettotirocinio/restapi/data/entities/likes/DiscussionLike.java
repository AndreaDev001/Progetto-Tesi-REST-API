package com.progettotirocinio.restapi.data.entities.likes;

import com.progettotirocinio.restapi.data.entities.Discussion;
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
@Table(name = "DISCUSSION_LIKES",uniqueConstraints = {@UniqueConstraint(columnNames = {"USER_ID","DISCUSSION_ID"})})
public class DiscussionLike extends Like
{
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "DISCUSSION_ID",nullable = false,updatable = false)
    private Discussion discussion;
}

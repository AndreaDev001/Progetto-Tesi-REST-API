package com.progettotirocinio.restapi.data.entities.likes;

import com.progettotirocinio.restapi.data.entities.Discussion;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "DISCUSSION_LIKES",uniqueConstraints = {@UniqueConstraint(columnNames = {"USER_ID","DISCUSSION_ID"})})
public class DiscussionLike extends Like
{
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "DISCUSSION_ID",nullable = false,updatable = false)
    private Discussion discussion;
}

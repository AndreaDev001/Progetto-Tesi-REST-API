package com.progettotirocinio.restapi.data.entities.comments;

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
@Table(name = "COMMENT_DISCUSSIONS")
public class CommentDiscussion extends Comment
{
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "DISCUSSION_ID",nullable = false,updatable = false)
    private Discussion discussion;
}

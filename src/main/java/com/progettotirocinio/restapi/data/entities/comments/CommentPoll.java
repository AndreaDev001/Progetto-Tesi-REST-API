package com.progettotirocinio.restapi.data.entities.comments;

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
@Table(name = "COMMENT_POLLS")
public class CommentPoll extends Comment
{
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "POLL_ID",nullable = false,updatable = false)
    private Poll poll;
}

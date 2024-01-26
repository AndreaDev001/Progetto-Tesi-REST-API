package com.progettotirocinio.restapi.data.entities.likes;

import com.progettotirocinio.restapi.data.entities.Comment;
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
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"USER_ID","COMMENT_ID"}))
public class CommentLike extends Like
{
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "COMMENT_ID",nullable = false,updatable = false)
    private Comment comment;
}

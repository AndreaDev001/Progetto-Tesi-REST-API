package com.progettotirocinio.restapi.data.entities.reports;

import com.progettotirocinio.restapi.data.entities.comments.Comment;
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
@Table(name = "COMMENT_REPORTS")
public class CommentReport extends Report
{
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "COMMENT_ID",nullable = false,updatable = false)
    private Comment comment;
}

package com.progettotirocinio.restapi.data.dto.output.refs;

import com.progettotirocinio.restapi.data.dto.output.GenericOutput;
import com.progettotirocinio.restapi.data.entities.comments.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CommentRef extends GenericOutput<CommentRef>
{
    private String title;
    private String text;
    private UserRef publisher;

    public CommentRef(Comment comment) {
        this.id = comment.getId();
        this.title = comment.getTitle();
        this.text = comment.getText();
        this.createdDate = comment.getCreatedDate();
    }
}

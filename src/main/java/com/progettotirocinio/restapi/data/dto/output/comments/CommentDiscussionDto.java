package com.progettotirocinio.restapi.data.dto.output.comments;

import com.progettotirocinio.restapi.data.dto.output.refs.DiscussionRef;
import com.progettotirocinio.restapi.data.entities.comments.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.server.core.Relation;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Relation(collectionRelation = "content")
public class CommentDiscussionDto extends CommentDto
{
    private DiscussionRef discussion;
}

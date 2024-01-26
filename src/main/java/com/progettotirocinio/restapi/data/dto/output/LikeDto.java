package com.progettotirocinio.restapi.data.dto.output;


import com.progettotirocinio.restapi.data.dto.output.refs.*;
import com.progettotirocinio.restapi.data.entities.enums.LikeType;
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
public class LikeDto extends GenericOutput<LikeDto>
{
    private UserRef user;
    private PollRef poll;
    private CommentRef comment;
    private DiscussionRef discussion;
    private TaskRef task;
    private LikeType type;
}

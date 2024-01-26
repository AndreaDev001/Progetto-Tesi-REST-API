package com.progettotirocinio.restapi.data.dto.output.likes;

import com.progettotirocinio.restapi.data.dto.output.refs.DiscussionRef;
import com.progettotirocinio.restapi.data.entities.likes.Like;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class DiscussionLikeDto extends LikeDto
{
    private DiscussionRef discussion;
}

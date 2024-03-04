package com.progettotirocinio.restapi.data.dto.output.comments;


import com.progettotirocinio.restapi.data.dto.output.refs.PollRef;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CommentPollDto extends CommentDto
{
    private PollRef poll;
}

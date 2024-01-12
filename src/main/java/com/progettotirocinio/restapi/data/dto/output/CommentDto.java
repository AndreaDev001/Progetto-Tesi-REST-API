package com.progettotirocinio.restapi.data.dto.output;


import com.progettotirocinio.restapi.data.dto.output.refs.DiscussionRef;
import com.progettotirocinio.restapi.data.dto.output.refs.UserRef;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CommentDto extends GenericOutput<CommentDto>
{
    private String title;
    private String text;
    private UserRef publisher;
    private DiscussionRef discussion;
}

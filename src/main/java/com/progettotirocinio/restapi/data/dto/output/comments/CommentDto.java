package com.progettotirocinio.restapi.data.dto.output.comments;

import com.progettotirocinio.restapi.data.dto.annotations.AmountReference;
import com.progettotirocinio.restapi.data.dto.output.GenericOutput;
import com.progettotirocinio.restapi.data.dto.output.refs.UserRef;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CommentDto extends GenericOutput<CommentDto>
{
    protected String title;
    protected String text;
    @AmountReference(name = "receivedLikes")
    private Integer amountOfReceivedLikes;
    protected UserRef publisher;
}

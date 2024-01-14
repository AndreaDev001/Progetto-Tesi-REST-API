package com.progettotirocinio.restapi.data.dto.output;


import com.progettotirocinio.restapi.data.dto.output.refs.BoardRef;
import com.progettotirocinio.restapi.data.dto.output.refs.UserRef;
import com.progettotirocinio.restapi.data.entities.enums.BoardInviteStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Relation(collectionRelation = "content")
public class BoardInviteDto extends GenericOutput<BoardInviteDto>
{
    private String text;
    private BoardInviteStatus status;
    private LocalDate expirationDate;
    private UserRef publisher;
    private BoardRef board;
}

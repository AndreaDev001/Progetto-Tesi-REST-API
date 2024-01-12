package com.progettotirocinio.restapi.data.dto.output.refs;


import com.progettotirocinio.restapi.data.dto.output.GenericOutput;
import com.progettotirocinio.restapi.data.entities.Board;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class BoardRef extends GenericOutput<BoardRef>
{
    private String title;
    private Integer minMembers;
    private Integer maxMembers;

    public BoardRef(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.maxMembers = board.getMaxMembers();
        this.createdDate = board.getCreatedDate();
    }
}

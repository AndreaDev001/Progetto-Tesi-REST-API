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
    private String description;
    private Integer maxMembers;
    private Integer amountOfMembers;
    private Integer amountOfGroups;
    private UserRef publisher;

    public BoardRef(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.description = board.getDescription();
        this.maxMembers = board.getMaxMembers();
        this.amountOfMembers = board.getMembers() != null ? board.getMembers().size() : 0;
        this.amountOfGroups = board.getGroups() != null ? board.getGroups().size() : 0;
        this.publisher = new UserRef(board.getPublisher());
        this.createdDate = board.getCreatedDate();
    }
}

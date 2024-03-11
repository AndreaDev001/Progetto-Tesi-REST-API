package com.progettotirocinio.restapi.data.dto.output.refs;

import com.progettotirocinio.restapi.data.dto.output.GenericOutput;
import com.progettotirocinio.restapi.data.entities.BoardMember;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class BoardMemberRef extends GenericOutput<BoardMemberRef>
{
    private UserRef user;
    private BoardRef board;

    public  BoardMemberRef(BoardMember member) {
        this.id = member.getId();
        this.user = new UserRef(member.getUser());
        this.board = new BoardRef(member.getBoard());
        this.createdDate = member.getCreatedDate();
    }
}

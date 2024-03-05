package com.progettotirocinio.restapi.data.dto.output.refs;

import com.progettotirocinio.restapi.data.dto.output.GenericOutput;
import com.progettotirocinio.restapi.data.entities.checklists.CheckList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CheckListRef extends GenericOutput<CheckListRef>
{
    private String name;
    private TaskRef task;
    private UserRef publisher;

    public CheckListRef(CheckList checkList) {
        this.id = checkList.getId();
        this.createdDate = checkList.getCreatedDate();
        this.name = checkList.getName();
        this.task = new TaskRef(checkList.getTask());
        this.publisher = new UserRef(checkList.getPublisher());
    }
}

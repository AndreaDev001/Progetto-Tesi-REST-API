package com.progettotirocinio.restapi.data.dto.output.refs;

import com.progettotirocinio.restapi.data.dto.output.GenericOutput;
import com.progettotirocinio.restapi.data.entities.CheckList;
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
    private TaskGroupRef group;
    private UserRef publisher;

    public CheckListRef(CheckList checkList) {
        this.name = checkList.getName();
        this.group = new TaskGroupRef(checkList.getGroup());
        this.publisher = new UserRef(checkList.getPublisher());
    }
}

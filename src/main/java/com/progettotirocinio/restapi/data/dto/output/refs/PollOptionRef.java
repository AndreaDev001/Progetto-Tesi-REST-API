package com.progettotirocinio.restapi.data.dto.output.refs;

import com.progettotirocinio.restapi.data.dto.output.GenericOutput;
import com.progettotirocinio.restapi.data.entities.polls.PollOption;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PollOptionRef extends GenericOutput<PollOptionRef>
{
    private String name;
    private PollRef poll;

    public PollOptionRef(PollOption pollOption) {
        this.name = pollOption.getName();
    }
}

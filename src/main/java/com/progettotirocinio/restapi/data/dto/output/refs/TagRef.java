package com.progettotirocinio.restapi.data.dto.output.refs;

import com.progettotirocinio.restapi.data.dto.output.GenericOutput;
import com.progettotirocinio.restapi.data.entities.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TagRef extends GenericOutput<TagRef>
{
    private String name;
    private String color;

    public TagRef(Tag tag) {
        this.id = tag.getId();
        this.createdDate = tag.getCreatedDate();
        this.name = tag.getName();
        this.color = tag.getColor();
    }
}

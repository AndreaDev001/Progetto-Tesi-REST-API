package com.progettotirocinio.restapi.data.dto.output.refs;


import com.progettotirocinio.restapi.data.dto.output.GenericOutput;
import com.progettotirocinio.restapi.data.entities.Discussion;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class DiscussionRef extends GenericOutput<DiscussionRef>
{
    private String title;
    private String topic;

    public DiscussionRef(Discussion discussion) {
        this.id = discussion.getId();
        this.title = discussion.getTitle();
        this.topic = discussion.getTopic();
        this.createdDate = discussion.getCreatedDate();
    }
}

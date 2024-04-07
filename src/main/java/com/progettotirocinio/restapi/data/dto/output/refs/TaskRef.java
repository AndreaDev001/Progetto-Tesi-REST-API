package com.progettotirocinio.restapi.data.dto.output.refs;


import com.progettotirocinio.restapi.data.dto.output.GenericOutput;
import com.progettotirocinio.restapi.data.entities.Task;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TaskRef extends GenericOutput<TaskRef>
{
    private String title;
    private String description;
    private Integer amountOfAssignedMembers;
    private Integer amountOfLikes;
    private LocalDate expirationDate;
    private UserRef publisher;
    private TaskGroupRef taskGroup;

    public TaskRef(Task task) {
        this.id = task.getId();
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.amountOfAssignedMembers = task.getAssignments() != null ? task.getAssignments().size() : 0;
        this.amountOfLikes = task.getReceivedLikes() != null ? task.getReceivedLikes().size() : 0;
        this.expirationDate = task.getExpirationDate();
        this.publisher = new UserRef(task.getPublisher());
        this.taskGroup = new TaskGroupRef(task.getGroup());
        this.createdDate = task.getCreatedDate();
    }
}

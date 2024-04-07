package com.progettotirocinio.restapi.data.entities;

import com.progettotirocinio.restapi.data.converters.TrimConverter;
import com.progettotirocinio.restapi.data.entities.interfaces.BoardElement;
import com.progettotirocinio.restapi.data.entities.interfaces.OwnableEntity;
import com.progettotirocinio.restapi.data.entities.interfaces.TaskElement;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(value = AuditingEntityListener.class)
@Table(name = "TASK_URLS")
public class TaskURL extends GenericEntity implements OwnableEntity, TaskElement, BoardElement
{

    @Column(name = "NAME",nullable = false)
    @Convert(converter = TrimConverter.class)
    @Length(min = 3,max = 20)
    private String name;

    @Column(name = "URL",nullable = false)
    @Convert(converter = TrimConverter.class)
    private String url;

    @ManyToOne(fetch = FetchType.LAZY,optional = true)
    @JoinColumn(name = "PUBLISHER_ID",nullable = true)
    private BoardMember publisher;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "TASK_ID",nullable = false)
    private Task task;

    @Override
    public UUID getTaskID() {
        return this.task.getId();
    }

    @Override
    public UUID getBoardID() {
        return this.task.getBoardID();
    }

    @Override
    public UUID getOwnerID() {
        return publisher.getId();
    }
}

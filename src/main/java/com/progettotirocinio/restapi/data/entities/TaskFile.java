package com.progettotirocinio.restapi.data.entities;

import com.progettotirocinio.restapi.data.converters.TrimConverter;
import com.progettotirocinio.restapi.data.entities.interfaces.BoardElement;
import com.progettotirocinio.restapi.data.entities.interfaces.OwnableEntity;
import com.progettotirocinio.restapi.data.entities.interfaces.TaskElement;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

@Entity
@Table(name = "TASK_FILES")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TaskFile extends GenericEntity implements OwnableEntity,TaskElement, BoardElement
{

    @Column(name = "NAME",nullable = false,updatable = false)
    @Convert(converter = TrimConverter.class)
    @Length(min = 3,max = 20)
    private String name;

    @Column(name = "FILE_NAME",nullable = false,updatable = false)
    private String fileName;

    @Column(name = "TYPE",nullable = false,updatable = false)
    private String type;

    @Column(name = "EXTENSION",nullable = false,updatable = false)
    private String extension;

    @Column(name = "FILE",nullable = false)
    @Lob
    private byte[] file;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "TASK_ID",nullable = false,updatable = false)
    private Task task;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "PUBLISHER_ID",nullable = false,updatable = false)
    private BoardMember publisher;


    @Override
    public UUID getBoardID() {
        return this.task.getBoardID();
    }

    @Override
    public UUID getOwnerID() {
        return this.publisher.getOwnerID();
    }

    @Override
    public UUID getTaskID() {
        return this.task.getId();
    }
}

package com.progettotirocinio.restapi.data.entities.checklists;

import com.progettotirocinio.restapi.data.converters.TrimConverter;
import com.progettotirocinio.restapi.data.entities.GenericEntity;
import com.progettotirocinio.restapi.data.entities.User;
import com.progettotirocinio.restapi.data.entities.interfaces.BoardElement;
import com.progettotirocinio.restapi.data.entities.interfaces.OwnableEntity;
import com.progettotirocinio.restapi.data.entities.interfaces.TaskElement;
import com.progettotirocinio.restapi.data.entities.listeners.UUIDEntityListener;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(value =  {AuditingEntityListener.class, UUIDEntityListener.class})
@Table(name = "CHECKLIST_OPTIONS")
public class CheckListOption extends GenericEntity implements OwnableEntity, BoardElement, TaskElement
{
    @Column(name = "NAME",nullable = false)
    @Convert(converter = TrimConverter.class)
    private String name;

    @Column(name = "COMPLETED",nullable = false)
    private boolean completed;

    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    @JoinColumn(name = "CHECKLIST_ID",nullable = false,updatable = false)
    private CheckList checkList;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "PUBLISHER_ID",nullable = false,updatable = false)
    private User publisher;

    @Override
    public UUID getOwnerID() {
        return this.publisher.getId();
    }

    @Override
    public UUID getBoardID() {
        return this.checkList.getTaskID();
    }

    @Override
    public UUID getTaskID() {
        return this.checkList.getTaskID();
    }
}

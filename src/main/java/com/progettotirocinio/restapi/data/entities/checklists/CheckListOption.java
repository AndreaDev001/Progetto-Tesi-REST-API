package com.progettotirocinio.restapi.data.entities.checklists;

import com.progettotirocinio.restapi.data.converters.TrimConverter;
import com.progettotirocinio.restapi.data.entities.GenericEntity;
import com.progettotirocinio.restapi.data.entities.User;
import com.progettotirocinio.restapi.data.entities.listeners.UUIDEntityListener;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder
@Entity
@EntityListeners(value =  {AuditingEntityListener.class, UUIDEntityListener.class})
@Table(name = "CHECKLIST_OPTIONS")
public class CheckListOption extends GenericEntity
{
    @Column(name = "NAME",nullable = false,updatable = false)
    @Convert(converter = TrimConverter.class)
    private String name;

    @Column(name = "COMPLETED",nullable = false,updatable = false)
    private boolean completed;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "CHECKLIST_ID",nullable = false,updatable = false)
    private CheckList checkList;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "PUBLISHER_ID",nullable = false,updatable = false)
    private User publisher;
}

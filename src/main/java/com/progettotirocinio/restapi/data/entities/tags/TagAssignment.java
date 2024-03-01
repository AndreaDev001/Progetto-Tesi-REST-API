package com.progettotirocinio.restapi.data.entities.tags;

import com.progettotirocinio.restapi.data.entities.GenericEntity;
import com.progettotirocinio.restapi.data.entities.Task;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@EntityListeners(value = AuditingEntityListener.class)
@Entity
@Table(name = "TAG_ASSIGNMENTS")
public class TagAssignment extends GenericEntity
{
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "TAG_ID",nullable = false,updatable = false)
    private Tag tag;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "TASK_ID",nullable = false,updatable = false)
    private Task task;
}

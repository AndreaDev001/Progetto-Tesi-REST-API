package com.progettotirocinio.restapi.data.entities;

import com.progettotirocinio.restapi.data.converters.TrimConverter;
import com.progettotirocinio.restapi.data.entities.listeners.UUIDEntityListener;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Entity
@EntityListeners(value = {AuditingEntityListener.class, UUIDEntityListener.class})
public class CheckList extends AmountEntity
{
    @Column(name = "NAME",nullable = false,updatable = false)
    @Convert(converter = TrimConverter.class)
    private String name;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "GROUP_ID",nullable = false,updatable = false)
    private TaskGroup group;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "PUBLISHER_ID",nullable = false,updatable = false)
    private User publisher;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true,mappedBy = "checkList")
    private Set<CheckListOption> options = new HashSet<>();
}

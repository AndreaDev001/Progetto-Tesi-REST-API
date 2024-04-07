package com.progettotirocinio.restapi.data.entities.tags;

import com.progettotirocinio.restapi.data.converters.TrimConverter;
import com.progettotirocinio.restapi.data.entities.Board;
import com.progettotirocinio.restapi.data.entities.GenericEntity;
import com.progettotirocinio.restapi.data.entities.User;
import com.progettotirocinio.restapi.data.entities.interfaces.BoardElement;
import com.progettotirocinio.restapi.data.entities.interfaces.OwnableEntity;
import com.progettotirocinio.restapi.data.entities.interfaces.TaskElement;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@EntityListeners(value = AuditingEntityListener.class)
@Entity
@Table(name = "TAGS")
public class Tag extends GenericEntity implements OwnableEntity, BoardElement
{
    @Column(name = "NAME",nullable = false,updatable = false)
    @Convert(converter = TrimConverter.class)
    @Length(min = 3,max = 10)
    private String name;

    @Column(name = "COLOR",nullable = false,updatable = false)
    @Pattern(regexp = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$")
    private String color;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "BOARD_ID",nullable = false,updatable = false)
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "PUBLISHER_ID",nullable = false,updatable = false)
    private User publisher;

    @Override
    public UUID getOwnerID() {
        return this.publisher.getId();
    }

    @Override
    public UUID getBoardID() {
        return this.board.getId();
    }
}

package com.progettotirocinio.restapi.data.entities.likes;

import com.progettotirocinio.restapi.data.entities.GenericEntity;
import com.progettotirocinio.restapi.data.entities.User;
import com.progettotirocinio.restapi.data.entities.interfaces.OwnableEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@MappedSuperclass
public abstract class Like extends GenericEntity implements OwnableEntity
{
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "USER_ID",nullable = false,updatable = false)
    protected User user;

    @Override
    public UUID getOwnerID() {
        return user.getId();
    }
}
package com.progettotirocinio.restapi.data.entities.listeners;


import com.progettotirocinio.restapi.data.entities.GenericEntity;
import jakarta.persistence.PrePersist;

import java.util.UUID;

public class UUIDEntityListener
{
    @PrePersist
    private void beforePersist(GenericEntity genericEntity) {
        if(genericEntity.getId() == null)
            genericEntity.setId(UUID.randomUUID());
    }
}

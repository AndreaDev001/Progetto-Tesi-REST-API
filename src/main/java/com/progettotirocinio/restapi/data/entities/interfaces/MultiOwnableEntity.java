package com.progettotirocinio.restapi.data.entities.interfaces;

import java.util.List;
import java.util.UUID;

public interface MultiOwnableEntity {
    public List<UUID> getOwnersID();
}

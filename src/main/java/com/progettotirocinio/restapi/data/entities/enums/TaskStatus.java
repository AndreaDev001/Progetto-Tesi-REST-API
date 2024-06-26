package com.progettotirocinio.restapi.data.entities.enums;

import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "content")
public enum TaskStatus
{
    OPEN,
    COMPLETED,
    EXPIRED
}

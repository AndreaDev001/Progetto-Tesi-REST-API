package com.progettotirocinio.restapi.data.entities.enums;

import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "content")
public enum PermissionType
{
    WRITE_BOARD,
    READ_BOARD,
    WRITE_TASKS,
    READ_TASKS,
    WRITE_ASSIGNED_TASK,
    READ_ASSIGNED_TASK,
}

package com.progettotirocinio.restapi.data.entities.enums;

import lombok.Getter;
import org.springframework.hateoas.server.core.Relation;
import org.springframework.web.bind.annotation.GetMapping;

@Relation(collectionRelation = "content")
@Getter
public enum NotAllowedExtension
{

    BIN("application/octet-stream"),
    OGV(" application/ogg"),
    SH("application/x-sh"),
    BAT("application/x-msdownload");

    private final String name;

    NotAllowedExtension(String name) {
        this.name = name;
    }
}

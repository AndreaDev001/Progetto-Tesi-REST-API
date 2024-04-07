package com.progettotirocinio.restapi.data.entities.enums;

import lombok.Getter;
import org.springframework.hateoas.server.core.Relation;


@Getter
@Relation(collectionRelation = "content")
public enum ImageType
{
    PNG("image/png"),
    JPG("image/jpeg");

    private String name;
    ImageType(String name) {
        this.name = name;
    }
}

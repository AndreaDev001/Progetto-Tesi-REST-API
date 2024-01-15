package com.progettotirocinio.restapi.data.dto.output;

import com.progettotirocinio.restapi.data.dto.annotations.AmountReference;
import org.springframework.hateoas.RepresentationModel;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.UUID;

public abstract class GenericOutput<T extends RepresentationModel<? extends T>> extends RepresentationModel<T> {

    protected UUID id;
    protected LocalDate createdDate;

    public void addLinks(Object... params) {

    }
}

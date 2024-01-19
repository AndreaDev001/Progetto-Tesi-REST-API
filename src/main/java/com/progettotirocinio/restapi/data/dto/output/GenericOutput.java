package com.progettotirocinio.restapi.data.dto.output;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.progettotirocinio.restapi.data.dto.annotations.AmountReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.UUID;



@Data
@EqualsAndHashCode(callSuper = false)
public abstract class GenericOutput<T extends RepresentationModel<? extends T>> extends RepresentationModel<T> {

    protected UUID id;
    protected LocalDate createdDate;

    public void addLinks(Object... params) {

    }
}

package com.progettotirocinio.restapi.data.dao.specifications.annotations;

import com.progettotirocinio.restapi.data.dao.specifications.SpecificationComparison;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface SpecificationPath
{
    String path();
    SpecificationComparison comparison();
}

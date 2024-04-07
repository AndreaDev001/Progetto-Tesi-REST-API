package com.progettotirocinio.restapi.data.dao.specifications.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface SpecificationOrderType
{
    String name() default "";
    boolean usePrefix() default true;
    boolean allowDepth() default false;
    boolean calculateDefault() default true;
}

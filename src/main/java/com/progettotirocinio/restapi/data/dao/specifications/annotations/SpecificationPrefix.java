package com.progettotirocinio.restapi.data.dao.specifications.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SpecificationPrefix
{
    String prefix() default "";
    boolean useDefault() default true;
}

package com.progettotirocinio.restapi.config.caching;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface RequiresCaching
{
    boolean allCachingRequired() default true;
    boolean searchCachingRequired() default false;
    String allCacheName() default "";
    String allSearchName() default "";
    int requiredInterval() default 10000;
    int initialDelay() default 500;
}

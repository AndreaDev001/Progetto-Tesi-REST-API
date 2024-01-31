package com.progettotirocinio.restapi.services.implementations;

import com.progettotirocinio.restapi.config.caching.CacheHandler;
import com.progettotirocinio.restapi.config.caching.RequiresCaching;
import com.progettotirocinio.restapi.config.hateoas.GenericModelAssembler;
import com.progettotirocinio.restapi.config.mapper.Mapper;
import com.progettotirocinio.restapi.data.dao.UserDao;
import com.progettotirocinio.restapi.data.dto.output.GenericOutput;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cache.CacheManager;
import org.springframework.data.web.PagedResourcesAssembler;

import java.util.Arrays;

public abstract class GenericServiceImp<T,U extends GenericOutput<?>> implements InitializingBean
{
    protected final Mapper modelMapper;
    protected final GenericModelAssembler<T,U> modelAssembler;
    protected final PagedResourcesAssembler<T> pagedResourcesAssembler;
    protected final UserDao userDao;
    protected final CacheHandler cacheHandler;

    public GenericServiceImp(CacheHandler cacheHandler,UserDao userDao,Mapper mapper,Class<T> source,Class<U> destination,PagedResourcesAssembler<T> pagedResourcesAssembler) {
        this.modelMapper = mapper;
        this.modelAssembler = new GenericModelAssembler<>(source,destination,modelMapper);
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.userDao = userDao;
        this.cacheHandler = cacheHandler;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if(this.getClass().isAnnotationPresent(RequiresCaching.class)) {
            RequiresCaching requiresCaching = this.getClass().getAnnotation(RequiresCaching.class);
            if(requiresCaching.allCachingRequired())
                cacheHandler.addAllCache(this.getClass());
            if(requiresCaching.searchCachingRequired())
                cacheHandler.addSearchCache(this.getClass());
        }
    }
}

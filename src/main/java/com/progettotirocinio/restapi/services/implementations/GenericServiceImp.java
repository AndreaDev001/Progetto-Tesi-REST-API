package com.progettotirocinio.restapi.services.implementations;

import com.progettotirocinio.restapi.config.hateoas.GenericModelAssembler;
import com.progettotirocinio.restapi.config.mapper.Mapper;
import com.progettotirocinio.restapi.data.dto.output.GenericOutput;
import org.modelmapper.ModelMapper;
import org.springframework.data.web.PagedResourcesAssembler;

public abstract class GenericServiceImp<T,U extends GenericOutput<?>>
{
    protected final Mapper modelMapper;
    protected final GenericModelAssembler<T,U> modelAssembler;
    protected final PagedResourcesAssembler<T> pagedResourcesAssembler;

    public GenericServiceImp(Mapper mapper,Class<T> source,Class<U> destination,PagedResourcesAssembler<T> pagedResourcesAssembler) {
        this.modelMapper = mapper;
        this.modelAssembler = new GenericModelAssembler<>(source,destination,modelMapper);
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }
}

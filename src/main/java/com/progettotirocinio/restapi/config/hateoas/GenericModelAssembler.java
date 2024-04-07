package com.progettotirocinio.restapi.config.hateoas;

import com.progettotirocinio.restapi.config.mapper.Mapper;
import com.progettotirocinio.restapi.data.dto.output.GenericOutput;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

public class GenericModelAssembler<T,U extends GenericOutput<?>> extends RepresentationModelAssemblerSupport<T,U> {

    private final Mapper modelMapper;

    public GenericModelAssembler(Class<?> controllerClass, Class<U> resourceType,Mapper mapper) {
        super(controllerClass, resourceType);
        this.modelMapper = mapper;
    }

    @Override
    public U toModel(T entity) {
        U result = HateoasUtils.convertToType(entity,this.getResourceType(),modelMapper);
        result.addLinks();
        return result;
    }
}

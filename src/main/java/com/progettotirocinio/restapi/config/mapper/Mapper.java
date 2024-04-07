package com.progettotirocinio.restapi.config.mapper;


import com.progettotirocinio.restapi.data.dto.annotations.AmountReference;
import com.progettotirocinio.restapi.data.dto.output.GenericOutput;
import com.progettotirocinio.restapi.data.entities.AmountEntity;
import com.progettotirocinio.restapi.data.entities.GenericEntity;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class Mapper
{
    private final ModelMapper modelMapper;
    private final Map<Class<?>,Field[]> annotatedFields = new HashMap<>();

    @SneakyThrows
    public<T> T map(Object source,Class<T> requiredClass) {
        T result = this.modelMapper.map(source,requiredClass);
        if(result instanceof GenericOutput<?> genericOutput)
        {
            genericOutput.addLinks();
            if(source instanceof GenericEntity genericEntity) {
                genericOutput.setId(genericEntity.getId());
                genericOutput.setCreatedDate(genericEntity.getCreatedDate());
            }
        }
        if(source instanceof AmountEntity amountEntity) {
            Field[] fields = getFields(result);
            for(Field current : fields)
            {
                if(current.isAnnotationPresent(AmountReference.class))
                {
                    current.setAccessible(true);
                    AmountReference amountReference = current.getAnnotation(AmountReference.class);
                    String name = amountReference.name();
                    if(amountEntity.getAmounts().containsKey(name)) {
                        Set<?> values = (Set<?>) amountEntity.getAmounts().get(name).get(amountEntity);
                        Integer requiredValue = values != null ? values.size() : amountReference.defaultValue();
                        current.set(result,requiredValue);
                    }
                }
            }
        }
        return result;
    }
    private Field[] getFields(Object value) {
        Field[] fields = value.getClass().getDeclaredFields();
        if(this.annotatedFields.containsKey(value.getClass()))
            fields = this.annotatedFields.get(value.getClass());
        else
            this.annotatedFields.put(value.getClass(),fields);
        return fields;
    }
}

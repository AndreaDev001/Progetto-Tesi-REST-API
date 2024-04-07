package com.progettotirocinio.restapi.data.entities;

import lombok.Getter;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.*;

@Getter
public abstract class AmountEntity extends GenericEntity {
    protected Map<String,Field> amounts = new HashMap<>();

    public AmountEntity() {
        this.initializeAmounts();
    }

    @SneakyThrows
    protected void initializeAmounts() {
        Field[] fields = this.getClass().getDeclaredFields();
        for(Field field : fields) {
            if(!Collection.class.isAssignableFrom(field.getType()))
                continue;
            field.setAccessible(true);
            this.amounts.put(field.getName(),field);
        }
    }
}

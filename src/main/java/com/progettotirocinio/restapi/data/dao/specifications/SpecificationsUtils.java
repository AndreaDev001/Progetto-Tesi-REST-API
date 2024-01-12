package com.progettotirocinio.restapi.data.dao.specifications;

import com.progettotirocinio.restapi.data.dao.specifications.annotations.SpecificationOrderType;
import com.progettotirocinio.restapi.data.dao.specifications.annotations.SpecificationPath;
import com.progettotirocinio.restapi.data.dao.specifications.annotations.SpecificationPrefix;
import jakarta.persistence.criteria.*;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.*;

public abstract class SpecificationsUtils
{
    private final static Map<Class<?>, List<String>> orderTypes = new HashMap<>();
    private final static Map<String,String> orderTypePath = new HashMap<>();

    public enum OrderMode
    {
        ASCENDED,
        DESCENDED
    }

    @SneakyThrows
    public static List<Order> generateOrders(Root<?> root, Class<?> requiredClass, CriteriaBuilder criteriaBuilder, List<String> requiredOrderTypes, OrderMode orderMode) {
        if(orderMode == null)
            orderMode = OrderMode.DESCENDED;
        List<String> generatedOrderTypes = SpecificationsUtils.generateOrderTypes(requiredClass);
        List<String> actualOrderTypes = new ArrayList<>();
        for(String currentOrder : requiredOrderTypes) {
            if(generatedOrderTypes.contains(currentOrder))
                actualOrderTypes.add(currentOrder);
        }
        if(actualOrderTypes.isEmpty())
            actualOrderTypes.add(generatedOrderTypes.get(0));
        List<Order> requiredOrders = new ArrayList<>();
        boolean desc = orderMode == OrderMode.DESCENDED;
        for(String currentOrderType : actualOrderTypes) {
            currentOrderType = currentOrderType.trim();
            String requiredPath = orderTypePath.get(currentOrderType);
            String[] values = requiredPath.split("\\.");
            Path<?> currentPath = root.get(values[0]);
            if(values.length > 1) {
                for(int i = 1;i < values.length;i++) {
                    String value = values[i];
                    currentPath = currentPath.get(value);
                }
            }
            System.out.println(currentPath);
            Order requiredOrder =  desc ? criteriaBuilder.desc(currentPath) : criteriaBuilder.asc(currentPath);
            requiredOrders.add(requiredOrder);
        }
        return requiredOrders;
    }


    @SneakyThrows
    public static Predicate generatePredicate(BaseFilter object, Root<?> root, CriteriaBuilder criteriaBuilder) {
        Class<?> targetClass = object.getClass();
        Field[] fields = targetClass.getDeclaredFields();
        List<Predicate> requiredPredicates = new ArrayList<>();
        for(Field current : fields) {
            current.setAccessible(true);
            SpecificationPath specificationPath = current.getAnnotation(SpecificationPath.class);
            if(specificationPath != null) {
                String path = specificationPath.path();
                SpecificationComparison requiredComparison = specificationPath.comparison();
                Expression<?> requiredPath = generatePath(root,path);
                if(current.get(object) == null)
                    continue;
                switch (requiredComparison) {
                    case LIKE -> requiredPredicates.add(criteriaBuilder.like((Expression<String>) requiredPath,SpecificationsUtils.likePattern(current.get(object).toString())));
                    case EQUAL -> requiredPredicates.add(criteriaBuilder.equal(requiredPath,current.get(object)));
                    case GREATER_THAN -> requiredPredicates.add(criteriaBuilder.ge((Expression<? extends Number>) requiredPath,(Number)current.get(object)));
                    case LESS_THAN -> requiredPredicates.add(criteriaBuilder.le((Expression<? extends Number>) requiredPath,(Number)current.get(object)));
                }
            }
        }
        return generatePredicate(root,requiredPredicates,criteriaBuilder,object);
    }

    @SneakyThrows
    public static List<String> generateOrderTypes(Class<?> requiredClass) {
        if(requiredClass.isAnnotationPresent(SpecificationPrefix.class)) {
            if(orderTypes.containsKey(requiredClass))
                return orderTypes.get(requiredClass);
            SpecificationPrefix specificationPrefix = requiredClass.getAnnotation(SpecificationPrefix.class);
            String prefix = specificationPrefix.useDefault() ? requiredClass.getSimpleName().toUpperCase() : specificationPrefix.prefix();
            List<String> values = getOrderTypes(requiredClass,prefix,"");
            orderTypes.put(requiredClass,values);
            return values;
        }
        return null;
    }

    private static List<String> getOrderTypes(Class<?> requiredClass,String currentName,String currentPath) {
        Field[] fields = requiredClass.getDeclaredFields();
        List<String> values = new ArrayList<>();
        for(Field current : fields) {
            if(current.isAnnotationPresent(SpecificationOrderType.class)) {
                SpecificationOrderType specificationOrderType = current.getAnnotation(SpecificationOrderType.class);
                String fieldName = current.getName();
                String name = specificationOrderType.calculateDefault() ? fieldName.toUpperCase() : specificationOrderType.name();
                String result = currentName + "_" + name;
                if(!specificationOrderType.allowDepth()) {
                    String previousPath = currentPath;
                    currentPath = currentPath.isBlank() ? fieldName : currentPath + "." + fieldName;
                    orderTypePath.put(result,currentPath);
                    currentPath = previousPath;
                    values.add(result);
                }
                else
                {
                    String value = currentPath;
                    value = value.isBlank() ? fieldName : currentPath + "." + fieldName;
                    List<String> orderTypes = getOrderTypes(current.getType(),result,value);
                    values.addAll(orderTypes);
                }
            }
        }
        return values;
    }

    public static Expression<?> generatePath(Root<?> root,String path) {
        String[] values = path.split("\\.");
        if(values.length > 0) {
            Path<?> currentPath = root.get(values[0]);
            for(int i = 1;i < values.length;i++) {
                currentPath = currentPath.get(values[i]);
            }
            return currentPath;
        }
        return null;
    }

    private static Predicate generatePredicate(Root<?> root, List<Predicate> requiredPredicates, CriteriaBuilder criteriaBuilder, BaseFilter baseFilter) {
        Predicate requiredPredicate = criteriaBuilder.isNotNull(root.get("id"));
        for(UUID currentID : baseFilter.excludedIDs) {
            requiredPredicate = criteriaBuilder.and(requiredPredicate,criteriaBuilder.notEqual(root.get("id"),currentID));
        }
        for(Predicate currentPredicate : requiredPredicates) {
            requiredPredicate = criteriaBuilder.and(requiredPredicate,currentPredicate);
        }
        return requiredPredicate;
    }

    public static String likePattern(String requiredString) {return "%" + requiredString + "%";}
}

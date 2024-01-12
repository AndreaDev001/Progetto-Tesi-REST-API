package com.progettotirocinio.restapi.data.dao.specifications;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseFilter
{
    protected SpecificationsUtils.OrderMode orderMode;
    protected List<UUID> excludedIDs = new ArrayList<>();
    protected List<String> orderTypes = new ArrayList<>();
}

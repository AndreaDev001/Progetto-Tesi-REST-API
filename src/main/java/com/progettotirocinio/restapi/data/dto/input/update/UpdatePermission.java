package com.progettotirocinio.restapi.data.dto.input.update;


import com.progettotirocinio.restapi.data.entities.enums.PermissionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdatePermission
{
    private String name;
    private PermissionType type;
}

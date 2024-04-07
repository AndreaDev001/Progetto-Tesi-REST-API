package com.progettotirocinio.restapi.data.dto.input.update;


import com.progettotirocinio.restapi.data.entities.enums.PermissionType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdatePermissionDto
{
    @NotNull
    private UUID permissionID;
    private String name;
    private PermissionType type;
}

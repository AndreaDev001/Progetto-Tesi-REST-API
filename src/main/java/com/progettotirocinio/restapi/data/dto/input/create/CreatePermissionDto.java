package com.progettotirocinio.restapi.data.dto.input.create;


import com.progettotirocinio.restapi.data.entities.enums.PermissionType;
import jakarta.validation.constraints.NotBlank;
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
public class CreatePermissionDto
{
    @NotNull
    @NotBlank
    private String name;
    @NotNull
    private PermissionType type;
    @NotNull
    private UUID roleID;
}

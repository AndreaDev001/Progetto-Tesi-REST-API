package com.progettotirocinio.restapi.data.dto.input.update;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRoleDto
{
    @NotNull
    private UUID roleID;
    private String name;
}

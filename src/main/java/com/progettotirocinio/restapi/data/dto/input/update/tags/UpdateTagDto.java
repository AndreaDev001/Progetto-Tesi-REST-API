package com.progettotirocinio.restapi.data.dto.input.update.tags;

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
public class UpdateTagDto
{
    @NotNull
    private UUID tagID;
    private String name;
}

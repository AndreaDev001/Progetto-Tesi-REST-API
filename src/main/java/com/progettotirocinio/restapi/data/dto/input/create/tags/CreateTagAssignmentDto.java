package com.progettotirocinio.restapi.data.dto.input.create.tags;

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
public class CreateTagAssignmentDto
{
    @NotNull
    private UUID tagID;
    @NotNull
    private UUID taskID;
}

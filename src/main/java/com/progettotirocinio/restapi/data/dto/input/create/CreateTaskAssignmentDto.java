package com.progettotirocinio.restapi.data.dto.input.create;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateTaskAssignmentDto
{
    @NotNull
    private UUID taskID;
    @NotNull
    private UUID userID;
}

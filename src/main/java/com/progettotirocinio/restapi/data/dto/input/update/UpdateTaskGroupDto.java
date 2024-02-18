package com.progettotirocinio.restapi.data.dto.input.update;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateTaskGroupDto
{
    @NotNull
    private UUID groupID;
    private String name;
    private LocalDate expirationDate;
    @PositiveOrZero
    private Integer order;
}

package com.progettotirocinio.restapi.data.dto.input.create;


import com.progettotirocinio.restapi.data.entities.enums.Priority;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class CreateTaskDto
{
    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String description;

    @NotNull
    private Priority priority;

    @NotNull
    private UUID groupID;

    @NotNull
    @Future
    private LocalDate expirationDate;
}

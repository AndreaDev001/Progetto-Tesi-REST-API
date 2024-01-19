package com.progettotirocinio.restapi.data.dto.input.create;


import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTaskGroupDto
{
    @NotNull
    private UUID boardID;
    @NotNull
    @NotBlank
    private String name;
    @Future
    private LocalDate expirationDate;
}

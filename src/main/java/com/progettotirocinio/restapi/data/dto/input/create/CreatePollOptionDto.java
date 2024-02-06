package com.progettotirocinio.restapi.data.dto.input.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePollOptionDto
{
    @NotNull
    @NotBlank
    private String name;

    @NotNull
    private UUID pollID;
}
